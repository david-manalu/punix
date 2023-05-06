package com.example.punix

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.punix.Controller.CartController
import com.example.punix.Model.Item
import com.example.punix.databinding.BrowseActivityBinding

class CartAdapter(private val cart: Map<Item, Int>) :
    RecyclerView.Adapter<CartAdapter.ListViewHolder>() {
    class ListViewHolder(private val view: BrowseActivityBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(item: Item, jumlah: Int?) {
            with(view) {
                var temp = 0
                txtName.text = jumlah.toString() + "x " + item.name
                txtDescription.text = item.description
                price.text = item.price.toString()
                Glide.with(itemView.context).load(item.img).into(imgPhoto)

                addToCart.setOnClickListener {
                    var message = ""
                    if (CartController().addToCart(item.id)) {
                        message = "Successfully added to cart"
                    } else {
                        message = "Failed to add to cart"
                    }
                    Toast.makeText(itemView.context, message, Toast.LENGTH_SHORT).show()
                }
                removeFromCart.setOnClickListener {
                    var message = ""
                    if (CartController().removeFromCart(item.id)) {
                        message = "Successfully removed from cart"
                    } else {
                        message = "Failed to remove from cart"
                    }
                    Toast.makeText(itemView.context, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            BrowseActivityBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup, false
            )

        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cart.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val key = cart.keys.toList()[position]
        val value = cart[key]
        holder.bind(key, value)
    }
}