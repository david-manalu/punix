package com.example.punix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnBrowse: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        btnBrowse = findViewById(R.id.browse_button)

        btnBrowse.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        when(v.id) {
            R.id.browse_button -> {
                val intent = Intent(this@MainActivity, BrowseActivity::class.java)
                startActivity(intent)
            }
        }
    }
}