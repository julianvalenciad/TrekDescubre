package com.julianvalencia.trekdescubre.ui.subiruta

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julianvalencia.trekdescubre.data.ResourceRemote
import com.julianvalencia.trekdescubre.data.RutasRepository
import com.julianvalencia.trekdescubre.data.UserRepository
import com.julianvalencia.trekdescubre.model.Rutas
import kotlinx.coroutines.launch

class SubirRutaViewModel : ViewModel() {

    val rutasRepository = RutasRepository()

    private val _errorMsg : MutableLiveData<String?> = MutableLiveData()
    val errorMsg : LiveData<String?> = _errorMsg

    private val _createRutaSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val createRutaSuccess: LiveData<Boolean> = _createRutaSuccess
    fun validateFields(nombre: String, ubicacion: String, distancia: String, seguridad: String, dificultad: String, descripcion: String, urlPicture: String) {
        if(nombre.isEmpty() || ubicacion.isEmpty() || distancia.isEmpty() || seguridad.isEmpty() || dificultad.isEmpty() || descripcion.isEmpty()){
            _errorMsg.value = "Debe digitar todos los campos"
        }else{
            val ruta = Rutas(nombre = nombre, ubicacion = ubicacion, distancia = distancia, seguridad = seguridad, dificultad = dificultad, descripcion = descripcion, urlpicture = urlPicture)
            viewModelScope.launch {
                val result = rutasRepository.createRuta(ruta)
                result.let {resourceRemote ->
                    when (resourceRemote){
                        is ResourceRemote.Success -> {
                            _createRutaSuccess.postValue(true)
                            _errorMsg.postValue("Ruta creada con exito")
                        }
                        is ResourceRemote.Error -> {
                            var msg = result.message
                            when (msg){
                                "The email address is already in use by another account." -> msg = "Ya existe una cuenta con ese correo electrónico"
                                "A network error (such as timeout, interrupted connection or unreachable host) has occurred." -> msg = "Revise su conexión a internet"
                                "An internal error has occurred. [ INVALID_LOGIN_CREDENTIALS ]" -> msg = "Correo electrónico o contraseña invalida"
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