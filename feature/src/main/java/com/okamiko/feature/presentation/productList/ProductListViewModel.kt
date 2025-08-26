package com.okamiko.feature.presentation.productList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okamiko.core.utils.Resource
import com.okamiko.feature.data.remote.dto.ProductDto
import com.okamiko.feature.domain.model.FilterData
import com.okamiko.feature.domain.model.SortOption
import com.okamiko.feature.domain.repository.ProductListRepository
import kotlinx.coroutines.launch
import java.time.Instant

class ProductListViewModel(private val repository: ProductListRepository) : ViewModel() {

    private var currentPage = 0
    private val pageSize = 4
    private var currentQuery: String? = null

    private val _productListEvent = MutableLiveData<Resource<List<ProductDto>?>>()
    val productListEvent: LiveData<Resource<List<ProductDto>?>> get() = _productListEvent

    var allProducts: MutableList<ProductDto> = mutableListOf()
    private var filteredProducts: MutableList<ProductDto> = mutableListOf()

    init {
        getProductList()
    }

    private fun getProductList() = viewModelScope.launch {
        _productListEvent.value = Resource.Loading
        try {
            val result = repository.getProductList()
            if (result.isSuccessful) {
                val response = result.body()
                response?.let {
                    allProducts = response.toMutableList()
                    filteredProducts = allProducts
                    loadNextPage()
                }
            } else {
                _productListEvent.value = Resource.Error("Teknik nedenlerden dolayı işleminizi gerçekleştiremiyoruz.")
            }
        } catch (e: Exception) {
            _productListEvent.value = Resource.Error("Lütfen internet bağlantınızı kontrol ediniz.")
        }

    }

    fun loadNextPage() {
        val listToUse = filteredProducts
        val start = currentPage * pageSize
        if (start >= listToUse.size) {
            _productListEvent.value = Resource.Success(filteredProducts)
            return
        }

        val end = minOf(start + pageSize, listToUse.size)
        val nextItems = listToUse.subList(0, end)

        _productListEvent.value = Resource.Success(nextItems)
        currentPage++
    }

    fun filterProductsWithQuery(query: String) {
        if (currentQuery.isNullOrEmpty() && query.isNullOrEmpty()) return

        currentQuery = query
        filteredProducts = if (query.isBlank()) {
            allProducts
        } else {
            allProducts.filter { product ->
                product.name?.contains(query, ignoreCase = true) == true
            }.toMutableList()
        }
        currentPage = 0
        loadNextPage()
    }

    fun applyFilter(filterData: FilterData) {
        val sortedList = when (filterData.sortOption) {
            SortOption.PriceLowToHigh -> allProducts.sortedBy { it.price?.toDoubleOrNull() }
            SortOption.PriceHighToLow -> allProducts.sortedByDescending { it.price?.toDoubleOrNull() }
            SortOption.DateNewToOld -> allProducts.sortedByDescending { Instant.parse(it.createdAt) }
            SortOption.DateOldToNew -> allProducts.sortedBy { Instant.parse(it.createdAt) }
            else -> allProducts
        }

        val filteredBrandList = if (filterData.selectedBrands.isEmpty().not()) {
            sortedList.filter { filterData.selectedBrands.contains(it.brand) }
        } else {
            sortedList
        }

        val filteredModelList = if (filterData.selectedModels.isEmpty().not()) {
            filteredBrandList.filter { filterData.selectedModels.contains(it.model) }
        } else {
            filteredBrandList
        }

        filteredProducts = filteredModelList.toMutableList()
        _productListEvent.value = Resource.Success(filteredProducts)
    }

}