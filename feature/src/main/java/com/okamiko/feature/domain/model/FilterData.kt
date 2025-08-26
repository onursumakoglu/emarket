package com.okamiko.feature.domain.model

data class FilterData(
    val sortOption: SortOption = SortOption.None,
    val selectedBrands: List<String> = emptyList(),
    val selectedModels: List<String> = emptyList()
)