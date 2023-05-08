package com.example.punix.View

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.punix.Controller.CartController
import com.example.punix.Controller.TransactionController
import com.example.punix.Model.Cart
import com.example.punix.R

class CheckoutActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TOTAL = "extra_total"
        const val EXTRA_CART: String = "extra_cart"
    }

    private lateinit var radioGroup: RadioGroup
    private lateinit var pay: Button
    private lateinit var method: String
    private var total: Float = 0.0f
    private lateinit var cart: Cart
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.payment_activity)
        radioGroup = findViewById(R.id.payment_methods)
        pay = findViewById(R.id.select_payment_button)
        val ovo: RadioButton = findViewById(R.id.ovo)
        val shopeepay: RadioButton = findViewById(R.id.shopeepay)
        total = intent.getFloatExtra(EXTRA_TOTAL, 0F)
        cart = intent.getParcelableExtra(EXTRA_CART,Cart::class.java) as Cart

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            method = if (radio.id == R.id.shopeepay) {
                "ShopeePay"
            } else if (radio.id == R.id.ovo) {
                "OVO"
            } else {
                "GoPay"
            }
        }

        val actionBar = supportActionBar
        actionBar!!.title = "Checkout Payment"
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        pay.setOnClickListener {
            val addTransaction = TransactionController().createTransactions(method, total)
            if (!addTransaction) {
                Toast.makeText(
                    baseContext,
                    "Failed to process order please try again",
                    Toast.LENGTH_LONG
                ).show()
            }
            CartController().emptyCart()
            val intent = Intent(this@CheckoutActivity, CompleteOrderActivity::class.java)
            intent.putExtra(CompleteOrderActivity.TOTAL, total)
            intent.putExtra(CompleteOrderActivity.EXTRA_CART, cart)
            finish()
            startActivity(intent)
        }
    }
}