package com.julianvalencia.trekdescubre.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.toObject
import com.julianvalencia.trekdescubre.data.ResourceRemote
import com.julianvalencia.trekdescubre.data.RutasRepository
import com.julianvalencia.trekdescubre.model.Rutas
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val rutasRepository = RutasRepository()

    private var rutasListLocal = mutableListOf<Rutas?>()

    private val _errorMsg : MutableLiveData<String?> = MutableLiveData()
    val errorMsg : LiveData<String?> = _errorMsg

    private val _rutasList: MutableLiveData<MutableList<Rutas?>> = MutableLiveData()
    val rutasList : LiveData<MutableList<Rutas?>> = _rutasList
    fun loadRutas() {
        rutasListLocal.clear()
        viewModelScope.launch {
            val result = rutasRepository.loadRutas()
            result.let{resourceRemote ->
                when(resourceRemote){
                    is ResourceRemote.Success -> {
                        result.data?.documents?.forEach { document->
                            val ruta = document.toObject<Rutas?>()
                            rutasListLocal.add(ruta)
                        }
                        _rutasList.postValue(rutasListLocal)
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