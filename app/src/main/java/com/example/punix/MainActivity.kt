package com.example.punix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import android.app.AlertDialog
import android.content.Context

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnBrowse: Button

    fun getHTTP(url: String): String {
        var responseString = ""
        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            connection.connect()

            val inputStream = connection.inputStream
            val reader = BufferedReader(InputStreamReader(inputStream))
            var line: String?
            val response = StringBuffer()

            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }

            reader.close()
            connection.disconnect()
            responseString = response.toString()
        } catch (e: Exception) {
            e.message?.let { showMessage("error", it) };
        }
        return responseString
    }

    fun showMessage(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, which ->
            // do something when OK button is clicked
        }
        builder.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        btnBrowse = findViewById(R.id.browse_button)

        btnBrowse.setOnClickListener(this)

        showMessage("test", getHTTP("https://google.com/"));
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