package com.okamiko.feature.presentation.productList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.okamiko.core.utils.toTurkishPrice
import com.okamiko.feature.R
import com.okamiko.feature.data.remote.dto.ProductDto
import com.okamiko.feature.databinding.ItemProductBinding

internal class ProductListAdapter(
    private val onProductClick: (product: ProductDto) -> Unit,
    private val onFavoriteClick: (product: ProductDto, position: Int) -> Unit,
    private val onAddBasketClick: (product: ProductDto) -> Unit
) :
    ListAdapter<ProductDto, ProductListAdapter.ProductViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onProductClick.invoke(getItem(adapterPosition))
                }
            }
            binding.likeIcon.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onFavoriteClick.invoke(getItem(adapterPosition), adapterPosition)
                }
            }
            binding.addBasketBtn.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onAddBasketClick.invoke(getItem(adapterPosition))
                }
            }
        }

        fun bind(product: ProductDto) = with(binding) {
            productPrice.text = product.price?.toTurkishPrice()
            productName.text = product.name
            if (product.isFavorite == true) {
                likeIcon.setImageResource(R.drawable.ic_liked_star)
            } else {
                likeIcon.setImageResource(R.drawable.ic_not_liked_star)
            }
            Glide.with(productImage).load(product.image).placeholder(R.drawable.bg_image_place_holder).into(productImage);
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
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