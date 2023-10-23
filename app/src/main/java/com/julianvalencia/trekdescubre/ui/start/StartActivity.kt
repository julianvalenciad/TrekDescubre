package com.julianvalencia.trekdescubre.ui.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.julianvalencia.trekdescubre.R
import com.julianvalencia.trekdescubre.databinding.ActivityStartBinding
import com.julianvalencia.trekdescubre.ui.register.RegisterActivity
import com.julianvalencia.trekdescubre.ui.session.SessionActivity

class StartActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityStartBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mainBinding.buttonStart.setOnClickListener {
            val intent = Intent(this, SessionActivity::class.java)
            startActivity(intent)
        }

        mainBinding.buttonRegistro.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}