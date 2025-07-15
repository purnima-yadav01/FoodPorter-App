package com.food.foodporterapplication.customer.activity.orderdetailpage

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.cremation.funeralcremation.utils.ErrorUtil
import com.food.foodporterapplication.customer.activity.orderdetailpage.model.OrderDetailBody
import com.food.foodporterapplication.customer.activity.orderdetailpage.model.OrderDetailModelView
import com.food.foodporterapplication.databinding.ActivityOrderDetailsPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailsPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailsPageBinding
    private val orderDetailModelView: OrderDetailModelView by viewModels()
    private lateinit var activity: Activity
    private var orderId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderDetailsPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this

        orderId = intent.getIntExtra("orderId", 0)
        Log.e("ClickOrder", orderId.toString())

        binding.imaBackMain.setOnClickListener {
            finish()
        }

        orderDetailsApi()
        orderDetailsObserver()
    }

    private fun orderDetailsApi() {
        val body = OrderDetailBody(
            order_id = orderId
        )
        orderDetailModelView.orderDetailUser(activity, body)
    }

    private fun orderDetailsObserver() {
        orderDetailModelView.progressIndicator.observe(this) {}
        orderDetailModelView.mOrderDetailResponse.observe(this) {
            val success = it.peekContent().success
            val message = it.peekContent().message
            val orderItemData = it.peekContent().order
            if (success == true) {
                binding.restName.text = orderItemData?.restaurantName
                binding.restAddressText.text = orderItemData?.restaurantAddress
                binding.totalItemText.text = orderItemData?.totalAmount
                binding.paidAmountText.text = orderItemData?.finalAmount
                binding.paymentMethodType.text = orderItemData?.paymentMethod
                binding.paymentDateType.text = orderItemData?.orderDate
                binding.deliveryAdddress.text = orderItemData?.address
                val imageUrl = orderItemData?.restaurantImageUrl
                    Glide.with(this).load(imageUrl).into(binding.itemsImg)
            } else {

            }

        }

        orderDetailModelView.errResponse.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }

}