package com.food.foodporterapplication.customer.classes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.food.foodporterapplication.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}