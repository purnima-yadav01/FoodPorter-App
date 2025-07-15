package com.food.foodporterapplication.customer.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.activity.orderdetailpage.OrderDetailsPageActivity
import com.food.foodporterapplication.customer.activity.RatingActivity
import com.food.foodporterapplication.customer.activity.TrackingOrderActivity
import com.food.foodporterapplication.customer.fragment.myorderlist.OnCancelClickListener
import com.food.foodporterapplication.customer.fragment.myorderlist.model.MyOrderListResponse


class OrderListAdapter(
    val context: Context,
    private val offerList: List<MyOrderListResponse.Order>,
    private val cancelOrderListener: OnCancelClickListener
) :
    RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.myorder_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modelView = offerList[position]

        // ✅ Add this block
        val itemsList = modelView.items ?: emptyList()
        val orderedItemAdapter = OrderedItemAdapter(context, itemsList)
        holder.nestedRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        holder.nestedRecyclerView.adapter = orderedItemAdapter


        val url = modelView.restaurantImageUrl
        Glide.with(context).load(url).into(holder.itemsImg)
        holder.restName.text = modelView.restaurantName
        holder.locationText.text = modelView.address
        holder.locationText.text = modelView.address
        holder.priceText.text = modelView.finalAmount
        //holder.orderStatus.text = modelView.s

        val deliveryTimeStatus = "${modelView.orderDate} ${modelView.orderTime}"
        holder.placedOrderText.text = deliveryTimeStatus

        holder.viewMoreDetail.visibility = View.GONE

        holder.moreImg.setOnClickListener {
            val isVisible = holder.viewMoreDetail.visibility == View.VISIBLE
            holder.viewMoreDetail.visibility = if (isVisible) View.GONE else View.VISIBLE
        }

        holder.orderDetailConst.setOnClickListener {
            val intent = Intent(context, OrderDetailsPageActivity::class.java)
            intent.putExtra("orderId", modelView.orderId ?: 0)
            Log.d("ClickOrder", "Sending orderId: ${modelView.orderId}")
            context.startActivity(intent)
        }


        holder.trackOrderConst.setOnClickListener {
            val intent = Intent(context, TrackingOrderActivity::class.java)
            context.startActivity(intent)
        }

        holder.deleteConst.setOnClickListener {

        }

        holder.orderDetailConst.setOnClickListener {
            val intent = Intent(context, OrderDetailsPageActivity::class.java)
            context.startActivity(intent)
        }

        holder.rateOrderConst.setOnClickListener {
            val intent = Intent(context, RatingActivity::class.java)
            context.startActivity(intent)
        }

        holder.deleteConst.setOnClickListener {
            holder.viewMoreDetail.visibility = View.GONE

            val dialog = Dialog(context)
            val dialogView =
                LayoutInflater.from(context).inflate(R.layout.delete_order_history_dialog, null)
            dialog.setContentView(dialogView)
            dialog.setCancelable(true)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            val cancelText = dialogView.findViewById<TextView>(R.id.cancelText)
            val confirmText = dialogView.findViewById<ConstraintLayout>(R.id.deleteBtnConst)

            cancelText.setOnClickListener {
                dialog.dismiss()
            }

            confirmText.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()

        }

        holder.cancelConst.setOnClickListener {
            cancelOrderListener.onClickCancel(modelView.orderId ?: 0, position)
        }
    }

    override fun getItemCount(): Int {

        return offerList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewMoreDetail: CardView = itemView.findViewById(R.id.viewMoreDetail)
        val moreImg: ImageView = itemView.findViewById(R.id.moreImg)
        val itemsImg: ImageView = itemView.findViewById(R.id.itemsImg)
        val restName: TextView = itemView.findViewById(R.id.restName)
        val locationText: TextView = itemView.findViewById(R.id.locationText)
        val placedOrderText: TextView = itemView.findViewById(R.id.placedOrderText)
        val priceText: TextView = itemView.findViewById(R.id.priceText)
        val deliveryStatusText: TextView = itemView.findViewById(R.id.deliveryStatusText)
        val orderDetailConst: ConstraintLayout = itemView.findViewById(R.id.orderDetailConst)
        val deleteConst: ConstraintLayout = itemView.findViewById(R.id.deleteConst)
        val cancelConst: ConstraintLayout = itemView.findViewById(R.id.cancelConst)
        val rateOrderConst: ConstraintLayout = itemView.findViewById(R.id.rateOrderConst)
        val trackOrderConst: ConstraintLayout = itemView.findViewById(R.id.trackOrderConst)
        val nestedRecyclerView: RecyclerView = itemView.findViewById(R.id.addOnsRecycler)

    }
}