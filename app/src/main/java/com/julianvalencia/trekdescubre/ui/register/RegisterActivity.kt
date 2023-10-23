package com.julianvalencia.trekdescubre.ui.register

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.julianvalencia.trekdescubre.R
import com.julianvalencia.trekdescubre.databinding.ActivityRegisterBinding
import com.julianvalencia.trekdescubre.ui.session.SessionActivity
import java.util.Calendar
import java.util.Locale

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    private var cal = Calendar.getInstance()
    private var FechaNacimiento:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        val view = registerBinding.root
        setContentView(view)

        registerViewModel.errorMsg.observe(this){msg->
            showErrorMsg(msg)
        }

        registerViewModel.registerSuccess.observe(this){msg->
            val intent = Intent(this, SessionActivity::class.java)
            startActivity(intent)
        }



        registerBinding.buttonInicio.setOnClickListener {
            val email: String = registerBinding.email.text.toString()
            val password: String = registerBinding.Password.text.toString()
            val repPassword: String = registerBinding.repPassword.text.toString()

            registerViewModel.validateFields(email, password, repPassword)



        }





        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val format = "MM/dd/yyyy"
                val sdf = SimpleDateFormat(format, Locale.US)
                FechaNacimiento = sdf.format(cal.time).toString()
                registerBinding.datebutton.setText(FechaNacimiento)
            }
        registerBinding.datebutton.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun showErrorMsg(msg: String?) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
    }
}