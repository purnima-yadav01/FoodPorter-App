package com.food.foodporterapplication.customer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.food.foodporterapplication.databinding.ActivityViewAllCouponBinding

class ViewAllCouponActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewAllCouponBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewAllCouponBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}