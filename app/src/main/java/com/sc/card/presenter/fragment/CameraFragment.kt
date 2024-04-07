package com.sc.card.presenter.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sc.card.databinding.CameraFragmentLayoutBinding
import com.sc.card.presenter.viewModel.CameraViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private const val TAG = "CameraFragment"
class CameraFragment: Fragment() {
    private val cameraViewModel by lazy {
        ViewModelProvider(this)[CameraViewModel::class.java]
    }

    private val binding by lazy {
        CameraFragmentLayoutBinding.inflate(layoutInflater)
    }

    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        imageCapture = view?.display?.let { ImageCapture.Builder().setTargetRotation(it.rotation).build() }
        if(cameraViewModel.allPermissionsGranted(requireContext())){
            startCamera()
        }else{
            cameraViewModel.requestPermissions(
                this,
                ::startCamera
            ) {
                Toast.makeText(
                    requireContext(),
                    "Permission request denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.takePhotoButton.setOnClickListener {
            cameraViewModel.takePhoto(
                requireActivity(),
                imageCapture
            )
        }
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.cameraViewFinder.surfaceProvider)
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                if(imageCapture!=null){
                    cameraProvider.bindToLifecycle(this, cameraSelector, imageCapture, preview)
                }else{
                    cameraProvider.bindToLifecycle(this, cameraSelector, preview)
                }

            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireActivity()))
    }

}