package com.example.eva3firebase.booksCrud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eva3firebase.R
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class UpdateBookActivity : AppCompatActivity() {
    private  lateinit var editTextTitle : EditText
    private  lateinit var editTextAuthor : EditText
    private  lateinit var editTextPublicationDate : EditText
    private  lateinit var editTextBinding : EditText
    private  lateinit var editTextPages : EditText
    private lateinit var editTextUrlImage : EditText
    private lateinit var btnUpdate: Button
    private lateinit var btnView: Button


    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_book)


        database = Firebase.database.reference

        editTextTitle = findViewById(R.id.editTextTitle)
        editTextAuthor = findViewById(R.id.editTextAuthor)
        editTextPublicationDate = findViewById(R.id.editTextPublicationDate)
        editTextBinding = findViewById(R.id.editTextBinding)
        editTextPages = findViewById(R.id.editTextPages)
        editTextUrlImage = findViewById(R.id.editTextUrlImage)
        btnUpdate = findViewById(R.id.buttonUpdateBook)
        btnView = findViewById(R.id.buttonViewBooks)

        val book = intent.getParcelableExtra<Books>("selectedBook")!!
        val bookReference = database.child("books").child(book.id!!)

        fillForm(book)

        btnUpdate.setOnClickListener{
            val updateBook = Books(id = book.id,
                title = editTextTitle.text.toString(),
                author = editTextAuthor.text.toString(),
                publicationDate = editTextPublicationDate.text.toString(),
                binding = editTextBinding.text.toString(),
                pages = editTextPages.text.toString().toInt(),
                urlImage = editTextUrlImage.text.toString()
            )
            bookReference.setValue(updateBook)
            Toast.makeText(applicationContext, "Libro actualizado exitosamente!!", Toast.LENGTH_LONG).show()
        }

        btnView.setOnClickListener{
            val intent = Intent(this, ListBookActivity2::class.java)
            startActivity(intent)
        }

    }

    private fun fillForm(books: Books){
        editTextTitle.setText(books.title)
        editTextAuthor.setText(books.author)
        editTextPublicationDate.setText(books.publicationDate)
        editTextBinding.setText(books.binding)
        editTextPages.setText(books.pages.toString())
        editTextUrlImage.setText((books.urlImage))
    }


}