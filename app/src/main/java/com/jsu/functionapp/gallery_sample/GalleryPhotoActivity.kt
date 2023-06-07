package com.jsu.functionapp.gallery_sample

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.jsu.functionapp.BaseVBActivity
import com.jsu.functionapp.R
import com.jsu.functionapp.databinding.ActivityGalleryPhotoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class GalleryPhotoActivity : BaseVBActivity() {
    private val activity = this
    private lateinit var binding: ActivityGalleryPhotoBinding

    private var adtPhoto : GalleryPhotoAdapter? = null
    private val images = MutableLiveData<List<MediaStoreImage?>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewInit()

    }

    private fun viewInit() {

        setTitleBar("Album", View.OnClickListener { onBackPressed() })

        GlobalScope.launch {
            val imageList = queryImages()
            images.postValue(imageList)
        }

        adtSetting()

    }

    private suspend fun queryImages(): MutableList<MediaStoreImage?> {

        val galleryImageList = mutableListOf<MediaStoreImage?>()
        var dummyData: MediaStoreImage? = null

        withContext(Dispatchers.IO) {

            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN
            )

            val sortOrder = "${MediaStore.Images.Media._ID} DESC"
            contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )?.use { cursor ->

                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val dateTakenColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)
                val displayNameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val dateTaken = Date(cursor.getLong(dateTakenColumn))
                    val displayName = cursor.getString(displayNameColumn)
                    val contentUri = Uri.withAppendedPath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id.toString()
                    )

                    val image = MediaStoreImage(id, displayName, dateTaken, contentUri)
                    dummyData = image
                    galleryImageList.add(image)

                    //22.07.07 jsu 갤러리에 이미지가 없는 경우 ANR발생 수정
                }
            }

        }
        if (dummyData == null) {
            withContext(Dispatchers.Main) {
                galleryImageList.add(0, dummyData)
                Toast.makeText(activity, "갤러리에 저장된 이미지가 없습니다.", Toast.LENGTH_LONG).show()
            }
        } else {
            dummyData?.let { galleryImageList.add(0, it) }
        }
        // dummyData?.let { galleryImageList.add(0, it) }

        return galleryImageList
    }

    private fun adtSetting() {

        adtPhoto = GalleryPhotoAdapter(activity)

        binding.rvPhoto.adapter = adtPhoto
        binding.rvPhoto.layoutManager = GridLayoutManager(activity,3)

        images.observe(this, Observer { images ->
            adtPhoto?.submitList(images)  //데이터를 교체할 때 사용
        })

    }

}