package com.food.foodporterapplication.customer.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.food.foodporterapplication.databinding.ActivityFoodPorterPageBinding

class FoodPorterPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodPorterPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFoodPorterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.continueBtn.setOnClickListener {
            val intent = Intent(this@FoodPorterPageActivity, ChooseFoodPorterActivity::class.java)
            startActivity(intent)
        }
    }
}