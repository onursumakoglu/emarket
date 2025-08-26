package com.okamiko.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.okamiko.core.domain.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BasketProductDao {
    @Query("SELECT * FROM basket_items")
    fun getBasketProducts(): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBasketProduct(basketProduct: ProductEntity)

    @Update
    suspend fun updateBasketProduct(basketProduct: ProductEntity)

    @Delete
    suspend fun deleteBasketProduct(basketProduct: ProductEntity)

    @Query("SELECT * FROM basket_items WHERE id = :id LIMIT 1")
    suspend fun getBasketItem(id: String): ProductEntity?
}
