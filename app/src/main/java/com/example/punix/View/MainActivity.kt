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
//            for (user in UserController().getUsers()) {
//                showMessage("user ", user.email + " - " + user.password)
//            }
//            val con = DatabaseHandler.connect()
//            val st: Statement = con!!.createStatement()
//            val rs: ResultSet = st.executeQuery("SELECT * from users")
//            while(rs.next())
//            {
//                showMessage("user ", rs.getString("name"))
//            }


            //UserController().editUserByEmail(User(0, "Dadang Test", "dadang@gmail.com", "123456", true))
            //showMessage("g", UserController().getUserByEmail("dadang@gmail.com")!!.name)

            //CartController().updateItemQuantity(4, 1, 6)
//            CartController().deleteItemFromCart(4, 1)
//            for (kv in TransactionController().getTransactionById(1).items)
//            {
//                showMessage(kv.key.name, kv.value.toString())
//            }
//            for (transaction in TransactionController().getTransactions())
//            {
//                showMessage(transaction.id.toString(), transaction.status + " " + transaction.token)
//            }
           // TransactionController().createTransactions("TEST", "123", 20000)

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
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}