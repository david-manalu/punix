package com.example.punix.Controller

import com.example.punix.Handler.DatabaseHandler
import com.example.punix.Model.Item
import com.example.punix.Model.Transaction
import com.example.punix.Model.User
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement
import java.sql.Timestamp

class TransactionController {

    private var con = DatabaseHandler.connect()
    fun getTransactionById(id: Int): Transaction {
        val query =
            "SELECT transactions.id AS 'id_transaction', transactions.datetime, transactions.status, transactions.token, items.id AS 'id_item', items.name AS 'name_item', items.price, items.description, detailed_transactions.quantity, users.id AS 'id_user', users.name AS 'name_user', users.email, users.password, users.admin, items.img_url FROM transactions, detailed_transactions, users, items WHERE items.id = detailed_transactions.id_item AND transactions.id = detailed_transactions.id_transaction AND transactions.id_user = users.id AND transactions.id = ?"

        val pstmt: PreparedStatement = con!!.prepareStatement(query)
        pstmt.setInt(1, id)
        val rs: ResultSet = pstmt.executeQuery()

        val items = mutableMapOf<Item, Int>()
        var user: User = User(0, "", "", "", false)
        var transactionId: Int = 0
        var datetime: Timestamp = Timestamp.valueOf("2023-04-27 18:42:30")
        var status: String = ""
        var token: String = ""

        while (rs.next()) {
            val userId = rs.getInt("id_user")
            val userName = rs.getString("name_user")
            val userEmail = rs.getString("email")
            val userPassword = rs.getString("password")
            val userAdmin = rs.getBoolean("admin")
            user = User(userId, userName, userEmail, userPassword, userAdmin)

            val itemId = rs.getInt("id_item")
            val itemName = rs.getString("name_item")
            val itemPrice = rs.getInt("price")
            val itemDescription = rs.getString("description")
            val itemImg = rs.getString("img_url")
            val quantity = rs.getInt("quantity")
            val item = Item(itemId, itemName, itemPrice, itemDescription, itemImg)

            items[item] = quantity

            transactionId = rs.getInt("id_transaction")
            datetime = rs.getTimestamp("datetime")
            status = rs.getString("status")
            token = rs.getString("token")
        }
        return Transaction(transactionId, user, datetime, status, token, items)
    }

    fun getTransactions(): ArrayList<Transaction> {
        val transactions: ArrayList<Transaction> = ArrayList()
        val query = "SELECT id from transactions ORDER BY ID DESC"

        val stmt: Statement = con!!.createStatement()
        val rs: ResultSet = stmt.executeQuery(query)

        while (rs.next()) {
            val id = rs.getInt("id")
            transactions.add(getTransactionById(id))
        }
        return transactions
    }

    fun createTransaction(status: String, token: String, total: Int): Boolean {
        var success: Boolean = true
        val query =
            "INSERT INTO transactions (id_user, datetime, status, token, total) VALUES (?, NOW(), ?, ?, ?)"

        val pstmt: PreparedStatement =
            con!!.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        pstmt.setInt(1, UserController.getCurrentUserID())
        pstmt.setString(2, status)
        pstmt.setString(3, token)
        pstmt.setInt(4, total)

        pstmt.executeUpdate()

        var transaction_id = 0
        val rs = pstmt.generatedKeys
        if (rs.next()) {
            val id = rs.getInt("id")
            transaction_id = id
        } else {
            return false
        }
        val cart = CartController().getCart(UserController.getCurrentUserID())
        cart.items.forEach {
            val query2 =
                "INSERT INTO detailed_transactions (id_transaction,id_item,quantity) VALUES (?, ?, ?)"
            val pstmt2: PreparedStatement = con!!.prepareStatement(query2)
            pstmt2.setInt(1, transaction_id)
            pstmt2.setInt(2, it.key.id)
            pstmt2.setInt(3, it.value)
            val rs2 = pstmt2.executeUpdate()

            if (rs2 == 0) {
                success = false
            }
        }
        return success
    }
}