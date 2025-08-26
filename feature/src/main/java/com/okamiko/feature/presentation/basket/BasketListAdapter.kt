package com.okamiko.feature.presentation.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.okamiko.core.utils.toTurkishPrice
import com.okamiko.feature.R
import com.okamiko.feature.data.remote.dto.ProductDto
import com.okamiko.feature.databinding.ItemBasketBinding

internal class BasketListAdapter(
    private val onAddBasketClick: (product: ProductDto) -> Unit,
    private val onDeleteBasketClick: (product: ProductDto) -> Unit
) :
    ListAdapter<ProductDto, BasketListAdapter.BasketViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BasketViewHolder(private val binding: ItemBasketBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.increaseButton.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onAddBasketClick.invoke(getItem(adapterPosition))
                }
            }
            binding.decreaseButton.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onDeleteBasketClick.invoke(getItem(adapterPosition))
                }
            }
        }

        fun bind(product: ProductDto) = with(binding) {
            productName.text = product.name
            productPrice.text = ((product.price?.toDouble() ?: 0.0) * product.quantity).toString().toTurkishPrice()
            quantityText.text = product.quantity.toString()

            if (product.quantity > 1) {
                decreaseButton.setImageResource(R.drawable.ic_decrease)
            } else {
                decreaseButton.setImageResource(R.drawable.ic_delete)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val binding = ItemBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BasketViewHolder(binding)
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductDto>() {
            override fun areItemsTheSame(oldItem: ProductDto, newItem: ProductDto): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ProductDto, newItem: ProductDto): Boolean {
                return oldItem == newItem
            }
        }
    }
}