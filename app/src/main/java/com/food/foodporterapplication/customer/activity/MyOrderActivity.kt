package com.food.foodporterapplication.customer.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cremation.funeralcremation.utils.ErrorUtil
import com.food.foodporterapplication.customer.adapter.OrderListAdapter
import com.food.foodporterapplication.customer.fragment.myorderlist.OnCancelClickListener
import com.food.foodporterapplication.customer.fragment.myorderlist.model.MyOrderListModelView
import com.food.foodporterapplication.customer.fragment.myorderlist.model.MyOrderListResponse
import com.food.foodporterapplication.databinding.ActivityMyOrderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOrderActivity : AppCompatActivity(), OnCancelClickListener {
    private lateinit var binding: ActivityMyOrderBinding
    private var adapter: OrderListAdapter? = null
    private val myOrderListModelView: MyOrderListModelView by viewModels()
    private var orderItemList: List<MyOrderListResponse.Order> = ArrayList()
    private var mysubOrderItemList: List<MyOrderListResponse.Item> = ArrayList()
    private lateinit var activity: Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         activity = this

        binding = ActivityMyOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBackImg.setOnClickListener {
            finish()
        }

        orderListApi()
        orderListObserver()
    }

    private fun orderListApi() {

        myOrderListModelView.orderListUser(activity)

    }

    private fun orderListObserver() {
        myOrderListModelView.mMyOrderListResponse.observe(this) {
            val success = it.peekContent().success
            val message = it.peekContent().message
            orderItemList = it.peekContent().orders!!

            try {
                if (success == true) {
                    adapter = OrderListAdapter(this, orderItemList, this)
                    binding.myOrderRecycler.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    binding.myOrderRecycler.adapter = adapter

                } else {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }

            myOrderListModelView.errorResponse.observe(this) {
                ErrorUtil.handlerGeneralError(this, it)
            }
        }
    }

    override fun onClickCancel(orderId: Int, position: Int) {

    }
}