package com.okamiko.feature.data.remote.repository

import com.okamiko.feature.data.remote.api.MarketService
import com.okamiko.feature.data.remote.dto.ProductDto
import com.okamiko.feature.domain.repository.ProductListRepository
import retrofit2.Response

class ProductListRepositoryImpl(private val marketService: MarketService) : ProductListRepository {
    override suspend fun getProductList(): Response<List<ProductDto>> {
        return marketService.getProducts()
    }
}