package com.example.punix.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    var id: Int,
    var name: String,
    var price: Int,
    var description: String,
    var img: String
) : Parcelable