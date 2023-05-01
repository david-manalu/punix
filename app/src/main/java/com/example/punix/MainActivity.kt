package com.example.punix

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnBrowse: Button
    private lateinit var auth: FirebaseAuth

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
        auth = FirebaseAuth.getInstance()
        supportActionBar?.show()

        try
        {
            val con = DatabaseHandler.connect()
            val st: Statement = con!!.createStatement()
            val rs: ResultSet = st.executeQuery("SELECT * from users")
            while(rs.next())
            {
                showMessage("user ", rs.getString("name"))
            }
        }
        catch (e: SQLException)
        {
            e.printStackTrace()
        }
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
                val intent = Intent(this@MainActivity,LoginActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}