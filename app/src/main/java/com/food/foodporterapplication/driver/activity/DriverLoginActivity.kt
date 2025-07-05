package com.food.foodporterapplication.driver.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.food.foodporterapplication.databinding.ActivityDriverLoginBinding

class DriverLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDriverLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDriverLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}