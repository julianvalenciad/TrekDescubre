package com.julianvalencia.trekdescubre.ui.session

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.julianvalencia.trekdescubre.R
import com.julianvalencia.trekdescubre.databinding.ActivitySessionBinding
import com.julianvalencia.trekdescubre.ui.main.MainActivity
import com.julianvalencia.trekdescubre.ui.navigation.NavigationActivity
import com.julianvalencia.trekdescubre.ui.register.RegisterActivity
import com.julianvalencia.trekdescubre.ui.register.RegisterViewModel


class SessionActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivitySessionBinding
    private lateinit var sessionViewModel: SessionViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivitySessionBinding.inflate(layoutInflater)
        sessionViewModel = ViewModelProvider(this)[SessionViewModel::class.java]
        val view = mainBinding.root
        setContentView(view)

        mainBinding.buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        sessionViewModel.registerSuccess.observe(this){msg->
            val intent = Intent(this, NavigationActivity::class.java)
            startActivity(intent)
        }

        mainBinding.buttonInicio.setOnClickListener {
            val email = mainBinding.emailSesion.text.toString()
            val password = mainBinding.passwordSesion.text.toString()
            sessionViewModel.validateFields(email, password)
            //val intent = Intent(this, NavigationActivity::class.java)
            //startActivity(intent)
        }
    }
}