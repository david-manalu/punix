package com.example.punix.Controller

import com.example.punix.DatabaseHandler
import com.example.punix.Model.*
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class ItemController {
    private var con = DatabaseHandler.connect()

    fun getItems(): ArrayList<Item> {
        val items: ArrayList<Item> = ArrayList()
        val query = "SELECT id, name, price, description FROM items"

        val stmt: Statement = con!!.createStatement()
        val rs: ResultSet = stmt.executeQuery(query)

        while (rs.next()) {
            val id = rs.getInt("id")
            val name = rs.getString("name")
            val price = rs.getInt("price")
            val description = rs.getString("description")

            val item = Item(id, name, price, description)
            items.add(item)
        }

        return items
    }

    fun getItemById(id: Int): Item? {
        val query = "SELECT id, name, price, description FROM items WHERE id = ?"
        val pstmt: PreparedStatement = con!!.prepareStatement(query)
        pstmt.setInt(1, id)

        val rs: ResultSet = pstmt.executeQuery()

        if (rs.next()) {
            val name = rs.getString("name")
            val price = rs.getInt("price")
            val description = rs.getString("description")

            return Item(id, name, price, description)
        } else {
            return null
        }
    }

    fun addItem(item: Item): Item {
        val query = "INSERT INTO items (name, price, description) VALUES (?, ?, ?)"

        val pstmt: PreparedStatement = con!!.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        pstmt.setString(1, item.name)
        pstmt.setInt(2, item.price)
        pstmt.setString(3, item.description)

        pstmt.executeUpdate()

        // Retrieve the generated id from the database and update the item object
        val rs = pstmt.generatedKeys
        if (rs.next()) {
            val id = rs.getInt(1)
            item.id = id
        }

        return item
    }

    fun editItemById(id: Int, item: Item): Item {
        val query = "UPDATE items SET name = ?, price = ?, description = ? WHERE id = ?"

        val pstmt: PreparedStatement = con!!.prepareStatement(query)
        pstmt.setString(1, item.name)
        pstmt.setInt(2, item.price)
        pstmt.setString(3, item.description)
        pstmt.setInt(4, id)

        pstmt.executeUpdate()

        // Retrieve the updated item from the database and return it
        val updatedItemQuery = "SELECT id, name, price, description FROM items WHERE id = ?"
        val updatedItemStmt: PreparedStatement = con!!.prepareStatement(updatedItemQuery)
        updatedItemStmt.setInt(1, id)
        val rs: ResultSet = updatedItemStmt.executeQuery()

        if (rs.next()) {
            val name = rs.getString("name")
            val price = rs.getInt("price")
            val description = rs.getString("description")

            return Item(id, name, price, description)
        } else {
            throw SQLException("Item with id $id not found")
        }
    }

    fun deleteItemById(id: Int): Boolean {
        val query = "DELETE FROM items WHERE id = ?"

        val pstmt: PreparedStatement = con!!.prepareStatement(query)
        pstmt.setInt(1, id)

        val rowsAffected = pstmt.executeUpdate()

        return rowsAffected > 0
    }
}