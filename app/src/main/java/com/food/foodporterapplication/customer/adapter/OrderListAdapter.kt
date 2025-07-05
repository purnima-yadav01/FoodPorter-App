package com.food.foodporterapplication.customer.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.activity.OrderDetailsPageActivity
import com.food.foodporterapplication.customer.activity.RatingActivity
import com.food.foodporterapplication.customer.activity.TrackingOrderActivity
import com.food.foodporterapplication.customer.model.OrderListModel

class OrderListAdapter(val context: Context, private val offerList: List<OrderListModel>) :
    RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.myorder_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderListAdapter.ViewHolder, position: Int) {
        val modelView = offerList[position]

        holder.viewMoreDetail.visibility = View.GONE

        holder.moreImg.setOnClickListener {
            val isVisible = holder.viewMoreDetail.visibility == View.VISIBLE
            holder.viewMoreDetail.visibility = if (isVisible) View.GONE else View.VISIBLE
        }

        holder.orderDetailConst.setOnClickListener {
            val intent = Intent(context, OrderDetailsPageActivity::class.java)
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
            val dialogView = LayoutInflater.from(context).inflate(R.layout.delete_order_history_dialog, null)
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

            holder.viewMoreDetail.visibility = View.GONE

            val dialog1 = Dialog(context)
            val dialogView1 = LayoutInflater.from(context).inflate(R.layout.cancel_order_layout, null)
            dialog1.setContentView(dialogView1)
            dialog1.setCancelable(true)
            dialog1.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog1.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            val cancelText1 = dialogView1.findViewById<TextView>(R.id.cancelText)
            val confirmText1 = dialogView1.findViewById<ConstraintLayout>(R.id.deleteBtnConst)

            cancelText1.setOnClickListener {
                dialog1.dismiss()
            }

            confirmText1.setOnClickListener {
                dialog1.dismiss()
            }

            dialog1.show()

        }
    }

    override fun getItemCount(): Int {

        return offerList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewMoreDetail: CardView = itemView.findViewById(R.id.viewMoreDetail)
        val moreImg: ImageView = itemView.findViewById(R.id.moreImg)
        val orderDetailConst: ConstraintLayout = itemView.findViewById(R.id.orderDetailConst)
        val deleteConst: ConstraintLayout = itemView.findViewById(R.id.deleteConst)
        val cancelConst: ConstraintLayout = itemView.findViewById(R.id.cancelConst)
        val rateOrderConst: ConstraintLayout = itemView.findViewById(R.id.rateOrderConst)
        val trackOrderConst: ConstraintLayout = itemView.findViewById(R.id.trackOrderConst)

    }
}