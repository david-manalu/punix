package com.example.punix

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var btnLogin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        auth = FirebaseAuth.getInstance()
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        btnLogin = findViewById(R.id.login_button)
        supportActionBar?.hide()

        btnLogin.setOnClickListener {
            signIn(email.text.toString(), password.text.toString())
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }

    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
        // [END sign_in_with_email]
    }

    private fun updateUI(user: FirebaseUser?) {
        user?.let {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun reload() {
        finish()
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }

    companion object {
        private const val TAG = "EmailPassword"
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.sign_up -> {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
                this.finish()
            }
            R.id.forgot_password -> {
                auth.sendPasswordResetEmail(email.text.toString())
                Toast.makeText(baseContext,"Password Reset Email successfully sent",Toast.LENGTH_LONG).show()
            }
        }
    }
}