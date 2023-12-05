package com.example.eva3firebase.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eva3firebase.R
import com.example.eva3firebase.genresCrud.Genres

class RVGenres(private val listGenres: List<Genres>, val myOnClickListener: MiListenerInterface) : RecyclerView.Adapter<RVGenres.ViewHolder>() {

    interface MiListenerInterface {
        fun onClick(position: Int)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewGenre: TextView = itemView.findViewById(R.id.tvGenre)


        init {
            itemView.setOnClickListener {
                myOnClickListener.onClick(adapterPosition)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_genres,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listGenres[position]
        holder.textViewGenre.text = currentItem.genre

    }


    override fun getItemCount(): Int {
        return listGenres.size
    }




}