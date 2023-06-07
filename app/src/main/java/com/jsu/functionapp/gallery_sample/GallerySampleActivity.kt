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

class GallerySampleActivity : BaseVBActivity() {

    private val activity = this
    private lateinit var binding: ActivityGallerySampleBinding

    private var currentPhotoPath: String? = null
    private var name: String? = null
    private var photoFile: File? = null

    // 필요한 권한array Q(29)10 R(30)11
    private val arrNeedPermit_Q = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private val arrNeedPermit_R = arrayOf(android.Manifest.permission.CAMERA)
    private lateinit var arrNotPermit: ArrayList<String>  // 거절된 권한

    private val Permission_CAMERA = 0x1145

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGallerySampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        arrNotPermit = ArrayList()

        viewInit()

    }

    private fun viewInit() {


        setTitleBar("Gallery Sample", View.OnClickListener { onBackPressed() })

        //권한 체크
        if(!permitChecks()) {
            requestPermit()
        }

        binding.btnMove.setOnClickListener {
            val intent = Intent(activity,GalleryPhotoActivity::class.java)
            startActivity(intent)
            Log.d("tjddnr", "permit: ${permitChecks()}")
        }

        binding.btnPhoto.setOnClickListener {

             dispatchTakePictureIntent()


        }

    }

    //Todo.. Button눌렀을 때 권한 따로 분리하기 (카메라 권한, 외부저장소 접근 권한)
    private fun permitChecks(): Boolean {
        arrNotPermit.clear()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { //Android11, API 30 이상
            arrNeedPermit_R.forEach { needPermit ->
                when (ContextCompat.checkSelfPermission(this, needPermit)) {
                    PackageManager.PERMISSION_GRANTED -> Log.d("tjddnr", "필요 권한 있음: $needPermit")

                    PackageManager.PERMISSION_DENIED -> arrNotPermit.add(needPermit)

                }
            }
        } else {
            //Android10, API 29 이하
            arrNeedPermit_Q.forEach { needPermit ->
                when (ContextCompat.checkSelfPermission(this, needPermit)) {
                    PackageManager.PERMISSION_GRANTED -> Log.d("tjddnr", "필요 권한 있음: $needPermit")

                    PackageManager.PERMISSION_DENIED -> arrNotPermit.add(needPermit)
                }
            }

        }

        return arrNotPermit.size == 0
    }

    private fun requestPermit() {
        // 권한 없는 상태
        arrNotPermit.forEach { notPermit -> //android.permission.WRITE_EXTERNAL_STORAGE or android.permission.CAMERA
            Log.d("tjddnr","notPermit: ${notPermit}")
            when (activity.shouldShowRequestPermissionRationale(notPermit)) {
                //true -> 거부한적 있을경우
                true -> {
                    Log.d("tjddnr", "권한X, 이전에 권한 거부한적 있음 , M(23) 안드6 이상")

                    Toast.makeText(activity, "카메라 및 저장공간 권한을 허용해 주세요.", Toast.LENGTH_LONG).show()

                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        data = Uri.fromParts("package", packageName, null)
                    }
                    startActivity(intent)
                    finish()
                }

                //첫 권한요청 or 다시보지않기(2번거절) 일 경우
                else -> Log.d("tjddnr", "권한X, 최초 요청 / 다시보지않기(2번거절) , M(23) 안드6 이상")
            }
        }

        Log.d("tjddnr", "권한X, 요청 $arrNotPermit")
        ActivityCompat.requestPermissions(this, arrNotPermit.toTypedArray(), Permission_CAMERA)


    }


    //Image File생성
    @Throws(IOException::class)
    private fun createImageFile(): File {

        var storageDir: File? = null
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
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

            val photoURI: Uri = FileProvider.getUriForFile(this, "com.jsu.functionapp.fileprovider", it)

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

            if (item != null) { pdf = contentResolver.openFileDescriptor(item, "w", null) }

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