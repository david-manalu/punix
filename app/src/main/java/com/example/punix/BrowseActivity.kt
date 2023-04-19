package com.example.punix

import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBar

class BrowseActivity : AppCompatActivity() {
    private lateinit var adapter: MakananAdapter
    private lateinit var dataName: Array<String>
    private lateinit var dataDescription: Array<String>
    private lateinit var dataPrice: TypedArray
    private lateinit var dataPhoto: TypedArray
    private var heroes = arrayListOf<MakananData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        val listView : ListView = findViewById(R.id.lv_list)
        adapter = MakananAdapter(this)
        listView.adapter = adapter

        prepare()
        addItem()

        listView.onItemClickListener = AdapterView.OnItemClickListener{ _, _, position, _ ->
            Toast.makeText(this@BrowseActivity, heroes[position].name, Toast.LENGTH_SHORT).show()
        }
    }

    private fun addItem() {
        for (position in dataName.indices) {
            val hero = MakananData(
                dataPhoto.getResourceId(position, -1),
                dataName[position],
                dataDescription[position],
                dataPrice.getInt(position, -1)
            )
            heroes.add(hero)
        }
        adapter.makanans = heroes
    }

    private fun prepare() {
        dataName = resources.getStringArray(R.array.makanan_name)
        dataDescription = resources.getStringArray(R.array.makanan_desc)
        dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        dataPrice = resources.obtainTypedArray(R.array.price_makanan)
    }
}