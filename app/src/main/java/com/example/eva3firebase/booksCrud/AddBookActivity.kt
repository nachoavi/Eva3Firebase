package com.example.eva3firebase.booksCrud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eva3firebase.R
import com.example.eva3firebase.genresCrud.AddGenresActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddBookActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    private  lateinit var editTextTitle : EditText
    private  lateinit var editTextAuthor : EditText
    private  lateinit var editTextPublicationDate : EditText
    private  lateinit var editTextBinding : EditText
    private  lateinit var editTextPages : EditText
    private lateinit var editTextUrlImage : EditText
    private  lateinit var btnInsert: Button
    private  lateinit var btnView: Button

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
                }R.id.navigation_addGenre -> {
                startActivity(Intent(this, AddGenresActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            }
            false
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference

        editTextTitle = findViewById(R.id.editTextTitle)
        editTextAuthor = findViewById(R.id.editTextAuthor)
        editTextPublicationDate = findViewById(R.id.editTextPublicationDate)
        editTextBinding = findViewById(R.id.editTextBinding)
        editTextPages = findViewById(R.id.editTextPages)
        editTextUrlImage = findViewById(R.id.editTextUrlImage)
        btnInsert = findViewById(R.id.buttonAddBook)
        btnView = findViewById(R.id.buttonViewBooks)

        btnInsert.setOnClickListener{
            insertData()
            Toast.makeText(applicationContext, "Libro agregado exitosamente!!", Toast.LENGTH_LONG).show()

        }

        btnView.setOnClickListener{
            val intent = Intent(this, ListBookActivity2::class.java)
            startActivity(intent)
        }


        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)



    }

    private fun insertData() {
        val title = editTextTitle.text.toString()
        val author = editTextAuthor.text.toString()
        val publicationDate = editTextPublicationDate.text.toString()
        val binding = editTextBinding.text.toString()
        val pages = editTextPages.text.toString().toInt()
        val urlImage = editTextUrlImage.text.toString()

        val bookId = databaseReference.child("books").push().key
        val book = Books(bookId,title,author,publicationDate,binding,pages,urlImage)

        databaseReference.child("books").child(bookId!!).setValue(book)

    }

}