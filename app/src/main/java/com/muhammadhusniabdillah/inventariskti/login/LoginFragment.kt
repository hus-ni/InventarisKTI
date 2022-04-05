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
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding: FragmentLoginBinding by viewBinding()
    private var daDb: InventarisDB? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        daDb = InventarisDB.getInstance(requireContext())

        val usernameLogin = binding.etInputUsernameLogin
        val passwordLogin = binding.etInputPasswordLogin
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

        // checker move without changing input
        usernameLogin.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // do nothing
            } else {
                usernameValidateEditText(usernameLogin.text)
            }
        }
        passwordLogin.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // do nothing
            } else {
                passwordValidateEditText(passwordLogin.text)
            }
        }

        // text watcher
        usernameLogin.addTextChangedListener(usernameTextWatcher)
        passwordLogin.addTextChangedListener(passwordTextWatcher)

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
                            daDb!!.loginDao().isThisUsernamePasswordExists(usernameValue, passwordValue)

                        activity?.runOnUiThread {
                            if (login) {
                                val toHomePage = LoginFragmentDirections.actionLoginFragmentToHomeFragment(usernameValue)
                                findNavController().navigate(toHomePage)
                            }
                        }
                    }
            }

            binding.tvRegister.setOnClickListener {
                val toRegisterPage = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                findNavController().navigate(toRegisterPage)
            }
        }
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
}