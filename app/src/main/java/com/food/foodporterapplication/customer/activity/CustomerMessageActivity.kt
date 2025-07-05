package com.food.foodporterapplication.customer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.food.foodporterapplication.databinding.ActivityCustomerMessageBinding

class CustomerMessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerMessageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCustomerMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imaBackMain.setOnClickListener {
            finish()
        }
    }
}