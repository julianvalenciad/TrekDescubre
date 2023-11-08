package com.julianvalencia.trekdescubre.ui.megustas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.julianvalencia.trekdescubre.databinding.FragmentMeGustasBinding
import com.julianvalencia.trekdescubre.model.Rutas
import com.julianvalencia.trekdescubre.ui.home.RutasAdapter


class MeGustasFragment : Fragment() {
    private lateinit var meGustasBinding: FragmentMeGustasBinding
    private lateinit var  meGustasViewModel: MeGustasViewModel
    private lateinit var  rutasAdapter: RutasAdapter
    private var rutasList = mutableListOf<Rutas?>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val homeViewModel = ViewModelProvider(this).get(MeGustasViewModel::class.java)
        meGustasBinding = FragmentMeGustasBinding.inflate(inflater, container, false)

        val view = meGustasBinding.root

        meGustasViewModel.loadRutas()

        meGustasViewModel.errorMsg.observe(viewLifecycleOwner){msg->
            showErrorMsg(msg)
        }

        meGustasViewModel.rutasList.observe(viewLifecycleOwner){rutasList ->
            rutasAdapter.appendItem(rutasList)
        }

        rutasAdapter = RutasAdapter(rutasList, onItemClicked = {onRutasItemClicked(it)})

        meGustasBinding.listaRutas.apply{
            layoutManager = LinearLayoutManager(this@MeGustasFragment.requireContext())
            adapter = rutasAdapter
            setHasFixedSize(false)
        }

        return view
    }

    private fun onRutasItemClicked(rutas: Rutas?): Unit {
        meGustasViewModel.createfav(rutas)
    }
    private fun showErrorMsg(msg: String?) {
        Toast.makeText(requireActivity(),msg, Toast.LENGTH_LONG).show()
    }

}