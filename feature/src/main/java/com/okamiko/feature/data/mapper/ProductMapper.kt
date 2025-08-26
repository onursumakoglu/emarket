package com.okamiko.feature.data.mapper

import com.okamiko.core.domain.entity.ProductEntity
import com.okamiko.feature.data.remote.dto.ProductDto

fun ProductDto.toEntity(): ProductEntity {
    return ProductEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        image = this.image,
        price = this.price,
        model = this.model,
        brand = this.brand,
        createdAt = this.createdAt,
        isFavorite = this.isFavorite,
        quantity = this.quantity
    )
}

fun ProductEntity.toDomain(): ProductDto {
    return ProductDto(
        id = this.id,
        name = this.name,
        description = this.description,
        image = this.image,
        price = this.price,
        model = this.model,
        brand = this.brand,
        createdAt = this.createdAt,
        isFavorite = this.isFavorite,
        quantity = this.quantity
    )
}