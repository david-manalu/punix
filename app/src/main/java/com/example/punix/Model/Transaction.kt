package com.example.punix.Model

import java.sql.Timestamp

class Transaction(
    var id: Int,
    var user: User?,
    var datetime: Timestamp,
    var status: String,
    var token: String,
    var items: Map<Item, Int>
)