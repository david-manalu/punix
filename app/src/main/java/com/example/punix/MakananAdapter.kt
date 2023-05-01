package com.example.punix

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class MakananAdapter internal constructor(private val context: Context) : BaseAdapter() {
    internal var makanans = arrayListOf<MakananData>()
    override fun getCount(): Int {
        return makanans.size
    }

    override fun getItem(position: Int): Any {
        return makanans[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        var itemView = view
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.browse_activity, viewGroup, false)
        }
        val viewHolder = ViewHolder(itemView as View)
        val makanan = getItem(position) as MakananData
        viewHolder.bind(makanan)
        return itemView
    }
    private inner class ViewHolder(view: View) {
        private val txtName: TextView = view.findViewById(R.id.txt_name)
        private val txtDescription: TextView = view.findViewById(R.id.txt_description)
        private val imgPhoto: ImageView = view.findViewById(R.id.img_photo)
        private val price: TextView = view.findViewById(R.id.price)

        fun bind(makanan: MakananData) {
            txtName.text = makanan.nama
            txtDescription.text = makanan.desc
            imgPhoto.setImageResource(makanan.img)
            price.text = "" + makanan.harga
        }
    }

}