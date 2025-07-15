package com.food.foodporterapplication.customer.activity.cartitemdetailpageapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.fragment.cartItemDetail.model.CartItemFragment
import com.food.foodporterapplication.customer.fragment.checkoutorderapi.CheckoutFragment
import com.food.foodporterapplication.databinding.ActivityCardItemDetailPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardItemDetailPageActivity : AppCompatActivity(), CartItemFragment.OnCartItemClickListener, CheckoutFragment.OnCheckoutClickListener {
    private lateinit var binding: ActivityCardItemDetailPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCardItemDetailPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imaBackMain.setOnClickListener {
            finish()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.menuDetailFrameLayout, CartItemFragment())
            .commit()
    }

    override fun onCartNextButtonClickListener() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.menuDetailFrameLayout, CheckoutFragment())
            .commit()

        binding.step3Circle.setBackgroundResource(R.drawable.circle_filled_dark)
        binding.step3Circle.setTextColor(ContextCompat.getColor(this, R.color.white))
        binding.viewMenuLine.visibility = View.VISIBLE
        binding.progressLine.visibility = View.GONE
        binding.viewMenuLine.setBackgroundResource(R.color.red)

    }

    override fun onCheckoutBackButtonListener() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.menuDetailFrameLayout, CartItemFragment())
            .commit()

        binding.step3Circle.setBackgroundResource(R.drawable.circle_filled_outline)
        binding.step3Circle.setTextColor(ContextCompat.getColor(this,R.color.black))
        binding.viewMenuLine.visibility = View.GONE
        binding.progressLine.visibility = View.VISIBLE
        binding.viewMenuLine.setBackgroundResource(R.color.gray)

    }
}
