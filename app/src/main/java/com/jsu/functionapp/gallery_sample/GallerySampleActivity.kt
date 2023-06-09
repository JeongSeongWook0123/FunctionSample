package com.jsu.functionapp.gallery_sample

import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jsu.functionapp.BaseVBActivity
import com.jsu.functionapp.R
import com.jsu.functionapp.databinding.ActivityGallerySampleBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GallerySampleActivity : BaseVBActivity() {

    private val activity = this
    private lateinit var binding: ActivityGallerySampleBinding

    private var currentPhotoPath: String? = null
    private var name: String? = null
    private var photoFile: File? = null

    private val permissionCamera = android.Manifest.permission.CAMERA
    private val permissionStroage = android.Manifest.permission.WRITE_EXTERNAL_STORAGE

    private val codeStroage = 0x1145
    private val codeCamera = 0x1146

    private var arrDeniedPermit: ArrayList<String> = arrayListOf()  // 거절된 권한
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGallerySampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewInit()

    }

    private fun viewInit() {

        setTitleBar("Gallery Sample", View.OnClickListener { onBackPressed() })

        binding.btnGallery.setOnClickListener {
            permitCheckStorage(permissionStroage, codeStroage)
        }

        binding.btnCapture.setOnClickListener {
            permitCheckStorage(permissionCamera, codeCamera)
        }

    }

    private fun permitCheckStorage(permission: String, resultCode: Int) {

        arrDeniedPermit.clear()

        when (ContextCompat.checkSelfPermission(activity, permission)) {
            PackageManager.PERMISSION_GRANTED -> {  //권한이 허용된 상태
                when (permission) {
                    permissionStroage -> {  //외부저장소 권한 허용
                        val intent = Intent(activity, GalleryPhotoActivity::class.java)
                        galleryRequestForResult.launch(intent)
                    }

                    permissionCamera -> {   //카메라 권한 허용
                        dispatchTakePictureIntent()
                    }
                }

            }

            PackageManager.PERMISSION_DENIED -> {   //권한을 허용되지 않은 상태에서 버튼 클릭 시 시스템 설정으로 이동
                arrDeniedPermit.add(permission)

                when (activity.shouldShowRequestPermissionRationale(permission)) {
                    true -> {   //권한을 거부한적이 있는 경우 -> 시스템 권한 설정으로 이동
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            data = Uri.fromParts("package", packageName, null)
                        }
                        startActivity(intent)
                    }

                    else -> Log.d("tjddnr", "권한X, 최초 요청 / 다시보지않기(2번거절) , M(23) 안드6 이상")

                }
                ActivityCompat.requestPermissions(this, arrDeniedPermit.toTypedArray(), resultCode)
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


        if(grantResults.contains(PackageManager.PERMISSION_GRANTED)) {

            when (requestCode) {
                codeStroage -> {
                    val intent = Intent(activity, GalleryPhotoActivity::class.java)
                    galleryRequestForResult.launch(intent)
                }
                codeCamera -> {
                    dispatchTakePictureIntent()
                }
            }

        }

    }


    //Image File생성
    @Throws(IOException::class)
    private fun createImageFile(): File {

        var storageDir: File? = null
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA).format(Date())
        val name = "JPEG_${timeStamp}_" // Image Name ex)JPEG_20230607135400

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { //API29
            storageDir = getExternalFilesDir(Environment.DIRECTORY_DCIM + "/fucntionApp")
        } else {
            storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        }

        this.name = name

        return File.createTempFile(name, ".jpg", storageDir)
            .apply { currentPhotoPath = absolutePath }
    }

    private val galleryRequestForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == RESULT_OK) {

                Log.d("tjddnr","imgUrl: ${result.data}")
                Glide.with(activity)
                    .load(result.data?.data)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .apply(RequestOptions.fitCenterTransform())
                    .into(binding.imageSample)
            }

        }

    //카메라 사진 촬영 후
    private val cameraRequsetForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                try {
                    val imageUri = Uri.fromFile(File(currentPhotoPath))

                    Glide.with(activity)
                        .load(imageUri)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.error)
                        .apply(RequestOptions.fitCenterTransform())
                        .into(binding.imageSample)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        saveQImage(imageUri)
                    } else {
                        saveImageInAlbum(this)
                    }
                } catch (e: NullPointerException) {
                    Toast.makeText(activity, "이미지 저장 실패", Toast.LENGTH_SHORT).show()
                }

            }
        }


    private fun dispatchTakePictureIntent() {

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        takePictureIntent.resolveActivity(packageManager)

        photoFile = try {

            createImageFile()

        } catch (e: IOException) {

            null
        }

        photoFile?.also {

            val photoURI: Uri =
                FileProvider.getUriForFile(this, "com.jsu.functionapp.fileprovider", it)

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        }
        cameraRequsetForResult.launch(takePictureIntent)
//        startActivityForResult(takePictureIntent, REQUEST_CAMERA)
    }

    // 안드로이드 Q 버전(API29) 이상인 경우
    private fun saveQImage(uri: Uri) {

        val values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, name)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        values.put(MediaStore.Images.Media.IS_PENDING, 1)

        val contentResolver = contentResolver
        val item = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        try {

            var pdf: ParcelFileDescriptor? = null

            if (item != null) {
                pdf = contentResolver.openFileDescriptor(item, "w", null)
            }

            if (pdf == null) {
                Log.d("로그", "null")
            } else {

                val inputData = getBytes(uri)
                val fos = FileOutputStream(pdf.fileDescriptor)
                fos.write(inputData)
                fos.close()

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    values.clear()
                    values.put(MediaStore.Images.Media.IS_PENDING, 0)
                    if (item != null) {
                        contentResolver.update(item, values, null, null)
                    }
                }

            }

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Log.d("로그", "FileNotFoundException  : " + e.localizedMessage)
        } catch (e: Exception) {
            Log.d("로그", "FileOutPutStream" + e.message)
        }

    }

    private fun getBytes(imageUri: Uri): ByteArray? {
        val iStream: InputStream? = contentResolver.openInputStream(imageUri)
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var length: Int?

        while (iStream?.read(buffer).also { length = it } != -1) {
            length?.let { byteBuffer.write(buffer, 0, it) }
        }

        return byteBuffer.toByteArray()
    }

    private fun saveImageInAlbum(context: Context): Uri? {

        val file = photoFile

        //file은 널이 절대 아니고 file이 존재하지 않을 때 파일을 만들어라

        photoFile.let {
            if (file?.exists() == false) {
                file.mkdirs()
            }
        }

        val values = ContentValues()

        with(values) {
            put(MediaStore.Images.Media.TITLE, name)
            put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
            put(MediaStore.Images.Media.BUCKET_ID, name)
            put(MediaStore.Images.Media.DATA, currentPhotoPath)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }

        return context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )
    }
}