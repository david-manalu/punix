package com.example.punix

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.punix.Model.Item
import com.example.punix.databinding.BrowseActivityBinding

class ListMakananAdapter(private val listMakanan: ArrayList<Item>) :
    RecyclerView.Adapter<ListMakananAdapter.ListViewHolder>() {
    class ListViewHolder(private val binding: BrowseActivityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            with(binding) {
                Glide.with(itemView.context).load(item.img).into(imgPhoto)
                txtName.text = item.name
                txtDescription.text = item.description
                price.text = item.price.toString()
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding =
            BrowseActivityBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup, false
            )
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listMakanan.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listMakanan[position])
    }
}