package com.julianvalencia.trekdescubre.ui.session

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julianvalencia.trekdescubre.ui.data.ResourceRemote
import com.julianvalencia.trekdescubre.ui.data.UserRepository
import emailValidator
import kotlinx.coroutines.launch

class SessionViewModel : ViewModel(){

    private val userRepository = UserRepository()
    private val _errorMsg : MutableLiveData<String?> = MutableLiveData()
    val errorMsg : LiveData<String?> = _errorMsg

    private val _registerSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val registerSuccess: LiveData<Boolean> = _registerSuccess
    fun validateFields(email: String, password: String) {
        if(email.isEmpty() || password.isEmpty()) {
            _errorMsg.value = "Debe digitar todos los campos"
        }else {
            if (password.length < 6) {
                _errorMsg.value = "La contraseña debe tener minimo 6 dígitos"
            } else {
                if (!emailValidator(email)) {
                    _errorMsg.value = "El formato electrónico está mal escrito, revise su formato"
                }else{
                    viewModelScope.launch {
                        val result = userRepository.loginUser(email, password)
                        result.let {resourceRemote ->
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
        }
    }
}