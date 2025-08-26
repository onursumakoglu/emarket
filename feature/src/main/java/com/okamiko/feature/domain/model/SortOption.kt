package com.okamiko.feature.domain.model

sealed class SortOption {
    data object PriceLowToHigh : SortOption()
    data object PriceHighToLow : SortOption()
    data object DateNewToOld : SortOption()
    data object DateOldToNew : SortOption()
    data object None : SortOption()
}