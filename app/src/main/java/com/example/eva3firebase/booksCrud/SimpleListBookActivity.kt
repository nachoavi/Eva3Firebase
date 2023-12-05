package com.example.eva3firebase.booksCrud

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eva3firebase.R
import com.example.eva3firebase.adapters.RVBooks
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SimpleListBookActivity : AppCompatActivity(),RVBooks.MiListenerInterface {
    lateinit var rvBooks: RecyclerView
    private val books: MutableList<Books> = mutableListOf()

    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_list_book)
        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("books")
        rvBooks = findViewById(R.id.rvBooks)
        val onCreateThis = this

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

    }

    override fun onClick(position: Int) {
        val selectedBook = books[position]
        Toast.makeText(applicationContext, "${selectedBook.title}!!", Toast.LENGTH_LONG).show()
    }


}