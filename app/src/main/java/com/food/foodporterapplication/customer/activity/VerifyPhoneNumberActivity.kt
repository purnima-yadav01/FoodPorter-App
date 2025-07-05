package com.food.foodporterapplication.customer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.food.foodporterapplication.databinding.ActivityVerifyPhoneNumberBinding

class VerifyPhoneNumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerifyPhoneNumberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVerifyPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}