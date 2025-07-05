package com.food.foodporterapplication.customer.activity.cartitemdetailpageapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.cremation.funeralcremation.utils.ErrorUtil
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.adapter.CompleteYourMealAdapter
import com.food.foodporterapplication.customer.fragment.cartItemDetail.model.CartItemFragment
import com.food.foodporterapplication.customer.fragment.CheckoutFragment
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

        // Load CartItemFragment initially
        supportFragmentManager.beginTransaction()
            .replace(R.id.menuDetailFrameLayout, CartItemFragment())
            .commit()
    }

    // From CartItemFragment -> navigates to CheckoutFragment
    override fun onCartButtonClickListener() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.menuDetailFrameLayout, CheckoutFragment())
            .commit()

        binding.step3Circle.setBackgroundResource(R.drawable.circle_filled_dark)
        binding.step3Circle.setTextColor(ContextCompat.getColor(this, R.color.white))
        binding.viewMenuLine.visibility = View.VISIBLE
        binding.progressLine.visibility = View.GONE
        binding.viewMenuLine.setBackgroundResource(R.color.red)

    }

    // Called from inside CheckoutFragment -> just change the circle color
    override fun onCheckoutButtonListener() {

    }
}
