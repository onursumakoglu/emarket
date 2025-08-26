package com.okamiko.feature.presentation.productDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.okamiko.core.utils.toTurkishPrice
import com.okamiko.feature.R
import com.okamiko.feature.databinding.FragmentProductDetailBinding
import com.okamiko.feature.presentation.basket.BasketViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    private lateinit var binding: FragmentProductDetailBinding
    private val receivedArgs: ProductDetailFragmentArgs by navArgs()
    private val basketViewModel by viewModel<BasketViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductDetailBinding.bind(view)
        initView()
        listener()
    }

    private fun initView() = with(binding) {
        priceLayout.totalPrice.text = getString(R.string.price_title)
        priceLayout.actionBtn.text = getString(R.string.add_basket_title)
        receivedArgs.product.let {
            (requireActivity() as AppCompatActivity).supportActionBar?.title = it.name
            productName.text = it.name
            productDescription.text = it.description
            priceLayout.totalPriceText.text = it.price?.toTurkishPrice()
            Glide.with(productImage).load(it.image).placeholder(R.drawable.bg_image_place_holder).into(productImage);
            setLikeUI()
        }
    }

    private fun listener() = with(binding) {
        priceLayout.actionBtn.setOnClickListener {
            basketViewModel.addToBasket(receivedArgs.product)
            Toast.makeText(context, "${receivedArgs.product.name} sepetinize eklendi.", Toast.LENGTH_SHORT).show()
        }
        likeIcon.setOnClickListener {
            receivedArgs.product.isFavorite = receivedArgs.product.isFavorite?.not()
            setLikeUI()
        }
    }

    private fun setLikeUI() = with(binding) {
        if (receivedArgs.product.isFavorite == true) {
            likeIcon.setImageResource(R.drawable.ic_liked_star)
        } else {
            likeIcon.setImageResource(R.drawable.ic_not_liked_star)
        }
    }
}