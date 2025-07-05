package com.food.foodporterapplication.customer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.foodporterapplication.customer.adapter.CustomerNotificationAdapter
import com.food.foodporterapplication.customer.model.CustomerNotificationModel
import com.food.foodporterapplication.databinding.ActivityCustomerNotificationsBinding

class CustomerNotificationsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerNotificationsBinding
    private lateinit var adapter: CustomerNotificationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCustomerNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBackImg.setOnClickListener {
            finish()
        }

        val data1 = listOf(
            CustomerNotificationModel(),
            CustomerNotificationModel(),
            CustomerNotificationModel(),
            CustomerNotificationModel(),
            CustomerNotificationModel()
        )

        adapter = CustomerNotificationAdapter(this, data1)
        binding.notificationRecyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.notificationRecyclerview.adapter = adapter
    }
}