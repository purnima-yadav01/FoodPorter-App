package com.food.foodporterapplication.customer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.adapter.PaymentHistoryAdapter
import com.food.foodporterapplication.customer.model.PaymentMethodModel
import com.food.foodporterapplication.databinding.ActivityPaymentMethodBinding

class PaymentMethodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentMethodBinding
    private lateinit var adapter: PaymentHistoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaymentMethodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBackArrow.setOnClickListener {
            finish()
        }

        val data = listOf(
            PaymentMethodModel(R.drawable.gpay, "Pay Via UPI"),
            PaymentMethodModel(R.drawable.gpay, "Pay Via UPI"),
            PaymentMethodModel(R.drawable.gpay, "Pay Via UPI"),
            PaymentMethodModel(R.drawable.gpay, "Pay Via UPI"),
            PaymentMethodModel(R.drawable.gpay, "Pay Via UPI"),

            )

        adapter = PaymentHistoryAdapter(this, data)
        binding.paymentRecyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.paymentRecyclerview.adapter = adapter
    }
}