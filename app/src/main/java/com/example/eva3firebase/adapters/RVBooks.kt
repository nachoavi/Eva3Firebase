package com.example.eva3firebase.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eva3firebase.R
import com.example.eva3firebase.booksCrud.Books
import com.squareup.picasso.Picasso


class RVBooks(private val listBooks: List<Books>, val myOnClickListener: MiListenerInterface) : RecyclerView.Adapter<RVBooks.ViewHolder>() {

    interface MiListenerInterface {
        fun onClick(position: Int)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val textViewAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
        val textViewPublicationDate: TextView = itemView.findViewById(R.id.tvPublicationDate)
        val textViewBinding: TextView = itemView.findViewById(R.id.tvBinding)
        val textViewUrlImage: ImageView = itemView.findViewById(R.id.imagen)

        init {
            itemView.setOnClickListener {
                myOnClickListener.onClick(adapterPosition)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_books,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listBooks[position]
        holder.textViewTitle.text = currentItem.title
        holder.textViewAuthor.text = currentItem.author
        holder.textViewPublicationDate.text = currentItem.publicationDate
        holder.textViewBinding.text = currentItem.binding
        Picasso.get().load(currentItem.urlImage).into(holder.textViewUrlImage)
    }


    override fun getItemCount(): Int {
        return listBooks.size
    }




}