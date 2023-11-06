package com.julianvalencia.trekdescubre.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julianvalencia.trekdescubre.data.ResourceRemote
import com.julianvalencia.trekdescubre.data.UserRepository
import com.julianvalencia.trekdescubre.model.User
import emailValidator
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val userRepository = UserRepository()
    private val _errorMsg : MutableLiveData<String?> = MutableLiveData()
    val errorMsg : LiveData<String?> = _errorMsg

    private val _registerSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val registerSuccess: LiveData<Boolean> = _registerSuccess

    fun validateFields(email: String, password: String, repPassword: String, fullname: String, genero: String, fechanacimiento: String) {
        if(email.isEmpty() || password.isEmpty() || repPassword.isEmpty() || fullname.isEmpty() || genero.isEmpty() || fechanacimiento.isEmpty()){
            _errorMsg.value = "Debe digitar todos los campos"
        }else{
            if(password!=repPassword){
                _errorMsg.value = "Las contraseñas deben de ser iguales"
            }else{
                if(password.length < 6){
                    _errorMsg.value = "La contraseña debe tener minimo 6 dígitos"
                }else{
                    if(!emailValidator(email)){
                        _errorMsg.value = "El formato electrónico está mal escrito, revise su formato"
                    }else{
                        viewModelScope.launch {
                            val result = userRepository.registerUser(email, password)
                            result.let {resourceRemote ->
                                when (resourceRemote){
                                    is ResourceRemote.Success -> {
                                        val uid = result.data
                                        uid?.let { Log.d("id User", it)}
                                        val user = User(uid, email, fullname, genero, fechanacimiento)
                                        createUser(user)

                                    }
                                    is ResourceRemote.Error -> {
                                        var msg = result.message
                                        when (msg){
                                            "The email address is already in use by another account." -> msg = "Ya existe una cuenta con ese correo electrónico"
                                            "A network error (such as timeout, interrupted connection or unreachable host) has occurred." -> msg = "Revise su conexión a internet"
                                        }
                                        _errorMsg.postValue(msg)
                                    }
                                    else ->{

                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    private fun createUser(user: User) {
        viewModelScope.launch {
            val result = userRepository.createUser(user)
            result.let{ resourceRemote ->
                when (resourceRemote){
                    is ResourceRemote.Success -> {
                        _registerSuccess.postValue(true)
                        _errorMsg.postValue("Usuario creado exitosamente")
                    }
                    is ResourceRemote.Error -> {
                        var msg = result.message
                        when (msg){
                            "The email address is already in use by another account." -> msg = "Ya existe una cuenta con ese correo electrónico"
                            "A network error (such as timeout, interrupted connection or unreachable host) has occurred." -> msg = "Revise su conexión a internet"
                        }
                        _errorMsg.postValue(msg)
                    }
                    else ->{

                    }
                }
            }
        }
    }

}