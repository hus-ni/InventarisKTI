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
import com.muhammadhusniabdillah.inventariskti.MainActivity
import com.muhammadhusniabdillah.inventariskti.R
import com.muhammadhusniabdillah.inventariskti.databinding.FragmentRegisterBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding: FragmentRegisterBinding by viewBinding()
    private var daDb: InventarisDB? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        daDb = InventarisDB.getInstance(requireContext())
        val username = binding.etInputUsernameRegister
        val password = binding.etInputPasswordRegister
        val passwordConfirm = binding.etInputConfirmPasswordRegister
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
        val passwordConfirmTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                passwordConfirmValidateRegister(p0)
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
        passwordConfirm.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // do nothing
            } else {
                passwordConfirmValidateRegister(password.text)
            }
        }

        // when text is changed implement input text watcher
        username.addTextChangedListener(usernameTextWatcher)
        password.addTextChangedListener(passwordTextWatcher)
        passwordConfirm.addTextChangedListener(passwordConfirmTextWatcher)


        binding.btnRegister.setOnClickListener {
            val usernameValue = username.text.toString()
            val passwordValue = password.text.toString()
            val passwordConfirmValue = passwordConfirm.text.toString()
            val emailValue = email.text.toString()
            val registerData = TableLogin(
                null,
                usernameValue,
                passwordValue,
                emailValue
            )

            when {
                // check no field is empty except email field
                usernameValue.isEmpty() -> {
                    binding.etLayoutUsernameRegister.error = "Username must be filled!"
                }
                passwordValue.isEmpty() -> {
                    binding.etLayoutPasswordRegister.error = "Password must be filled!"
                }
                passwordConfirm.text.toString().isEmpty() -> {
                    binding.etLayoutConfirmPasswordRegister.error = "This field must be filled!"
                }
                else -> {
                    // check password confirm is match or not
                    if (passwordConfirmValue == passwordValue) {
                        GlobalScope.launch {
                            val readUsers = daDb!!.loginDao().isUserExists(usernameValue)

                            activity?.runOnUiThread {
                                //check user exist, if exists cant be use
                                if (readUsers) {
                                    binding.etLayoutUsernameRegister.error =
                                        "Username is not available!"
                                } else {
                                    // after all field and requirement fulfilled, add data to table
                                    GlobalScope.launch {
                                        val createUser = daDb!!.loginDao().createLogin(registerData)

                                        activity?.runOnUiThread {
                                            if (createUser != MainActivity.FAILURE_CODE) {
                                                Toast.makeText(
                                                    requireContext(),
                                                    "Registered Successfully!",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                // back to login page when succeed
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
                    } else {
                        binding.etLayoutConfirmPasswordRegister.error = "Password not match!"
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

    fun passwordConfirmValidateRegister(inputText: Editable?) {
        if (TextUtils.isEmpty(inputText)) {
            binding.etLayoutConfirmPasswordRegister.error = "This field must be filled!"
        } else {
            binding.etLayoutConfirmPasswordRegister.error = null
        }
    }

}