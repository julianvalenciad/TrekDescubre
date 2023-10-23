package com.julianvalencia.trekdescubre.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julianvalencia.trekdescubre.ui.data.ResourceRemote
import com.julianvalencia.trekdescubre.ui.data.UserRepository
import emailValidator
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val userRepository = UserRepository()
    private val _errorMsg : MutableLiveData<String?> = MutableLiveData()
    val errorMsg : LiveData<String?> = _errorMsg

    fun validateFields(email: String, password: String, repPassword: String) {
        if(email.isEmpty() || password.isEmpty() || repPassword.isEmpty()){
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
                                        _errorMsg.postValue("Usuario creado exitosamente")
                                    }
                                    is ResourceRemote.Error -> {
                                        var msg = result.message
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

}