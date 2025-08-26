package com.okamiko.core.di

import android.content.Context
import androidx.room.Room
import com.okamiko.core.data.local.dao.BasketProductDao
import com.okamiko.core.data.local.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {
    single { provideDatabase(context = androidContext()) }
    single { provideBasketProductDao(get()) }
}


fun provideDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "market_db"
    ).build()
}

fun provideBasketProductDao(database: AppDatabase): BasketProductDao = database.basketProductDao()