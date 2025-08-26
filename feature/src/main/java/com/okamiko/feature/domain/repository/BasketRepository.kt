package com.okamiko.feature.domain.repository

import com.okamiko.core.data.local.dao.BasketProductDao
import com.okamiko.core.domain.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

class BasketRepository(
    private val dao: BasketProductDao
) {
    fun getBasketItems(): Flow<List<ProductEntity>> = dao.getBasketProducts()

    suspend fun addToBasket(item: ProductEntity) = dao.insertBasketProduct(item)

    suspend fun updateBasket(item: ProductEntity) = dao.updateBasketProduct(item)

    suspend fun removeFromBasket(item: ProductEntity) = dao.deleteBasketProduct(item)

    suspend fun getBasketItem(productId: String) = dao.getBasketItem(productId)
}