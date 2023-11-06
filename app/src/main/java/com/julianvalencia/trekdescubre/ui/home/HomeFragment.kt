package com.julianvalencia.trekdescubre.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.julianvalencia.trekdescubre.R
import com.julianvalencia.trekdescubre.databinding.FragmentHomeBinding
import com.julianvalencia.trekdescubre.model.Rutas

class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var  homeViewModel: HomeViewModel
    private lateinit var  rutasAdapter: RutasAdapter
    private var rutasList = mutableListOf<Rutas?>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)

        val view = homeBinding.root

        homeViewModel.loadRutas()

        homeViewModel.errorMsg.observe(viewLifecycleOwner){msg->
            showErrorMsg(msg)
        }

        homeViewModel.rutasList.observe(viewLifecycleOwner){rutasList ->
            rutasAdapter.appendItem(rutasList)

        }

        rutasAdapter = RutasAdapter(rutasList, onItemClicked = {onRutasItemClicked(it)})

        homeBinding.listaRutas.apply{
            layoutManager = LinearLayoutManager(this@HomeFragment.requireContext())
            adapter = rutasAdapter
            setHasFixedSize(false)
        }


        return view
    }

    private fun onRutasItemClicked(rutas: Rutas?) {

    }
    private fun showErrorMsg(msg: String?) {
        Toast.makeText(requireActivity(),msg, Toast.LENGTH_LONG).show()
    }
}