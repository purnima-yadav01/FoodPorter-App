package com.food.foodporterapplication.customer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.food.foodporterapplication.databinding.ActivityOrderDetailsPageBinding

class OrderDetailsPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailsPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderDetailsPageBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.imaBackMain.setOnClickListener {
            finish()
        }
    }
}