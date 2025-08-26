package com.okamiko.feature.presentation.basket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.okamiko.core.utils.toTurkishPrice
import com.okamiko.feature.R
import com.okamiko.feature.data.mapper.toDomain
import com.okamiko.feature.data.remote.dto.ProductDto
import com.okamiko.feature.databinding.FragmentBasketBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BasketFragment : Fragment(R.layout.fragment_basket) {
    private lateinit var binding: FragmentBasketBinding
    private val basketViewModel by viewModel<BasketViewModel>()

    private val basketListAdapter by lazy {
        BasketListAdapter(
            onAddBasketClick = { product ->
                basketViewModel.addToBasket(product)
            },
            onDeleteBasketClick = { product ->
                basketViewModel.removeFromBasket(product)
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBasketBinding.bind(view)
        observeData()
    }

    private fun observeData() {
        basketViewModel.basketItems.observe(viewLifecycleOwner) { items ->
            binding.rvBasketList.isVisible = items.isNotEmpty()
            binding.emptyBasketView.isVisible = items.isEmpty()
            if (items.isNotEmpty()) {
                initAdapter(items.map { it.toDomain() })
                binding.priceLayout.totalPriceText.text =
                    items.sumOf { (it.price?.toDouble() ?: 0.0) * it.quantity }.toString().toTurkishPrice()
            } else {
                binding.priceLayout.totalPriceText.text = 0.0.toString()
            }
        }
    }

    private fun initAdapter(products: List<ProductDto>?) {
        binding.rvBasketList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = basketListAdapter
        }
        basketListAdapter.submitList(products)
    }

}