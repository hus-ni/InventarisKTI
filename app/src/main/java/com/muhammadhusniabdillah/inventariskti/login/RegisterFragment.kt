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
        username.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // do nothing
            } else {
                usernameValidateRegister(username.text)
            }
        }

        password.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // do nothing
            } else {
                passwordValidateRegister(password.text)
            }
        }

        // when text is changed implement input text watcher
        username.addTextChangedListener(usernameTextWatcher)
        password.addTextChangedListener(passwordTextWatcher)

        binding.btnRegister.setOnClickListener {
            val usernameValue = username.text.toString()
            val passwordValue = password.text.toString()
            val emailValue = email.text.toString()
            val registerData = TableLogin(
                null,
                usernameValue,
                passwordValue,
                emailValue
            )
            when {
                usernameValue.isEmpty() -> {
                    binding.etLayoutUsernameRegister.error = "Username must be filled!"
                }
                passwordValue.isEmpty() -> {
                    binding.etLayoutPasswordRegister.error = "Password must be filled!"
                }
                else -> {
                    GlobalScope.async {
                        val readUsers = daDb!!.loginDao().isUserExists(username.text.toString())

                        activity?.runOnUiThread {
                            //check user exist
                            if (readUsers) {
                                Toast.makeText(
                                    requireContext(),
                                    "Username ${username.text.toString()} is not avalailable!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                binding.etLayoutUsernameRegister.error = "Username is not available!"
                            } else {
                                GlobalScope.async {
                                    val createUser = daDb!!.loginDao().createLogin(registerData)

                                    activity?.runOnUiThread {
                                        if (createUser != LoginActivity.FAILURE_CODE) {
                                            Toast.makeText(
                                                requireContext(),
                                                "Registered Successfully!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            val backToLoginPage =
                                                RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                                            findNavController().navigate(backToLoginPage)
                                        } else {
                                            Toast.makeText(
                                                requireContext(),
                                                "Register Failed!",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
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