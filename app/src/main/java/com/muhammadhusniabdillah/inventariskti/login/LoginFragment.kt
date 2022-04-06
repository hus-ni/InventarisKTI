package com.muhammadhusniabdillah.inventariskti.login

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.muhammadhusniabdillah.inventariskti.InventarisDB
import com.muhammadhusniabdillah.inventariskti.R
import com.muhammadhusniabdillah.inventariskti.databinding.FragmentLoginBinding
import com.muhammadhusniabdillah.inventariskti.preferences.Constant
import com.muhammadhusniabdillah.inventariskti.preferences.PreferencesHelper
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding: FragmentLoginBinding by viewBinding()
    private var daDb: InventarisDB? = null
    private lateinit var sharedPref: PreferencesHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        daDb = InventarisDB.getInstance(requireContext())
        sharedPref = PreferencesHelper(requireContext())

        val usernameLogin = binding.etInputUsernameLogin
        val passwordLogin = binding.etInputPasswordLogin

        // text watcher
        usernameLogin.addTextChangedListener(usernameTextWatcher())
        passwordLogin.addTextChangedListener(passwordTextWatcher())

        // checker when edit text left without input
        editTextFocusChangeListener(usernameLogin, passwordLogin)

        // when login button clicked
        binding.btnLogin.setOnClickListener {
            val usernameValue = usernameLogin.text.toString()
            val passwordValue = passwordLogin.text.toString()
            when {
                usernameValue.isEmpty() -> binding.etLayoutUsernameLogin.error =
                    "This field must be filled!"
                passwordValue.isEmpty() -> binding.etLayoutPasswordLogin.error =
                    "This field must be filled!"
                else ->
                    GlobalScope.launch {
                        val login =
                            daDb!!.loginDao()
                                .isThisUsernamePasswordExists(usernameValue, passwordValue)

                        activity?.runOnUiThread {
                            if (login) {
                                saveSession(usernameValue, login)
                                toHomePage()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Invalid Username or Password!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
            }
        }
        // when register button clicked
        binding.tvRegister.setOnClickListener {
            val toRegisterPage = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(toRegisterPage)
        }
        exitOnBackPressed()
    }

    private fun exitOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            })
    }

    override fun onStart() {
        super.onStart()
        // if logged in before, go straight to home page
        if (sharedPref.getBoolean(Constant.IS_LOGIN)) {
            toHomePage()
        }
    }

    private fun toHomePage() {
        val toHomePage =
            LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        findNavController().navigate(toHomePage)
    }

    private fun saveSession(usernameValue: String, isLogin: Boolean) {
        sharedPref.putString(Constant.SAVED_USERNAME, usernameValue)
        sharedPref.putBoolean(Constant.IS_LOGIN, isLogin)
    }

    // function validate edittext
    fun usernameValidateEditText(text: Editable?) {
        if (TextUtils.isEmpty(text)) {
            binding.etLayoutUsernameLogin.error = "Username must be filled!"
        } else {
            binding.etLayoutUsernameLogin.error = null
        }
    }

    // function validate edittext
    fun passwordValidateEditText(text: Editable?) {
        if (TextUtils.isEmpty(text)) {
            binding.etLayoutPasswordLogin.error = "Password must be filled!"
        } else {
            binding.etLayoutPasswordLogin.error = null
        }
    }

    private fun usernameTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                usernameValidateEditText(p0)
            }
        }
    }

    private fun passwordTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                passwordValidateEditText(p0)
            }
        }
    }

    private fun editTextFocusChangeListener(
        username: TextInputEditText,
        password: TextInputEditText
    ) {
        username.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // do nothing
            } else {
                usernameValidateEditText(username.text)
            }
        }
        password.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // do nothing
            } else {
                passwordValidateEditText(password.text)
            }
        }
    }
}