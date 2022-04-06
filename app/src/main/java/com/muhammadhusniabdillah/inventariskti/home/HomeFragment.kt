package com.muhammadhusniabdillah.inventariskti.home

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.muhammadhusniabdillah.inventariskti.InventarisDB
import com.muhammadhusniabdillah.inventariskti.R
import com.muhammadhusniabdillah.inventariskti.databinding.FragmentHomeBinding
import com.muhammadhusniabdillah.inventariskti.preferences.Constant
import com.muhammadhusniabdillah.inventariskti.preferences.PreferencesHelper

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by viewBinding()
    private var daDb: InventarisDB? = null
    private lateinit var sharedPref: PreferencesHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = PreferencesHelper(requireContext())
        binding.tvUsernameWelcomeHome.text = sharedPref.getString(Constant.SAVED_USERNAME)

        binding.btnLogoutFromHome.setOnClickListener{
            sharedPref.clear()
            showMessage("Logged Out")
            toLoginPage()
        }
    }

    private fun toLoginPage() {
        val toLoginPage = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
        findNavController().navigate(toLoginPage)
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}