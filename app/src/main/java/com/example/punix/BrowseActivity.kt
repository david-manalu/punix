package com.example.punix

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.punix.Controller.ItemController
import com.example.punix.Model.Item
import com.example.punix.databinding.ActivityBrowseBinding

class BrowseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBrowseBinding
    private var foods = ArrayList<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        binding = ActivityBrowseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepare()
        binding.lvList.setHasFixedSize(true)

        binding.lvList.layoutManager = LinearLayoutManager(this)
        val listMakananAdapter = ListMakananAdapter(foods)
        binding.lvList.adapter = listMakananAdapter
    }

    private fun prepare() {
        foods = ItemController().getItems()
    }
}