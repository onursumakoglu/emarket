package com.okamiko.feature.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDto(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val image: String? = null,
    val price: String? = null,
    val model: String? = null,
    val brand: String? = null,
    val createdAt: String? = null,
    var isFavorite: Boolean? = false,
    var quantity: Int = 0
) : Parcelable