package com.example.eva3firebase.booksCrud

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eva3firebase.MainActivity
import com.example.eva3firebase.R
import com.example.eva3firebase.adapters.RVBooks
import com.example.eva3firebase.genresCrud.AddGenresActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListBookActivity2 : AppCompatActivity(),RVBooks.MiListenerInterface{
    lateinit var rvBooks: RecyclerView
    private val books: MutableList<Books> = mutableListOf()

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
        setContentView(R.layout.activity_list_book2)


        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("books")

        rvBooks = findViewById(R.id.rvBooks)

        val onCreateThis = this

        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                books.clear()

                for (objSnapshot in snapshot.children){
                    val book = objSnapshot.getValue(Books::class.java)!!
                        books.add(book)
                }
                val booksAdapter = RVBooks(books,onCreateThis)
                rvBooks.adapter = booksAdapter
                rvBooks.layoutManager = LinearLayoutManager(onCreateThis)
                rvBooks.setHasFixedSize(true)

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
                val bookToDelete = books[position]

                // Borrar el libro de la base de datos
                databaseReference.child(bookToDelete.id.toString()).removeValue()

                // Actualizar la lista y el RecyclerView
                books.removeAt(position)
                rvBooks.adapter?.notifyItemRemoved(position)

                Toast.makeText(applicationContext, "Libro eliminado exitosamente!!", Toast.LENGTH_LONG).show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvBooks)
    }

    override fun onClick(position: Int) {
        val intent = Intent(this, UpdateBookActivity::class.java)
        val selectedBook = books[position]
        intent.putExtra("selectedBook",selectedBook)
        startActivity(intent)
    }


}










