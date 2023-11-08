package com.julianvalencia.trekdescubre.ui.megustas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.toObject
import com.julianvalencia.trekdescubre.data.ResourceRemote
import com.julianvalencia.trekdescubre.data.RutasMeGustaRepository
import com.julianvalencia.trekdescubre.model.Rutas
import kotlinx.coroutines.launch

class MeGustasViewModel : ViewModel() {

    private val rutasRepository = RutasMeGustaRepository()

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

    fun createfav(rutas: Rutas?){
        viewModelScope.launch {
            val result = rutas?.let { rutasRepository.createRuta(it) }
        }
    }

}