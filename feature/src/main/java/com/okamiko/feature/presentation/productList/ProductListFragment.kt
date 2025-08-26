package com.okamiko.feature.presentation.productList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.okamiko.core.utils.GridSpaceDecoration
import com.okamiko.core.utils.Resource
import com.okamiko.feature.R
import com.okamiko.feature.data.remote.dto.ProductDto
import com.okamiko.feature.databinding.FragmentProductListBinding
import com.okamiko.feature.presentation.basket.BasketViewModel
import com.okamiko.feature.presentation.filter.FilterDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListFragment : Fragment(R.layout.fragment_product_list) {

    private lateinit var binding: FragmentProductListBinding
    private val viewModel by viewModel<ProductListViewModel>()
    private val basketViewModel by viewModel<BasketViewModel>()

    private val productListAdapter by lazy {
        ProductListAdapter(
            onProductClick = { product ->
                val action = ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(product = product)
                view?.let { Navigation.findNavController(it).navigate(action) }
            },
            onFavoriteClick = { product, position ->
                updateProductFavoriteStatus(product, position)
            },
            onAddBasketClick = { product ->
                basketViewModel.addToBasket(product)
                Toast.makeText(context, "${product.name} sepetinize eklendi.", Toast.LENGTH_SHORT).show()
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductListBinding.bind(view)
        initAdapter()
        observeProducts()
        listener()
    }

    private fun observeProducts() {
        viewModel.productListEvent.observe(viewLifecycleOwner) { response ->
            binding.stateView.isVisible = response == Resource.Loading
            when (response) {
                is Resource.Success -> {
                    binding.searchView.isVisible = true
                    binding.filterLayout.isVisible = true
                    productListAdapter.submitList(response.data)
                }

                is Resource.Error -> {
                    Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                }

                else -> {}
            }
        }
    }

    private fun listener() = with(binding) {
        rvProductList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(rv, dx, dy)
                val layoutManager = rv.layoutManager as GridLayoutManager
                val lastVisible = layoutManager.findLastVisibleItemPosition()
                val total = productListAdapter.itemCount

                if (lastVisible >= total - 2) {
                    viewModel.loadNextPage()
                }
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterProductsWithQuery(newText ?: "")
                return true
            }
        })

        filterBtn.setOnClickListener {
            val dialog = FilterDialogFragment(viewModel.allProducts) { filterData ->
                binding.rvProductList.scrollToPosition(0)
                viewModel.applyFilter(filterData)
            }
            dialog.show(parentFragmentManager, "")
        }
    }

    private fun initAdapter() {
        binding.rvProductList.apply {
            layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            addItemDecoration(GridSpaceDecoration(spanCount = 2, context = context, spacingInDp = 21f))
            adapter = productListAdapter
        }
    }

    private fun updateProductFavoriteStatus(product: ProductDto, position: Int) {
        viewModel.allProducts.find { it == product }?.isFavorite = product.isFavorite?.not()
        productListAdapter.notifyItemChanged(position)
    }

}