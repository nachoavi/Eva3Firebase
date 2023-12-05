package com.example.eva3firebase.genresCrud

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eva3firebase.R
import com.example.eva3firebase.adapters.RVGenres
import com.example.eva3firebase.booksCrud.AddBookActivity
import com.example.eva3firebase.booksCrud.ListBookActivity2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListGenresActivity : AppCompatActivity(),RVGenres.MiListenerInterface {
    lateinit var rvGenres: RecyclerView
    private val genres: MutableList<Genres> = mutableListOf()

    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference


    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_listBooks -> {
                    startActivity(Intent(this, ListBookActivity2::class.java))
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_addBooks -> {
                    startActivity(Intent(this, AddBookActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_addGenre -> {
                    startActivity(Intent(this, AddGenresActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_genres)


        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("genres")

        rvGenres = findViewById(R.id.rvGenres)

        val onCreateThis = this

        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)


        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                genres.clear()

                for (objSnapshot in snapshot.children) {
                    val genre = objSnapshot.getValue(Genres::class.java)!!
                    genres.add(genre)
                }
                val genresAdapter = RVGenres(genres, onCreateThis)
                rvGenres.adapter = genresAdapter
                rvGenres.layoutManager = LinearLayoutManager(onCreateThis)
                rvGenres.setHasFixedSize(true)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Obtener la posición del elemento deslizado
                val position = viewHolder.adapterPosition

                // Obtener el libro a borrar
                val genreToDelete = genres[position]

                // Borrar el libro de la base de datos
                databaseReference.child(genreToDelete.id.toString()).removeValue()

                // Actualizar la lista y el RecyclerView
                genres.removeAt(position)
                rvGenres.adapter?.notifyItemRemoved(position)

                Toast.makeText(
                    applicationContext,
                    "Genero eliminado!!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvGenres)
    }

    override fun onClick(position: Int) {
        TODO("Not yet implemented")
    }
}