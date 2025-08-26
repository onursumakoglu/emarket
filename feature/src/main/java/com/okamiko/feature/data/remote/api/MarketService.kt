package com.okamiko.feature.data.remote.api

import com.okamiko.feature.data.remote.dto.ProductDto
import retrofit2.Response
import retrofit2.http.GET

interface MarketService {
    @GET("products")
    suspend fun getProducts(): Response<List<ProductDto>>
}