package com.example.punix.Model

data class Cart(
    var user: User?,
    var items: Map<Item, Int>
)