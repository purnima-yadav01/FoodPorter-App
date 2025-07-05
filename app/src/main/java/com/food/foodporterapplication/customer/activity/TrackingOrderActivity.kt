package com.food.foodporterapplication.customer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.food.foodporterapplication.databinding.ActivityTrackingOrderBinding

class TrackingOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrackingOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTrackingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imaBackMain.setOnClickListener {
            finish()
        }
    }
}