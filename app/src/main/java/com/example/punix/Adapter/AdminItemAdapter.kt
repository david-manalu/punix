package com.example.punix.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.punix.Controller.CartController
import com.example.punix.Model.Item
import com.example.punix.View.AdminItemActivity
import com.example.punix.databinding.AdminItemActivityBinding
import com.example.punix.databinding.BrowseActivityBinding

class AdminItemAdapter(private val listMakanan: ArrayList<Item>) :
    RecyclerView.Adapter<AdminItemAdapter.ListViewHolder>() {
    class ListViewHolder(private val binding: AdminItemActivityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            with(binding) {
                Glide.with(itemView.context).load(item.img).into(imgPhoto)
                txtName.text = item.name
                txtDescription.text = item.description
                price.text = item.price.toString()
                adminItemEdit.setOnClickListener {
//                    var message = ""
//                    if (CartController().addToCart(item.id)) {
//                        message = "Successfully added to cart"
//                    } else {
//                        message = "Failed to add to cart"
//                    }
                    Toast.makeText(itemView.context, "editing item...", Toast.LENGTH_SHORT).show()
                }
//                removeFromCart.setOnClickListener {
//                    var message = ""
//                    if (CartController().removeFromCart(item.id)) {
//                        message = "Successfully removed from cart"
//                    } else {
//                        message = "Failed to remove from cart"
//                    }
//                    Toast.makeText(itemView.context, message, Toast.LENGTH_SHORT).show()
//                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding =
            AdminItemActivityBinding.inflate(
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