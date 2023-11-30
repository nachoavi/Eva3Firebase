package com.example.eva3firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eva3firebase.booksCrud.AddBookActivity
import com.example.eva3firebase.booksCrud.ListBookActivity2
import com.example.eva3firebase.registerUser.RegisterUserActivity
import com.example.eva3firebase.registerUser.Users
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        btnLogin = findViewById(R.id.buttonLogin)
        btnRegister = findViewById(R.id.buttonRegister)

        btnRegister.setOnClickListener{
            val intent = Intent(this,RegisterUserActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener{
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()
            val intentAdmin = Intent(this, AddBookActivity::class.java)
            val intentNormal = Intent(this,ListBookActivity2::class.java)
            loginUser(username,password,intentAdmin,intentNormal)
        }

    }

    private fun loginUser(username:String,password:String,intentAdmin: Intent,intentNormal: Intent) {
        val database = FirebaseDatabase.getInstance()
        val userRef = database.getReference("users")

        val query: Query = userRef.orderByChild("username").equalTo(username)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (userSnapshot in dataSnapshot.children) {
                        val user = userSnapshot.getValue(Users::class.java)

                        if (user?.password == password && user?.userType == 1) {
                            startActivity(intentAdmin)
                            Toast.makeText(applicationContext, "Bienvenido administrador!!", Toast.LENGTH_LONG).show()
                        }else if(user?.password == password && user?.userType == 0){
                            startActivity(intentNormal)
                            Toast.makeText(applicationContext, "Bienvenido!!", Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(applicationContext, "Contraseña incorrecta!", Toast.LENGTH_LONG).show()
                        }

                    }
                }else{
                    Toast.makeText(applicationContext, "El usuario no esta registrado!", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}