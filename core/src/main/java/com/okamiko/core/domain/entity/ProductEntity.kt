package com.okamiko.core.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "basket_items")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Int? = null,
    val id: String?,
    val name: String?,
    val description: String?,
    val image: String?,
    val price: String?,
    val model: String?,
    val brand: String?,
    val createdAt: String?,
    var isFavorite: Boolean?,
    var quantity: Int
)