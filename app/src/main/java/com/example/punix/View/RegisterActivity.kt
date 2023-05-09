package com.example.punix.View

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.punix.Controller.UserController
import com.example.punix.Model.User
import com.example.punix.R
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    private lateinit var etUsername: EditText
    private lateinit var btnSignUp: Button
    lateinit var tvRedirectLogin: TextView
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        btnSignUp = findViewById(R.id.register_button)
        tvRedirectLogin = findViewById(R.id.login)
        etEmail = findViewById(R.id.email)
        etPass = findViewById(R.id.password)
        etUsername = findViewById(R.id.username)
        
        btnSignUp.setOnClickListener {
            signUpUser()
        }

        tvRedirectLogin.setOnClickListener() {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signUpUser() {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()
        val username = etUsername.text.toString()

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Email or Password cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                var user = User(0, username, email, pass, false)
                UserController().addUser(user)

                Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
                auth.signOut()
                finish()
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
