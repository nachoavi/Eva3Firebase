package com.example.eva3firebase.registerUser

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eva3firebase.MainActivity
import com.example.eva3firebase.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterUserActivity : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)


        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextEmail = findViewById(R.id.editTextEmail)
        btnRegister = findViewById(R.id.btnRegister)


        btnRegister.setOnClickListener{
            insertData()
            Toast.makeText(applicationContext, "Registrado exitosamente!!", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun insertData() {
        val username = editTextUsername.text.toString()
        val password = editTextPassword.text.toString()
        val email = editTextEmail.text.toString()
        val userType = 0


        val usersId = databaseReference.child("users").push().key
        val users = Users(usersId,userType,username,password,email)

        databaseReference.child("users").child(usersId!!).setValue(users)
    }


}