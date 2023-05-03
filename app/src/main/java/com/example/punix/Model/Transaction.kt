package com.example.punix.Model

import java.time.LocalDateTime

class Transaction(
    val id: Int,
    val user: User,
    val datetime: LocalDateTime,
    val status: String,
    val method: String,
    val items: Map<Item, Int>
) {
    // Constructor
    constructor(
        id: Int,
        user: User,
        datetime: LocalDateTime,
        status: String,
        method: String,
        detailedTransactions: Map<Item, Int>
    ) : this(
        id,
        user,
        datetime,
        status,
        method,
        detailedTransaction
    )

    // Getters
    fun getId(): Int {
        return id
    }

    fun getUser(): User {
        return user
    }

    fun getDatetime(): LocalDateTime {
        return datetime
    }

    fun getStatus(): String {
        return status
    }

    fun getMethod(): String {
        return method
    }

    fun getItems(): Map<Item, Int> {
        return items
    }

    // Setters
    fun setId(id: Int) {
        this.id = id
    }

    fun setUser(user: User) {
        this.user = user
    }

    fun setDatetime(datetime: LocalDateTime) {
        this.datetime = datetime
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun setMethod(method: String) {
        this.method = method
    }

    fun setItems(items: Map<Item, Int>) {
        this.items = items
    }
}
