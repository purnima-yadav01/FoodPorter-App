package com.food.foodporterapplication.customer.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.food.foodporterapplication.customer.activity.profileapi.ViewProfileActivity
import com.food.foodporterapplication.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private var editImg = "1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBackImg.setOnClickListener {
            finish()
        }

        binding.settingConstraint.setOnClickListener {
            val intent = Intent(this, ViewProfileActivity::class.java)
            intent.putExtra("editImg", editImg)
            startActivity(intent)
        }

        binding.notificationConstraint.setOnClickListener {
            val intent = Intent(this, CustomerNotificationsActivity::class.java)
            startActivity(intent)
        }

    }
}