package com.okamiko.feature.presentation.filter

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDialogFragment
import com.okamiko.feature.R
import com.okamiko.feature.data.remote.dto.ProductDto
import com.okamiko.feature.databinding.DialogFilterBinding
import com.okamiko.feature.domain.model.FilterData
import com.okamiko.feature.domain.model.SortOption

class FilterDialogFragment(
    private val products: List<ProductDto>,
    private var onApplyFilter: ((FilterData) -> Unit)? = null
) : AppCompatDialogFragment(R.layout.dialog_filter) {

    private lateinit var binding: DialogFilterBinding
    private lateinit var brandAdapter: BrandModelAdapter
    private lateinit var modelAdapter: BrandModelAdapter

    private var sortOption: SortOption = SortOption.None

    private var selectedBrands: MutableList<String> = mutableListOf()
    private var selectedModels: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Material_Light_NoActionBar)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogFilterBinding.bind(view)
        initAdapters()
        listener()
    }

    private fun initAdapters() = with(binding) {
        brandAdapter = BrandModelAdapter(items = products.map { it.brand ?: "" }) { checkedItem ->
            if (selectedBrands.contains(checkedItem)) {
                selectedBrands.remove(checkedItem)
            } else {
                selectedBrands.add(checkedItem)
            }
        }
        rvBrand.adapter = brandAdapter

        modelAdapter = BrandModelAdapter(items = products.map { it.model ?: "" }) { checkedItem ->
            if (selectedModels.contains(checkedItem)) {
                selectedModels.remove(checkedItem)
            } else {
                selectedModels.add(checkedItem)
            }
        }
        rvModel.adapter = modelAdapter
    }

    private fun listener() {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioPriceLowHigh -> sortOption = SortOption.PriceLowToHigh
                R.id.radioPriceHighLow -> sortOption = SortOption.PriceHighToLow
                R.id.radioDateNewOld -> sortOption = SortOption.DateNewToOld
                R.id.radioDateOldNew -> sortOption = SortOption.DateOldToNew
            }
        }

        binding.btnApply.setOnClickListener {
            onApplyFilter?.invoke(
                FilterData(
                    sortOption = sortOption,
                    selectedBrands = selectedBrands,
                    selectedModels = selectedModels
                )
            )
            dismiss()
        }
        binding.toolbar.setNavigationOnClickListener { dismiss() }
    }
}