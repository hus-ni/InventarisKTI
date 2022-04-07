package com.muhammadhusniabdillah.inventariskti.home

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.muhammadhusniabdillah.inventariskti.R
import com.muhammadhusniabdillah.inventariskti.databinding.FragmentAddDataBinding

class AddDataFragment : Fragment(R.layout.fragment_add_data) {
    private val binding: FragmentAddDataBinding by viewBinding()
    private val args: AddDataFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // set edit text text
        binding.etBarcode.editText?.setText(args.barcode)
        val barcode = binding.etBarcode.editText!!.text
        val spec = binding.etSpec.editText!!.text

        // spinner
        val locationSpinner: Spinner = binding.spinnerLocation
        var location: Any? = null
        spinnerAdapter(locationSpinner)
        // take selected item from spinner
        locationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                location = parent?.getItemAtPosition(position)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // do nothing
            }
        }
        // user
        val pic = binding.etPic.editText?.text
        // description
        val condition = binding.etDescription.editText?.text

        binding.btnAddData.setOnClickListener {
//            if (false) {
//                Toast.makeText(requireContext(), "All field must be filled!", Toast.LENGTH_SHORT)
//                    .show()
//            } else {
            val data = TableHome(
                null,
                barcode.toString(),
                spec.toString(),
                location.toString(),
                pic.toString(),
                condition.toString()
            )
            val toHomeWithData = AddDataFragmentDirections.actionAddDataFragmentToHomeFragment()
            toHomeWithData.inventarisData = data
            findNavController().navigate(toHomeWithData)
//            }
        }
        onBackPressed()

    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    AlertDialog.Builder(requireContext())
                        .setMessage("Discard changes ?")
                        .setPositiveButton("Yes") { _, _ ->
                            val backToLogin =
                                AddDataFragmentDirections.actionAddDataFragmentToHomeFragment()
                            findNavController().navigate(backToLogin)
                        }
                        .setNegativeButton("No") {dialog,_ ->
                            dialog.dismiss()
                        }
                        .show()

                }
            })
    }

    private fun spinnerAdapter(locationSpinner: Spinner) {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.location,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            locationSpinner.adapter = adapter
        }
    }

}