package com.okamiko.feature.presentation.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.okamiko.feature.databinding.ItemBrandModelCheckboxBinding

class BrandModelAdapter(
    private val items: List<String>,
    private val onCheckedItem: (String) -> Unit
) : RecyclerView.Adapter<BrandModelAdapter.BrandModelViewHolder>() {

    inner class BrandModelViewHolder(private val binding: ItemBrandModelCheckboxBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(name: String) {
            binding.checkbox.text = name
            binding.checkbox.setOnCheckedChangeListener { _, _ ->
                onCheckedItem(name)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandModelViewHolder {
        val binding = ItemBrandModelCheckboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BrandModelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BrandModelViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
