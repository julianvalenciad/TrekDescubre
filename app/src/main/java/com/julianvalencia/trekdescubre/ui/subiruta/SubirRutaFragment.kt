package com.julianvalencia.trekdescubre.ui.subiruta

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.julianvalencia.trekdescubre.databinding.FragmentSubirRutaBinding
import com.julianvalencia.trekdescubre.ui.navigation.NavigationActivity


class SubirRutaFragment : Fragment() {

    private lateinit var subirRutaViewModel: SubirRutaViewModel
    private lateinit var subirRutaBinding: FragmentSubirRutaBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        subirRutaBinding = FragmentSubirRutaBinding.inflate(inflater, container, false)
        subirRutaViewModel = ViewModelProvider(this)[SubirRutaViewModel::class.java]
        val view = subirRutaBinding.root

        subirRutaViewModel.errorMsg.observe(viewLifecycleOwner){msg->
            showErrorMsg(msg)
        }
        subirRutaViewModel.createRutaSuccess.observe(viewLifecycleOwner){msg->
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }


        subirRutaBinding.subirRuta.setOnClickListener {
            val nombre: String = subirRutaBinding.nombreRuta.text.toString()
            val ubicacion: String = subirRutaBinding.ubicacionRuta.text.toString()
            val distancia: String = subirRutaBinding.distanciaRuta.text.toString()
            val seguridad: String = subirRutaBinding.seguridadRuta.text.toString()
            val dificultad: String = subirRutaBinding.dificultadRuta.text.toString()
            val descripcion: String = subirRutaBinding.descripcionRuta.text.toString()

            subirRutaViewModel.validateFields(nombre, ubicacion, distancia, seguridad, dificultad, descripcion)

        }


        return view
    }

    private fun showErrorMsg(msg: String?) {
        Toast.makeText(requireActivity(),msg,Toast.LENGTH_LONG).show()
    }
}