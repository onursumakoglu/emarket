package com.okamiko.feature.di

import com.okamiko.feature.data.remote.api.MarketService
import com.okamiko.feature.data.remote.repository.ProductListRepositoryImpl
import com.okamiko.feature.domain.repository.BasketRepository
import com.okamiko.feature.domain.repository.ProductListRepository
import com.okamiko.feature.domain.usecase.AddToBasketUseCase
import com.okamiko.feature.domain.usecase.RemoveFromBasketUseCase
import com.okamiko.feature.presentation.basket.BasketViewModel
import com.okamiko.feature.presentation.productList.ProductListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val featureModule = module {
    single { get<Retrofit>().create(MarketService::class.java) }
    single<ProductListRepository> { ProductListRepositoryImpl(marketService = get()) }
    single { BasketRepository(get()) }
    single { AddToBasketUseCase(get()) }
    single { RemoveFromBasketUseCase(get()) }

    viewModel { ProductListViewModel(get()) }
    viewModel { BasketViewModel(get(), get(), get()) }
}