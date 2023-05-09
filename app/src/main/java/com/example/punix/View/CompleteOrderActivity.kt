package com.example.punix.View

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.punix.Model.Cart
import com.example.punix.R

class CompleteOrderActivity : AppCompatActivity() {
    private lateinit var totalBayar: TextView
    private lateinit var orderItems: TextView
    private var total: Float = 0F
    private lateinit var cart: Cart
    private lateinit var ok: ImageView

    companion object {
        const val TOTAL = "extra_total"
        const val EXTRA_CART = "extra_cart"
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_complete_activity)
        totalBayar = findViewById(R.id.order_total_text)
        orderItems = findViewById(R.id.items)
        ok = findViewById(R.id.order_complete_ok)
        total = intent.getFloatExtra(TOTAL, 0F)
        cart = intent.getParcelableExtra(EXTRA_CART, Cart::class.java) as Cart
        orderItems.text = summaryBuilder()
        totalBayar.text = "Total = $total"

        ok.setOnClickListener {
            finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun summaryBuilder(): String {
        var summary = ""
        val items = cart.items
        items.forEach {
            summary += it.value.toString() + "x " + it.key.name + ", Rp. " + (it.key.price * it.value) + "\n"
        }
        return summary

    }
}