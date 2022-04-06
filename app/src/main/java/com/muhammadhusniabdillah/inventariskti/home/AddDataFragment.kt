package com.muhammadhusniabdillah.inventariskti.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.muhammadhusniabdillah.inventariskti.R
import com.muhammadhusniabdillah.inventariskti.databinding.FragmentAddDataBinding

class AddDataFragment : Fragment(R.layout.fragment_add_data) {
    private val binding: FragmentAddDataBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val barcode = binding.etBarcode.text
        val spec = binding.etSpec.text
        val location = binding.etLocation.text
        val pic = binding.etPic.text
        val condition = binding.etCondition.text


        binding.btnAddData.setOnClickListener {
            if (barcode.isEmpty() ||
                spec.isEmpty() ||
                location.isEmpty() ||
                pic.isEmpty() ||
                condition.isEmpty()) {
                Toast.makeText(requireContext(), "All field must be filled!", Toast.LENGTH_SHORT).show()
            } else {
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
            }
        }
    }
}