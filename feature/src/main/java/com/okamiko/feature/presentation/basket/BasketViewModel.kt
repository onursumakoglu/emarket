package com.okamiko.feature.presentation.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.okamiko.core.domain.entity.ProductEntity
import com.okamiko.feature.data.remote.dto.ProductDto
import com.okamiko.feature.domain.repository.BasketRepository
import com.okamiko.feature.domain.usecase.AddToBasketUseCase
import com.okamiko.feature.domain.usecase.RemoveFromBasketUseCase
import kotlinx.coroutines.launch

class BasketViewModel(
    basketRepository: BasketRepository,
    private val addToBasketUseCase: AddToBasketUseCase,
    private val removeFromBasketUseCase: RemoveFromBasketUseCase,
) : ViewModel() {

    val basketItems: LiveData<List<ProductEntity>> = basketRepository
        .getBasketItems()
        .asLiveData()

    fun addToBasket(product: ProductDto) {
        viewModelScope.launch {
            addToBasketUseCase.invoke(product)
        }
    }

    fun removeFromBasket(product: ProductDto) {
        viewModelScope.launch {
            removeFromBasketUseCase.invoke(product.id ?: "")
        }
    }
}