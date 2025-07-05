package com.food.foodporterapplication.customer.activity.applycouponsapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.foodporterapplication.customer.activity.applycouponsapi.viewallcoupons.ViewAllCouponBody
import com.food.foodporterapplication.customer.activity.applycouponsapi.viewallcoupons.ViewAllCouponsModelView
import com.food.foodporterapplication.customer.activity.applycouponsapi.viewallcoupons.ViewAllCouponsResponse
import com.food.foodporterapplication.customer.adapter.PaymentCouponsAdapter
import com.food.foodporterapplication.customer.adapter.RestaurantCouponsAdapter
import com.food.foodporterapplication.customer.application.FoodPorter
import com.food.foodporterapplication.databinding.ActivityAppyVoucherBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppyVoucherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppyVoucherBinding
    private val viewAllCouponsModelView : ViewAllCouponsModelView by viewModels()
    private var  paymentCouponList : List<ViewAllCouponsResponse.GlobalCoupon> = ArrayList()
    private var  restaurantCouponList : List<ViewAllCouponsResponse.RestaurantCoupon> = ArrayList()
    private var paymentCouponAdapter: PaymentCouponsAdapter? = null
    private var restCouponAdapter: RestaurantCouponsAdapter? = null
    private lateinit var activity: AppyVoucherActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAppyVoucherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activity = this

        binding.imaBackMain.setOnClickListener {
            finish()
        }

        viewAllCouponsListApi()
        viewAllCouponsListObserver()
    }

    private fun viewAllCouponsListApi() {
        val body = ViewAllCouponBody(
            restaurant_id = FoodPorter.encryptedPrefs.restaurantId
        )

        viewAllCouponsModelView.viewAllUser(activity, body)
    }

    private fun viewAllCouponsListObserver() {
        viewAllCouponsModelView.mViewAllCouponsResponse.observe(this) {
            val response = it.peekContent()
            val success = response.success

            try {
                if (success == true) {
                    restaurantCouponList = response.restaurantCoupons ?: emptyList()
                    restCouponAdapter = RestaurantCouponsAdapter(this, restaurantCouponList)
                    binding.couponsRecyclerview.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    binding.couponsRecyclerview.adapter = restCouponAdapter

                    paymentCouponList = response.globalCoupons ?: emptyList()
                    paymentCouponAdapter = PaymentCouponsAdapter(this, paymentCouponList)
                    binding.paymentRecyclerview.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    binding.paymentRecyclerview.adapter = paymentCouponAdapter
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}