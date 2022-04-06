package com.muhammadhusniabdillah.inventariskti.home

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhammadhusniabdillah.inventariskti.*
import com.muhammadhusniabdillah.inventariskti.databinding.FragmentHomeBinding
import com.muhammadhusniabdillah.inventariskti.preferences.Constant
import com.muhammadhusniabdillah.inventariskti.preferences.PreferencesHelper
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by viewBinding()
    private var daDb: InventarisDB? = null
    private lateinit var sharedPref: PreferencesHelper
    private val args: HomeFragmentArgs by navArgs()

    private val viewModel : MainViewModel by viewModels {
        ViewModelFactory((activity?.application as MainApplication).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPrefAndDBStuff()

        val recycler = binding.viewRecyclerHome
        val recyclerAdapter = RecyclerAdapterHome()
        recycler.adapter = recyclerAdapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        fabAction()

        // observer
        viewModel.inventoryDataLive.observe(requireActivity()) { p0 ->
            p0.let { recyclerAdapter.submitList(it) }
        }

        // database input data
        GlobalScope.launch{
            args.inventarisData?.let { viewModel.createData(it) }
        }

        logout()

    }

    private fun fabAction() {
        binding.btnFabHome.setOnClickListener{
            val toAddPage = HomeFragmentDirections.actionHomeFragmentToAddDataFragment()
            findNavController().navigate(toAddPage)
        }
    }

    private fun sharedPrefAndDBStuff() {
        daDb = InventarisDB.getInstance(requireContext())
        sharedPref = PreferencesHelper(requireContext())
        binding.tvUsernameWelcomeHome.text = sharedPref.getString(Constant.SAVED_USERNAME)
    }

    private fun logout() {
        binding.btnLogoutFromHome.setOnClickListener {
            sharedPref.clear()
            showMessage("Logged Out")
            val toLoginPage = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            findNavController().navigate(toLoginPage)
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}