package com.okamiko.feature.domain.repository

import com.okamiko.feature.data.remote.dto.ProductDto
import retrofit2.Response

interface ProductListRepository {
    suspend fun getProductList(): Response<List<ProductDto>>
}