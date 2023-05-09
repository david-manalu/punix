package com.example.punix.View

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.punix.Controller.ItemController
import com.example.punix.Model.Item
import com.example.punix.R
import com.google.firebase.auth.FirebaseAuth

class AdminEditItemActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var txtName: EditText
    private lateinit var txtPrice: EditText
    private lateinit var txtDescription: EditText
    private lateinit var txtURL: EditText
    private lateinit var btnSave: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_edit_item)

        btnSave = findViewById(R.id.admin_edit_save_button)
        txtName = findViewById(R.id.admin_edit_item_name)
        txtPrice = findViewById(R.id.admin_edit_item_price)
        txtDescription = findViewById(R.id.admin_edit_item_description)
        txtURL = findViewById(R.id.admin_edit_item_img_url)

        supportActionBar?.show()

        val idItem = intent.getIntExtra(AdminItemActivity.EXTRA_ITEM_ID, -1)
        val item = ItemController().getItemById(idItem)

        if (item != null) {
            txtName.setText(item.name)
            txtPrice.setText(item.price.toString())
            txtDescription.setText(item.description)
            txtURL.setText(item.img)
        }

        btnSave.setOnClickListener{
            ItemController().editItemById(idItem, Item(0, txtName.getText().toString(), txtPrice.getText().toString().toInt(), txtDescription.getText().toString(), txtURL.getText().toString()))
            finish()
            startActivity(Intent(this@AdminEditItemActivity, AdminItemActivity::class.java))
        }

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }


}