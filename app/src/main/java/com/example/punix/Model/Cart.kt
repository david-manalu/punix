package com.example.punix.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cart(
    var items: Map<Item, Int>
) : Parcelable