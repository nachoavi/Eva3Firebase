package com.example.eva3firebase.genresCrud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eva3firebase.R
import com.example.eva3firebase.booksCrud.AddBookActivity
import com.example.eva3firebase.booksCrud.ListBookActivity2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddGenresActivity : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    private  lateinit var editTextGenre : EditText
    private  lateinit var btnInsert: Button
    private lateinit var btnGenres: Button

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
        setContentView(R.layout.activity_add_genres)

        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference


        editTextGenre = findViewById(R.id.editTextGenre)
        btnInsert = findViewById(R.id.buttonAddGenre)
        btnGenres = findViewById(R.id.buttonViewGenres)

        btnInsert.setOnClickListener{
            insertData()
            Toast.makeText(applicationContext, "Genero ingresado correctamente!!", Toast.LENGTH_LONG).show()

        }

        btnGenres.setOnClickListener{
            val intent = Intent(this, ListGenresActivity::class.java)
            startActivity(intent)
        }


        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)


    }

    private fun insertData() {
        val genre = editTextGenre.text.toString()


        val genreId = databaseReference.child("genres").push().key
        val genres = Genres(genreId,genre)

        databaseReference.child("genres").child(genreId!!).setValue(genres)

    }

}