package com.example.punix.Handler

import android.os.StrictMode
import android.util.Log
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Connection


object DatabaseHandler {
    var con: Connection? = null
    private var url = "jdbc:mysql://10.0.2.2/punix"
    private var username = "root"
    private var password = ""

    fun connect(): Connection? {
        Log.d("myTag", "This is my message");
        try {
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            con = DriverManager.getConnection(url, username, password)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return con
    }
}