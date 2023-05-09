package com.example.punix.View

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.punix.R
import com.google.firebase.auth.FirebaseAuth

class MainAdminActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnBrowseItem: Button
    private lateinit var btnBrowseTransaction: Button
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_admin_activity)
        btnBrowseItem = findViewById(R.id.admin_item_parent_button)
        btnBrowseTransaction = findViewById(R.id.admin_transaction_parent_button)

        btnBrowseItem.setOnClickListener(this)
        btnBrowseTransaction.setOnClickListener(this)
        auth = FirebaseAuth.getInstance()
        supportActionBar?.show()


    }
    override fun onClick(v: View) {
        when(v.id) {
            R.id.admin_item_parent_button -> {
                val intent = Intent(this@MainAdminActivity, AdminItemActivity::class.java)
                startActivity(intent)
            }
            R.id.admin_transaction_parent_button -> {
                val intent = Intent(this@MainAdminActivity, AdminItemActivity::class.java)
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
                val intent = Intent(this@MainAdminActivity, LoginActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}