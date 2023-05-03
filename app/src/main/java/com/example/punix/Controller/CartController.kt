package com.example.punix.Controller

import com.example.punix.DatabaseHandler
import com.example.punix.Model.*
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class CartController {
    private var con = DatabaseHandler.connect()

    fun getCart(userId: Int): Cart {
        val query = "SELECT items.id, items.name, items.price, items.description, carts.quantity FROM carts, items WHERE items.id = carts.id_item and carts.id_user = ?"
        val pstmt: PreparedStatement = con!!.prepareStatement(query)
        pstmt.setInt(1, userId)
        val rs: ResultSet = pstmt.executeQuery()

        val items = mutableMapOf<Item, Int>()
        while (rs.next()) {
            val itemId = rs.getInt("id")
            val itemName = rs.getString("name")
            val itemPrice = rs.getInt("price")
            val itemDescription = rs.getString("description")
            val quantity = rs.getInt("quantity")
            val item = Item(itemId, itemName, itemPrice, itemDescription)
            items[item] = quantity
        }

        return Cart(UserController().getUserById(userId), items)
    }

    fun updateItemQuantity(userId: Int, itemId: Int, newQuantity: Int): Boolean {
        val query = "UPDATE carts SET quantity = ? WHERE id_user = ? AND id_item = ?"
        val pstmt: PreparedStatement = con!!.prepareStatement(query)
        pstmt.setInt(1, newQuantity)
        pstmt.setInt(2, userId)
        pstmt.setInt(3, itemId)
        val numRowsUpdated = pstmt.executeUpdate()
        return numRowsUpdated > 0
    }

    fun deleteItemFromCart(userId: Int, itemId: Int): Boolean {
        val query = "DELETE FROM carts WHERE id_user = ? AND id_item = ?"
        val pstmt: PreparedStatement = con!!.prepareStatement(query)
        pstmt.setInt(1, userId)
        pstmt.setInt(2, itemId)

        val deletedRows = pstmt.executeUpdate()

        return deletedRows > 0
    }

//    fun checkoutCart(userId: Int)
//    {
//        val cart: Cart = getCart(userId)
//        for (kv in cart.items)
//        {
//            deleteItemFromCart(userId, kv.key.id)
//            val query = "INSERT INTO detailed_transactions (id_transaction, id_item, quantity) VALUES (?, ?, ?);"
//        }
//        val user: User? = UserController().getUserById(userId)
//
//    }
}