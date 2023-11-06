package com.julianvalencia.trekdescubre.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.julianvalencia.trekdescubre.R
import com.julianvalencia.trekdescubre.databinding.CardViewRutaItemBinding
import com.julianvalencia.trekdescubre.model.Rutas
import com.squareup.picasso.Picasso

class RutasAdapter(
    private val rutasList: MutableList<Rutas?>,
    private val onItemClicked: (Rutas?) -> Unit,
) : RecyclerView.Adapter<RutasAdapter.RutasViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RutasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_ruta_item, parent, false)
        return RutasViewHolder(view)
    }

    override fun getItemCount(): Int =rutasList.size

    override fun onBindViewHolder(holder: RutasViewHolder, position: Int) {
        val rutas = rutasList[position]
        holder.bind(rutas)
        holder.itemView.setOnClickListener{ onItemClicked(rutas)}
    }

    fun appendItem(newList: MutableList<Rutas?>){
        rutasList.clear()
        rutasList.addAll(newList)
        notifyDataSetChanged()
    }

    class RutasViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        private val binding = CardViewRutaItemBinding.bind(itemView)

        fun bind(rutas: Rutas?){
            with(binding){
                nameTextView.text = rutas?.nombre
                descripcion.text = rutas?.descripcion.toString()
                if (rutas?.urlpicture == null){
                    pictureImageView.setImageResource(R.drawable.logo)
                }else{
                    Picasso.get().load(rutas.urlpicture).into(pictureImageView)
                }
            }
        }
    }


}