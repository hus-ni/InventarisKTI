package com.muhammadhusniabdillah.inventariskti.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.navigation.fragment.findNavController
import com.muhammadhusniabdillah.inventariskti.InventarisDB
import com.muhammadhusniabdillah.inventariskti.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private val binding: FragmentLoginBinding by viewBinding()
    private var daDb: InventarisDB? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        daDb = InventarisDB.getInstance(requireContext())

        binding.btnRegister.setOnClickListener {
            val toRegisterPage = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(toRegisterPage)
        }
    }
}