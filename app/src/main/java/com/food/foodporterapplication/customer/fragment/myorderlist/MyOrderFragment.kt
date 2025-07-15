package com.food.foodporterapplication.customer.fragment.myorderlist

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cremation.funeralcremation.utils.ErrorUtil
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.adapter.OrderListAdapter
import com.food.foodporterapplication.customer.adapter.OrderedItemAdapter
import com.food.foodporterapplication.customer.fragment.myorderlist.cancelorderapi.CancelOrderBody
import com.food.foodporterapplication.customer.fragment.myorderlist.cancelorderapi.CancelOrderModelView
import com.food.foodporterapplication.customer.fragment.myorderlist.model.MyOrderListModelView
import com.food.foodporterapplication.customer.fragment.myorderlist.model.MyOrderListResponse
import com.food.foodporterapplication.customer.model.OrderListModel
import com.food.foodporterapplication.databinding.FragmentMyOrderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOrderFragment : Fragment(), OnCancelClickListener {
    private lateinit var binding: FragmentMyOrderBinding
    private var adapter: OrderListAdapter? = null
    private var orderedItemAdapter: OrderedItemAdapter? = null
    private val myOrderListModelView: MyOrderListModelView by viewModels()
    private val cancelOrderModelView: CancelOrderModelView by viewModels()
    private var orderItemList: List<MyOrderListResponse.Order> = ArrayList()
    private var mysubOrderItemList: List<MyOrderListResponse.Item> = ArrayList()
    private lateinit var activity: Activity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentMyOrderBinding.inflate(layoutInflater, container, false)

        orderListApi()
        orderListObserver()
        cancelOrderObserver()

        val data = listOf(
            OrderListModel(),
            OrderListModel(),
            OrderListModel(),
            OrderListModel(),
            OrderListModel(),

            )

        activity = requireActivity()
        return binding.root

    }

    private fun orderListApi() {

        myOrderListModelView.orderListUser(requireActivity())

    }

    private fun orderListObserver() {
        myOrderListModelView.mMyOrderListResponse.observe(viewLifecycleOwner) {
            val success = it.peekContent().success
            val message = it.peekContent().message
            orderItemList = it.peekContent().orders!!

            try {
                if (success == true) {
                    adapter = OrderListAdapter(requireContext(), orderItemList, this)
                    binding.myOrderRecycler.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    binding.myOrderRecycler.adapter = adapter

                } else {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }

            myOrderListModelView.errorResponse.observe(viewLifecycleOwner) {
                ErrorUtil.handlerGeneralError(requireContext(), it)
            }
        }
    }


    override fun onClickCancel(orderId: Int, position: Int) {
        Log.e("openDialog", "click cancel dialog: $orderId at psotion:  $position")
        canOrderDialog(orderId)

    }

    private fun canOrderDialog(orderId: Int) {
        val dialog1 = Dialog(requireContext())
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog1.setCancelable(true)
        dialog1.setContentView(R.layout.cancel_order_layout)

        val cancelBtn = dialog1.findViewById<Button>(R.id.cancel_button)
        val confirmBtn = dialog1.findViewById<Button>(R.id.confirm_button)
        val cancellation = dialog1.findViewById<EditText>(R.id.cancellationEditText)

        dialog1.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        cancelBtn.setOnClickListener {
            dialog1.dismiss()
        }

        confirmBtn.setOnClickListener {
            val cancelReason = cancellation.text?.toString()?.trim()

            if (cancelReason.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Please enter a valid reason", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            Log.d("FinalCancelReason", "Sending reason: '$cancelReason'")
            cancelOrderApi(orderId, cancelReason)
            dialog1.dismiss()

        }

        Log.d("DialogCheck", "$dialog1.show() called")
        dialog1.show()

    }

    //cancel order api
    private fun cancelOrderApi(orderId: Int, cancelReason: String) {
        val body = CancelOrderBody(
            order_id = orderId,
            cancel_reason = cancelReason
        )
        cancelOrderModelView.cancelUser(requireActivity(), body)
    }

    private fun cancelOrderObserver() {
        cancelOrderModelView.progressIndicator.observe(requireActivity()) {}
        cancelOrderModelView.mCancelOrderResponse.observe(requireActivity()) {
            val message = it.peekContent().message
            val success = it.peekContent().success

            if (success == true) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                orderListApi()
            } else {
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        }

        cancelOrderModelView.errorResponse.observe(requireActivity()) {
            ErrorUtil.handlerGeneralError(requireContext(), it)
        }
    }
}
