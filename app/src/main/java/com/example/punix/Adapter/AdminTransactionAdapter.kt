package com.example.punix.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.punix.databinding.AdminItemActivityBinding
import com.example.punix.databinding.AdminTransactionActivityBinding
import com.example.punix.Model.Transaction

class AdminTransactionAdapter(private val listMakanan: ArrayList<Transaction>) :
    RecyclerView.Adapter<AdminTransactionAdapter.ListViewHolder>() {
    class ListViewHolder(private val binding: AdminTransactionActivityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(transaction: Transaction) {
            with(binding) {
                //Glide.with(itemView.context).load(item.img).into(imgPhoto)
                txtName.text = transaction.token
                txtDescription.text = transaction.status + " | " + (transaction.user?.email ?: "")

                var total = 0
                for (kv in transaction.items)
                {
                    total += kv.key.price * kv.value
                }

                price.text = total.toString()
//                adminItemEdit.setOnClickListener {
////                    var message = ""
////                    if (CartController().addToCart(item.id)) {
////                        message = "Successfully added to cart"
////                    } else {
////                        message = "Failed to add to cart"
////                    }
//                    Toast.makeText(itemView.context, "editing item...", Toast.LENGTH_SHORT).show()
//                }
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
            AdminTransactionActivityBinding.inflate(
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