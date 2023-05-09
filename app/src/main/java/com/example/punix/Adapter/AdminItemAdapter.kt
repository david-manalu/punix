package com.example.punix.Adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.punix.Controller.CartController
import com.example.punix.Controller.UserController
import com.example.punix.Model.Cart
import com.example.punix.Model.Item
import com.example.punix.View.AdminEditItemActivity
import com.example.punix.View.AdminItemActivity
import com.example.punix.View.CheckoutActivity
import com.example.punix.databinding.AdminItemActivityBinding
import com.example.punix.databinding.BrowseActivityBinding

class AdminItemAdapter(private val listMakanan: ArrayList<Item>, var act: AdminItemActivity) :
    RecyclerView.Adapter<AdminItemAdapter.ListViewHolder>() {
    class ListViewHolder(private val binding: AdminItemActivityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item, activity: AdminItemActivity) {
            with(binding) {
                Glide.with(itemView.context).load(item.img).into(imgPhoto)
                txtName.text = item.name
                txtDescription.text = item.description
                price.text = item.price.toString()
                adminItemEdit.setOnClickListener {

                    val intent = Intent(activity, AdminEditItemActivity::class.java)
                    intent.putExtra(AdminItemActivity.EXTRA_ITEM_ID, item.id)
                    activity.startActivity(intent)
                }
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
        holder.bind(listMakanan[position], act)
    }
}