package com.muhammadhusniabdillah.inventariskti.login

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.muhammadhusniabdillah.inventariskti.InventarisDB
import com.muhammadhusniabdillah.inventariskti.R
import com.muhammadhusniabdillah.inventariskti.databinding.FragmentRegisterBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding: FragmentRegisterBinding by viewBinding()
    private var daDb: InventarisDB? = null

    @kotlin.OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        daDb = InventarisDB.getInstance(requireContext())
         val username = binding.etInputUsernameRegister
         val password = binding.etInputPasswordRegister
         val email = binding.etInputEmailRegister

        // input text watcher
        val usernameTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                usernameValidateRegister(p0)
            }
        }
        val passwordTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                passwordValidateRegister(p0)
            }
        }

        // focus checker
        username.setOnFocusChangeListener{ _, hasFocus ->
            if (hasFocus) {
                // do nothing
            } else {
                usernameValidateRegister(username.text)
            }
        }
        password.setOnFocusChangeListener{_, hasFocus ->
            if (hasFocus) {
                // do nothing
            } else {
                passwordValidateRegister(password.text)
            }
        }

        // when text is changed implement input text watcher
        username.addTextChangedListener(usernameTextWatcher)
        password.addTextChangedListener(passwordTextWatcher)

        binding.btnRegister.setOnClickListener{
            val registerData = TableLogin(
                null,
                username.text.toString(),
                password.text.toString(),
                email.text.toString()
            )

            GlobalScope.async {
                val registUser =daDb?.loginDao()?.createLogin(registerData)
                activity?.runOnUiThread{
                    if (registUser != LoginActivity.FAILURE_CODE) {
                        Toast.makeText(requireContext(), "Registered Successfully!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Register Failed!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            val backToLoginPage = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(backToLoginPage)
        }
    }

    fun usernameValidateRegister(inputText: Editable?) {
        if (TextUtils.isEmpty(inputText)) {
            binding.etLayoutUsernameRegister.error = "Username must be filled!"
        } else {
            binding.etLayoutUsernameRegister.error = null
        }
    }

    fun passwordValidateRegister(inputText: Editable?) {
        if (TextUtils.isEmpty(inputText)) {
            binding.etLayoutPasswordRegister.error = "Password must be filled!"
        } else {
            binding.etLayoutPasswordRegister.error = null
        }
    }

}