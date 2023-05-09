package com.example.punix.View

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.punix.Controller.TransactionController
import com.example.punix.R
import com.google.firebase.auth.FirebaseAuth
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.sql.SQLException

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnBrowse: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        btnBrowse = findViewById(R.id.browse_button)

        btnBrowse.setOnClickListener(this)
        auth = FirebaseAuth.getInstance()
        supportActionBar?.show()
    }
    override fun onClick(v: View) {
        when(v.id) {
            R.id.browse_button -> {
                val intent = Intent(this@MainActivity, BrowseActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                auth.signOut()
                finish()
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
