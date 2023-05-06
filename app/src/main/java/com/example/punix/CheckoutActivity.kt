package com.example.punix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CheckoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.payment_activity)

        val actionBar = supportActionBar
        actionBar!!.title = "Checkout Payment"
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
}