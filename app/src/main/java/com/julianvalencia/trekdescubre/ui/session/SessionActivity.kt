package com.julianvalencia.trekdescubre.ui.session

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.julianvalencia.trekdescubre.R
import com.julianvalencia.trekdescubre.databinding.ActivitySessionBinding
import com.julianvalencia.trekdescubre.ui.main.MainActivity
import com.julianvalencia.trekdescubre.ui.navigation.NavigationActivity
import com.julianvalencia.trekdescubre.ui.register.RegisterActivity


class SessionActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivitySessionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivitySessionBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mainBinding.buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        mainBinding.buttonInicio.setOnClickListener {
            val intent = Intent(this, NavigationActivity::class.java)
            startActivity(intent)
        }
    }
}