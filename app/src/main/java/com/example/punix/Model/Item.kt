package com.example.punix.Model

import java.net.URL

data class Item(
    var id: Int,
    var name: String,
    var price: Int,
    var description: String,
    var img: URL
)