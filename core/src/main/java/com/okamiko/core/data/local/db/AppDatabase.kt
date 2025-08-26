package com.okamiko.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.okamiko.core.data.local.dao.BasketProductDao
import com.okamiko.core.domain.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun basketProductDao(): BasketProductDao
}