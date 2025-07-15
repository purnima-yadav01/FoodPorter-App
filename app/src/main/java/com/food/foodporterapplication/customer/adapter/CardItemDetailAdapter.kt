package com.food.foodporterapplication.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.OnUpdateQuantityListener
import com.food.foodporterapplication.customer.fragment.cartItemDetail.model.CardItemDetailResponse

class CardItemDetailAdapter(
    val context: Context,
    private val cartUserList: List<CardItemDetailResponse.Item>,
    private val quantityUpdate: OnUpdateQuantityListener,
    private val quantityChangeListener: (Double, Int) -> Unit
) : RecyclerView.Adapter<CardItemDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.add_to_item_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = cartUserList[position]
        val itemPrice = model.dishPrice?.toDoubleOrNull() ?: 0.0
        var quantity = model.quantity ?: 1

        holder.ItemName.text = model.dishName ?: ""
        holder.itemPrice.text = "Rs %.2f".format(itemPrice)
        holder.quantityTextItemView.text = quantity.toString()
        holder.itemsPrice.text = "Rs %.2f".format(itemPrice * quantity)

        Glide.with(context)
            .load(model.imageUrl)
            .into(holder.itemsImg)

        val addonsText = model.addons?.joinToString(", ") { it.name ?: "" } ?: ""
        holder.addOnItemText.text = addonsText

        fun notifyPriceUpdate() {
            var subtotal = 0.0
            var total = 0
            cartUserList.forEach {
                val price = it.dishPrice?.toDoubleOrNull() ?: 0.0
                val qty = it.quantity ?: 1
                subtotal += price * qty
                total += it.totalPrice ?: (price * qty).toInt()
            }

            quantityChangeListener(subtotal, total)
        }

        holder.increaseItemLayout.setOnClickListener {
            quantity++
            model.quantity = quantity
            holder.quantityTextItemView.text = quantity.toString()
            holder.itemsPrice.text = "Rs %.2f".format(itemPrice * quantity)
            notifyPriceUpdate()

            // Call the listener here
            quantityUpdate.quantityClick(position, model.dishId ?: 0, quantity)
        }

        holder.decreaseItemLayout.setOnClickListener {
            if (quantity > 1) {
                quantity--
                model.quantity = quantity
                holder.quantityTextItemView.text = quantity.toString()
                holder.itemsPrice.text = "Rs %.2f".format(itemPrice * quantity)
                notifyPriceUpdate()

                // Call the update quantity listener
                quantityUpdate.quantityClick(position, model.dishId ?: 0, quantity)
            }
        }
    }

    override fun getItemCount(): Int = cartUserList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemsImg: ImageView = itemView.findViewById(R.id.itemsImg)
        val ItemName: TextView = itemView.findViewById(R.id.ItemName)
        val itemPrice: TextView = itemView.findViewById(R.id.itemPrice)
        val itemsPrice: TextView = itemView.findViewById(R.id.itemsPrice)
        val addOnItemText: TextView = itemView.findViewById(R.id.addOnItemText)
        val quantityTextItemView: TextView = itemView.findViewById(R.id.quantityTextItemView)
        val decreaseItemLayout: LinearLayout = itemView.findViewById(R.id.decreaseItemLayout)
        val increaseItemLayout: LinearLayout = itemView.findViewById(R.id.increaseItemLayout)
    }
}


