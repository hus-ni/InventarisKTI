@file:Suppress("DEPRECATION")

package com.muhammadhusniabdillah.inventariskti.home.scanner

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.muhammadhusniabdillah.inventariskti.R
import com.muhammadhusniabdillah.inventariskti.databinding.FragmentScannerBinding

class ScannerFragment : Fragment(R.layout.fragment_scanner) {

    private val binding: FragmentScannerBinding by viewBinding()

    @VisibleForTesting
    lateinit var viewModel: ViewFinderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!::viewModel.isInitialized) {
            viewModel = ViewModelProvider(this)[ViewFinderViewModel::class.java]
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(binding.viewScanner)
        viewModel.setData(binding.viewScanner.barcode)
        viewModel.barcode.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resources.Success -> {
                    binding.progress.visibility = View.GONE
                    val barcode = resource.data
                    val toAddDataFragment = ScannerFragmentDirections.actionScannerFragmentToAddDataFragment(barcode.displayValue!!)
                    findNavController().navigate(toAddDataFragment)
                }
                is Resources.Error -> {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Error", Toast.LENGTH_SHORT
                    ).show()
                    binding.viewScanner.resume()
                }
                is Resources.Loading -> {
                    binding.viewScanner.pause()
                    binding.progress.visibility = View.VISIBLE
                }
            }
        }

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestCameraPermission()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_PERMISSION_CAMERA -> if (
                grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                binding.viewScanner.start()
            }
            else ->
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.CAMERA
            )
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_PERMISSION_CAMERA
            )
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_PERMISSION_CAMERA
            )
        }
    }

    companion object {

        private const val REQUEST_PERMISSION_CAMERA = 1
    }

}