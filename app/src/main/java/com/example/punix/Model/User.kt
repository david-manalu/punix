package com.example.punix.Model

class User(
    var id: Int,
    var name: String,
    var email: String,
    var password: String,
    var admin: Boolean
) {
    // Getters
    fun getId(): Int {
        return id
    }

    fun getName(): String {
        return name
    }

    fun getEmail(): String {
        return email
    }

    fun getPassword(): String {
        return password
    }

    fun isAdmin(): Boolean {
        return admin
    }

    // Setters
    fun setId(id: Int) {
        this.id = id
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun setAdmin(admin: Boolean) {
        this.admin = admin
    }
}
