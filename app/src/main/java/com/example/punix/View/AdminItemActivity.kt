package com.example.punix.View

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.punix.Adapter.AdminItemAdapter
import com.example.punix.Controller.ItemController
import com.example.punix.Adapter.ListMakananAdapter
import com.example.punix.Controller.CartController
import com.example.punix.Controller.UserController
import com.example.punix.Model.Cart
import com.example.punix.Model.Item
import com.example.punix.R
import com.example.punix.databinding.ActivityAdminItemBinding
import com.example.punix.databinding.ActivityBrowseBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AdminItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminItemBinding
    private var foods = ArrayList<Item>()
    private lateinit var fab: FloatingActionButton

    companion object {
        const val EXTRA_ITEM_ID = "extra_item_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        binding = ActivityAdminItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fab = findViewById(R.id.cart)
        fab.setOnClickListener {
            // nambah item
        }
        prepare()
        binding.lvList.setHasFixedSize(true)

        binding.lvList.layoutManager = LinearLayoutManager(this)
        val listMakananAdapter = AdminItemAdapter(foods, this@AdminItemActivity)
        binding.lvList.adapter = listMakananAdapter
    }

    private fun prepare() {
        foods = ItemController().getItems()
    }
}