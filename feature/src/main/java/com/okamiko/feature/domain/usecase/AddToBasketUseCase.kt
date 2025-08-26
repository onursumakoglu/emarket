package com.okamiko.feature.domain.usecase

import com.okamiko.feature.data.mapper.toEntity
import com.okamiko.feature.data.remote.dto.ProductDto
import com.okamiko.feature.domain.repository.BasketRepository

class AddToBasketUseCase(
    private val basketRepository: BasketRepository
) {
    suspend operator fun invoke(product: ProductDto) {
        val existing = basketRepository.getBasketItem(product.id ?: "")
        if (existing == null) {
            basketRepository.addToBasket(product.toEntity().copy(quantity = 1))
        } else {
            basketRepository.updateBasket(existing.copy(quantity = existing.quantity + 1))
        }
    }
}