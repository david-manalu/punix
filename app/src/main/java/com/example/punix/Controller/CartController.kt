package com.example.punix.Controller

import com.example.punix.Handler.DatabaseHandler
import com.example.punix.Model.Cart
import com.example.punix.Model.Item
import java.sql.PreparedStatement
import java.sql.ResultSet

class CartController {
    private var con = DatabaseHandler.connect()

    fun getCart(userId: Int): Cart {
        val query =
            "SELECT items.id, items.name, items.price, items.description, items.img_url, carts.quantity FROM carts, items WHERE items.id = carts.id_item and carts.id_user = ?"
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
            val img_url = rs.getString("img_url")
            val item = Item(itemId, itemName, itemPrice, itemDescription, img_url)
            items[item] = quantity
        }

        return Cart(items)
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

    fun addToCart(itemId: Int): Boolean {
        val query =
            "INSERT INTO carts (id_user,id_item,quantity) VALUES (?,?,?) ON DUPLICATE KEY UPDATE quantity = quantity + 1"
        val pstmt: PreparedStatement = con!!.prepareStatement(query)
        pstmt.setInt(1, UserController.getCurrentUserID())
        pstmt.setInt(2, itemId)
        pstmt.setInt(3, 1)

        val exec = pstmt.executeUpdate()
        return exec > 0
    }

    fun removeFromCart(itemId: Int): Boolean {
        val stmt =
            con!!.prepareStatement("SELECT quantity FROM carts WHERE id_user = ? AND id_item = ?")
        stmt.setInt(1, UserController.getCurrentUserID())
        stmt.setInt(2, itemId)

        val result = stmt.executeQuery()

        if (result.next()) {
            val quantity = result.getInt("quantity")
            if (quantity == 1) {
                return deleteItemFromCart(UserController.getCurrentUserID(), itemId)
            }
        }


        val query = "UPDATE carts SET quantity = quantity - 1 WHERE id_user = ? AND id_item = ?"
        val pstmt: PreparedStatement = con!!.prepareStatement(query)
        pstmt.setInt(1, UserController.getCurrentUserID())
        pstmt.setInt(2, itemId)
        val numRowsUpdated = pstmt.executeUpdate()
        return numRowsUpdated > 0
    }

    fun emptyCart() : Boolean {
        val stmt = con!!.prepareStatement("DELETE FROM carts WHERE id_user = ?")
        stmt.setInt(1, UserController.getCurrentUserID())

        val result = stmt.executeUpdate()

        return result > 0
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