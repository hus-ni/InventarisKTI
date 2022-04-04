package com.muhammadhusniabdillah.inventariskti.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.muhammadhusniabdillah.inventariskti.R

class LoginActivity : AppCompatActivity() {

    companion object {
        const val FAILURE_CODE = 0.toLong()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}