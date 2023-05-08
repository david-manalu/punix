package com.example.punix.View

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.punix.Adapter.CartAdapter
import com.example.punix.Controller.CartController
import com.example.punix.Controller.UserController
import com.example.punix.Model.Cart
import com.example.punix.Model.Item
import com.example.punix.R
import com.example.punix.databinding.CheckoutActivityBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: CheckoutActivityBinding
    private lateinit var items: Map<Item, Int>
    private var subtotal: Float = 0F
    private var tax: Float = 0F
    private lateinit var textSubtotal: TextView
    private lateinit var textTax: TextView
    private lateinit var textTotal: TextView
    private var total: Float = 0F
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var proceedCheckout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CheckoutActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        textSubtotal = findViewById(R.id.checkout_subtotal_value)
        textTax = findViewById(R.id.checkout_tax_value)
        textTotal = findViewById(R.id.checkout_total_value)
        refreshLayout = findViewById(R.id.refresh_layout)
        proceedCheckout = findViewById(R.id.buttonCheckout)
        prepare()
        binding.cartRecyclerView.setHasFixedSize(true)

        val actionBar: ActionBar? = supportActionBar
        actionBar!!.title = "Cart"
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this)
        val cartAdapter = CartAdapter(items)
        binding.cartRecyclerView.adapter = cartAdapter
        refreshLayout.setOnRefreshListener {
            finish()
            startActivity(this.intent)
            refreshLayout.isRefreshing = false
        }
        proceedCheckout.setOnClickListener {
            val intent = Intent(this@CartActivity, CheckoutActivity::class.java)
            intent.putExtra(CheckoutActivity.EXTRA_TOTAL, total)
            val cart: Cart = CartController().getCart(UserController.getCurrentUserID())
            intent.putExtra(CheckoutActivity.EXTRA_CART, cart)
            startActivity(intent)
        }
    }

    private fun prepare() {
        items = CartController().getCart(UserController.getCurrentUserID()).items
        items.forEach {
            subtotal += it.key.price * it.value
        }
        tax = (subtotal * 0.06).toFloat()
        total = subtotal + tax
        textSubtotal.text = "Rp. $subtotal"
        textTotal.text = "Rp. $total"
        textTax.text = "Rp. $tax"
    }
}