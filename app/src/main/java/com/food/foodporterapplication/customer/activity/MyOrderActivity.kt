package com.food.foodporterapplication.customer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.foodporterapplication.customer.adapter.OrderListAdapter
import com.food.foodporterapplication.customer.model.OrderListModel
import com.food.foodporterapplication.databinding.ActivityMyOrderBinding

class MyOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyOrderBinding
    private lateinit var adapter: OrderListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBackImg.setOnClickListener {
            finish()
        }

        val data = listOf(
            OrderListModel(),
            OrderListModel(),
            OrderListModel(),
            OrderListModel(),
            OrderListModel(),

            )

        adapter = OrderListAdapter(this, data)
        binding.myOrderRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.myOrderRecycler.adapter = adapter

    }
}