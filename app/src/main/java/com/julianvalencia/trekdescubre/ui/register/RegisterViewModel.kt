package com.julianvalencia.trekdescubre.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    private val _errorMsg : MutableLiveData<String> = MutableLiveData()
    val errorMsg : LiveData<String> = _errorMsg

    fun validateFields(email: String, password: String, repPassword: String) {
        if(email.isEmpty() || password.isEmpty() || repPassword.isEmpty()){
            _errorMsg.value = "Debe digitar todos los campos"
        }else{
            if(password!=repPassword){
                _errorMsg.value = "Las contraseñas deben de ser iguales"
            }else{
                if(password.length < 6){
                    _errorMsg.value = "La contraseña debe tener minimo 6 dígitos"
                }
            }
        }
    }

}