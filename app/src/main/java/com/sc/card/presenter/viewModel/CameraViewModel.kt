package com.sc.card.presenter.viewModel

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.util.Base64
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.ByteArrayOutputStream
import javax.inject.Inject


@HiltViewModel
class CameraViewModel @Inject constructor(
): ViewModel() {

    private val REQUIRED_PERMISSIONS =
        mutableListOf(Manifest.permission.CAMERA)
            .apply {
                if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.P){
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()


    fun allPermissionsGranted(context: Context) = REQUIRED_PERMISSIONS.all { permission ->
        ContextCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissions(
        fragment: Fragment,
        onGranted: () -> Unit,
        onNotGranted: () -> Unit,
    ){
        val activityResultLauncher =
            fragment.registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions())
            { permissions ->
                permissionForResult(
                    permissions,
                    onGranted,
                    onNotGranted,
                )
            }
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private fun permissionForResult(
        permissions: Map<String,Boolean>,
        onGranted: () -> Unit,
        onNotGranted: () -> Unit,
    ){
        // Handle Permission granted/rejected
        var permissionGranted = true
        permissions.entries.forEach {
            if (it.key in REQUIRED_PERMISSIONS && !it.value)
                permissionGranted = false
        }
        if (!permissionGranted) {
            onNotGranted()
        } else {
            onGranted()
        }
    }

    fun takePhoto(
        activity: Activity,
        imageCap: ImageCapture?,
        onCaptured: (String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCap ?: return

        // Set up image capture listener, which is triggered after photo has
        // been taken

        imageCapture.takePicture(
            ContextCompat.getMainExecutor(activity),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    val imageBitmap = image.toBitmap()
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                    val byteArray = byteArrayOutputStream.toByteArray()
                    val encodedBitmap: String = Base64.encodeToString(byteArray, Base64.DEFAULT)
                    onCaptured(encodedBitmap)
                }

                override fun onError(exception: ImageCaptureException) {
                    onError(exception)
                }
            }
        )

    }

}