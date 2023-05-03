package com.example.punix.Controller

import com.example.punix.DatabaseHandler
import com.example.punix.Model.*
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class UserController {
    private var con = DatabaseHandler.connect()

    fun getUsers(): ArrayList<User> {
        val users: ArrayList<User> = ArrayList()
        val query = "SELECT id, name, email, password, admin FROM users"

        val stmt: Statement = con!!.createStatement()
        val rs: ResultSet = stmt.executeQuery(query)

        while (rs.next()) {
            val id = rs.getInt("id")
            val name = rs.getString("name")
            val email = rs.getString("email")
            val password = rs.getString("password")
            val admin = rs.getBoolean("admin")

            val user = User(id, name, email, password, admin)
            users.add(user)
        }

        return users
    }

    fun addUser(user: User): User {
        val query = "INSERT INTO users (name, email, password, admin) VALUES (?, ?, ?, ?)"

        val pstmt: PreparedStatement = con!!.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        pstmt.setString(1, user.name)
        pstmt.setString(2, user.email)
        pstmt.setString(3, user.password)
        pstmt.setBoolean(4, user.admin)

        pstmt.executeUpdate()

        // Retrieve the generated id from the database and update the user object
        val rs = pstmt.generatedKeys
        if (rs.next()) {
            val id = rs.getInt(1)
            user.id = id
        }

        return user
    }

    fun getUserByEmail(email: String): User? {
        for (user in getUsers()) {
            if (user.email.equals(email)) {
                return user
            }
        }
        return null
    }

    fun getUserById(id: Int): User? {
        for (user in getUsers()) {
            if (user.id == id) {
                return user
            }
        }
        return null
    }

    fun editUserByEmail(user: User): User {
        val query = "UPDATE users SET name = ?, password = ?, admin = ? WHERE email = ?"

        val pstmt: PreparedStatement = con!!.prepareStatement(query)
        pstmt.setString(1, user.name)
        pstmt.setString(2, user.password)
        pstmt.setBoolean(3, user.admin)
        pstmt.setString(4, user.email)

        pstmt.executeUpdate()

        // Retrieve the updated user from the database and return it
        val updatedUserQuery = "SELECT id, name, email, password, admin FROM users WHERE email = ?"
        val updatedUserStmt: PreparedStatement = con!!.prepareStatement(updatedUserQuery)
        updatedUserStmt.setString(1, user.email)
        val rs: ResultSet = updatedUserStmt.executeQuery()

        if (rs.next()) {
            val id = rs.getInt("id")
            val name = rs.getString("name")
            val password = rs.getString("password")
            val admin = rs.getBoolean("admin")

            return User(id, name, user.email, password, admin)
        } else {
            throw SQLException("User with email $user.email not found")
        }
    }

    fun editUserById(id: Int, user: User): User {
        val query = "UPDATE users SET name = ?, email = ?, password = ?, admin = ? WHERE id = ?"

        val pstmt: PreparedStatement = con!!.prepareStatement(query)
        pstmt.setString(1, user.name)
        pstmt.setString(2, user.email)
        pstmt.setString(3, user.password)
        pstmt.setBoolean(4, user.admin)
        pstmt.setInt(5, id)

        pstmt.executeUpdate()

        // Retrieve the updated user from the database and return it
        val updatedUserQuery = "SELECT id, name, email, password, admin FROM users WHERE id = ?"
        val updatedUserStmt: PreparedStatement = con!!.prepareStatement(updatedUserQuery)
        updatedUserStmt.setInt(1, id)
        val rs: ResultSet = updatedUserStmt.executeQuery()

        if (rs.next()) {
            val name = rs.getString("name")
            val email = rs.getString("email")
            val password = rs.getString("password")
            val admin = rs.getBoolean("admin")

            return User(id, name, email, password, admin)
        } else {
            throw SQLException("User with id $id not found")
        }
    }

    fun deleteUserByEmail(email: String): Boolean {
        val query = "DELETE FROM users WHERE email = ?"
        val pstmt: PreparedStatement = con!!.prepareStatement(query)
        pstmt.setString(1, email)

        val deletedRows = pstmt.executeUpdate()
        return deletedRows > 0
    }

    fun deleteUserById(id: Int): Boolean {
        val query = "DELETE FROM users WHERE id = ?"
        val pstmt: PreparedStatement = con!!.prepareStatement(query)
        pstmt.setInt(1, id)

        val deletedRows = pstmt.executeUpdate()
        return deletedRows > 0
    }
}