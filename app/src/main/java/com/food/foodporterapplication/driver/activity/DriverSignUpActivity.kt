package com.food.foodporterapplication.driver.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.food.foodporterapplication.databinding.ActivityDriverSignUpBinding

class DriverSignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDriverSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDriverSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}