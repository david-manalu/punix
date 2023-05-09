package com.example.punix.View

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.example.punix.Controller.CartController
import com.example.punix.Controller.TransactionController
import com.example.punix.Model.Cart
import com.example.punix.R
import com.loopj.android.http.*
import cz.msebera.android.httpclient.HttpEntity
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.Base64


class CheckoutActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TOTAL = "extra_total"
        const val EXTRA_CART: String = "extra_cart"
    }

    fun encodeStringToBase64(): String {
        val input = "AQRb3yWBdDazr7VitEXM2-m7mXMAHwR2jGpEM4Zx5Lkdl76UUFqf-cIPYyEoy_-3_ijx3wYim0Q4bvU6:EJ05WQPb7mOS5UPf2XayPT4udZNJV_KHEEYbml5h33Y4TxWXziBuWNw8FcgpmYe1zTkaPtbKPKDcUyiw"
        return Base64.getEncoder().encodeToString(input.toByteArray())
    }

     var accessToken: String = "A21AAJPDe-Ath7bQw6PUhRaKn3Ur3AK8qmcs5JBES_hWIoBuq7fsKHTJ4oV9kjiYETHhjcCbaikgyhT2aEdUeiJLRe2ONEy9A"

    fun getAccessToken() {
        val AUTH = encodeStringToBase64()
        val client = AsyncHttpClient()
        client.addHeader("Accept", "application/json")
        client.addHeader("Content-type", "application/x-www-form-urlencoded")
        client.addHeader("Authorization", "Basic $AUTH")
        val jsonString = "grant_type=client_credentials"
        val entity: HttpEntity = StringEntity(jsonString, "utf-8")
        client.post(
            this,
            "https://api-m.sandbox.paypal.com/v1/oauth2/token",
            entity,
            "application/x-www-form-urlencoded",
            object : TextHttpResponseHandler() {


                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out cz.msebera.android.httpclient.Header>?,
                    responseString: String?
                ) {
                    Log.d("","SUCCESS TOKEN")
                    try {
                        val jobj = responseString?.let { JSONObject(it) }
                        if (jobj != null) {
                            accessToken = jobj.getString("access_token")
                            Log.d("", accessToken)
                        }
                        else {
                            Log.d("", "ERROR GETTING TOKEN 1")
                        }
                    } catch (e: JSONException) {
                        Log.d("", "ERROR GETTING TOKEN 2")
                        e.printStackTrace()
                    }
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out cz.msebera.android.httpclient.Header>?,
                    responseString: String?,
                    throwable: Throwable?
                ) {

                    Log.d("","FAILED TOKEN")
                    Log.d("", "ERROR GETTING TOKEN")
                    Log.d("", statusCode.toString())
                }
            })
    }

    private fun createOrder() {
        getAccessToken()
        Log.d("","CREATING ORDER")
        val url = "https://api.sandbox.paypal.com"
        val client = AsyncHttpClient()
        client.addHeader("Accept", "application/json")
        client.addHeader("Content-type", "application/json")
        client.addHeader("Authorization", "Bearer $accessToken")
        val total2 = Math.floor(total * 100 / 14709.65) / 100

        val order = """{"intent": "CAPTURE","purchase_units": [
      {
        "amount": {
          "currency_code": "USD",
          "value": "$total2"
        }
      }
    ],"application_context": {
        "brand_name": "Punix Restaurant",
        "return_url": "http://10.0.2.2/transaction.php/",
        "cancel_url": "http://10.0.2.2/transaction.php/"
    }}"""

        val entity: HttpEntity = StringEntity(order, "utf-8")
        client.post(
            this,
            "$url/v2/checkout/orders",
            entity,
            "application/json",
            object : TextHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out cz.msebera.android.httpclient.Header>?,
                    responseString: String?
                ) {
                    try {
                        if (responseString != null) {
                            Log.d("", responseString)
                        }
                        val r = responseString?.let { JSONObject(it) }
                        val links: JSONArray = r!!.getJSONArray("links")
                        TransactionController().createTransactions(r.getString("status"), r.getString("id"), total.toInt())
                        Log.d("id", JSONObject(responseString).getString("id"))
                        //iterate the array to get the approval link
                        for (i in 0 until links.length()) {
                            val rel = links.getJSONObject(i).getString("rel")
                            if (rel == "approve") {
                                val link: String = links.getJSONObject(i).getString("href")
                                //redirect to this link via CCT
                                val builder = CustomTabsIntent.Builder()
                                val customTabsIntent = builder.build()
                                customTabsIntent.launchUrl(this@CheckoutActivity, Uri.parse(link))
                            }
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out cz.msebera.android.httpclient.Header>?,
                    response: String?,
                    throwable: Throwable?
                ) {
                    Log.e("RESPONSE", response!!)
                }


            })
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

            createOrder()
//            val addTransaction = TransactionController().createTransactions(method, total)
//            if (!addTransaction) {
//                Toast.makeText(
//                    baseContext,
//                    "Failed to process order please try again",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
            CartController().emptyCart()
            val intent = Intent(this@CheckoutActivity, CompleteOrderActivity::class.java)
            intent.putExtra(CompleteOrderActivity.TOTAL, total)
            intent.putExtra(CompleteOrderActivity.EXTRA_CART, cart)
            finish()
            startActivity(intent)
        }
    }
}