package com.example.punix.Model

data class Cart(
    var userId: Int,
    var items: Map<Item, Int>
) {
    // Getters
    fun getUserId(): Int {
        return userId
    }

    fun getItems(): Map<Item, Int> {
        return items
    }

    // Setters
    fun setUserId(userId: Int) {
        this.userId = userId
    }

    fun setItems(items: Map<Item, Int>) {
        this.items = items
    }
}