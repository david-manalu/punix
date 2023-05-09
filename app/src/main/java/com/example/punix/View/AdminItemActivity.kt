package com.example.punix.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.punix.Adapter.AdminItemAdapter
import com.example.punix.Controller.ItemController
import com.example.punix.Adapter.ListMakananAdapter
import com.example.punix.Model.Item
import com.example.punix.R
import com.example.punix.databinding.ActivityAdminItemBinding
import com.example.punix.databinding.ActivityBrowseBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AdminItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminItemBinding
    private var foods = ArrayList<Item>()
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        binding = ActivityAdminItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fab = findViewById(R.id.cart)
        fab.setOnClickListener {
//            val intent = Intent(this@AdminItemActivity, CartActivity::class.java)
//            startActivity(intent)
        }
        prepare()
        binding.lvList.setHasFixedSize(true)

        binding.lvList.layoutManager = LinearLayoutManager(this)
        val listMakananAdapter = AdminItemAdapter(foods)
        binding.lvList.adapter = listMakananAdapter
    }

    private fun prepare() {
        foods = ItemController().getItems()
    }
}