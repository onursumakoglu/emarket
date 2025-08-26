package com.okamiko.feature.domain.usecase

import com.okamiko.feature.domain.repository.BasketRepository

class RemoveFromBasketUseCase(
    private val basketRepository: BasketRepository
) {
    suspend operator fun invoke(productId: String) {
        val existing = basketRepository.getBasketItem(productId)
        if (existing != null) {
            if (existing.quantity > 1) {
                basketRepository.updateBasket(existing.copy(quantity = existing.quantity - 1))
            } else {
                basketRepository.removeFromBasket(existing)
            }
        }
    }
}