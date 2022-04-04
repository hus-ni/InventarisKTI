package com.muhammadhusniabdillah.inventariskti.login

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.navigation.fragment.findNavController
import com.muhammadhusniabdillah.inventariskti.InventarisDB
import com.muhammadhusniabdillah.inventariskti.R
import com.muhammadhusniabdillah.inventariskti.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding: FragmentLoginBinding by viewBinding()
    private var daDb: InventarisDB? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        daDb = InventarisDB.getInstance(requireContext())
        // ngambil data dari edit text
//        val userInput = binding.etUsernameLogin
//        val passInput = binding.etPasswordLogin

        // null checker on edit text
        val usernameTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                usernameValidateEditText(p0)
            }
        }
        val passwordTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                passwordValidateEditText(p0)
            }
        }

        // checker pindah tanpa merubah isi
        binding.etUsernameLogin.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // do nothing
            } else {
                usernameValidateEditText(binding.etUsernameLogin.editText?.text)
            }
        }
        binding.etPasswordLogin.editText?.setOnFocusChangeListener{_, hasFocus ->
            if (hasFocus) {
                // do nothing
            } else {
                passwordValidateEditText(binding.etPasswordLogin.editText?.text)
            }
        }

        // textwatcher
        binding.etUsernameLogin.editText?.addTextChangedListener(usernameTextWatcher)
        binding.etPasswordLogin.editText?.addTextChangedListener(passwordTextWatcher)

        // when login button clicked
        binding.btnLogin.setOnClickListener {

//            val checkUserExists = daDb?.loginDao()?.isUserExists(userInput)
        }

        binding.btnRegister.setOnClickListener {
            val toRegisterPage = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(toRegisterPage)
        }
    }

    // function validate edittext
    fun usernameValidateEditText(text: Editable?) {
        if (TextUtils.isEmpty(text)) {
            binding.etUsernameLogin.error = "Username must be filled!"
        } else {
            binding.etUsernameLogin.error = null
        }
    }
    fun passwordValidateEditText(text: Editable?) {
        if (TextUtils.isEmpty(text)) {
            binding.etPasswordLogin.error = "Password must be filled!"
        } else {
            binding.etPasswordLogin.error = null
        }
    }
}