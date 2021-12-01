package com.artelsv.petprojectsecond.ui.profile

import android.Manifest
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.artelsv.petprojectsecond.databinding.FragmentProfileBinding
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import java.io.File
import java.util.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ProfileFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: ProfileViewModel

    private var currentPhotoPath: String? = null

    private val binding: FragmentProfileBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    private val requestMultiplePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { resultsMap ->
            resultsMap.forEach {
                Timber.tag("profile").i("Permission: ${it.key}, granted: ${it.value}")
            }
        }

    private val getCameraImage =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                binding.ivProfileImage.load(viewModel.saveUri.value)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prepareToTakePicture()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.setup(childFragmentManager)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
    }

    private fun setObservers() {
        viewModel.error.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
                viewModel.error.postValue(null)
            }
        })

        viewModel.isTakePicture.observe(viewLifecycleOwner, {
            takePicture()
        })
    }

    private fun hasCameraPermission() = ContextCompat.checkSelfPermission(requireContext(),
        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    private fun hasWriteExternalStoragePermission() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat.getDateTimeInstance().format(Date())
        val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun prepareToTakePicture() {
        requestMultiplePermissionLauncher.launch(arrayOf(Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE))
    }

    private fun takePicture() {
        if (hasCameraPermission() && hasWriteExternalStoragePermission()) {
            val uri =
                FileProvider.getUriForFile(requireContext(),
                    FILEPROVIDER_AUTHORITY,
                    createImageFile())

            getCameraImage.launch(uri)
            viewModel.saveUri(uri)
        } else {
            requestMultiplePermissionLauncher.launch(arrayOf(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE))
        }
    }

    companion object {
        const val FILEPROVIDER_AUTHORITY = "com.artelsv.fileprovider"
    }
}