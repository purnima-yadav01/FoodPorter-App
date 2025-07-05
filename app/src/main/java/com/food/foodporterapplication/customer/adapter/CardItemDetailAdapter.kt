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
import com.food.foodporterapplication.customer.fragment.cartItemDetail.model.CardItemDetailResponse

class CardItemDetailAdapter(
    val context: Context,
    private val cartUserList: List<CardItemDetailResponse.CartItem>
) : RecyclerView.Adapter<CardItemDetailAdapter.ViewHolder>() {
    companion object{
        var pricePerItem = ""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.add_to_item_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = cartUserList[position]

        val itemPrice = model.price?.toDoubleOrNull() ?: 0.0
        var quantity = model.quantity ?: 1

        // Set initial values
        holder.ItemName.text = model.name
        holder.itemPrice.text = "₹${"%.2f".format(itemPrice)}"
        holder.quantityTextItemView.text = quantity.toString()
        holder.itemsPrice.text = "₹${"%.2f".format(itemPrice * quantity)}"

        // Load image
        Glide.with(context).load(model.image).into(holder.itemsImg)

        // Increase quantity
        holder.increaseItemLayout.setOnClickListener {
            quantity++
            holder.quantityTextItemView.text = quantity.toString()
            holder.itemsPrice.text = "₹${"%.2f".format(itemPrice * quantity)}"
        }

        // Decrease quantity
        holder.decreaseItemLayout.setOnClickListener {
            if (quantity > 1) {
                quantity--
                holder.quantityTextItemView.text = quantity.toString()
                holder.itemsPrice.text = "₹${"%.2f".format(itemPrice * quantity)}"
            }
        }
    }

    override fun getItemCount(): Int {
        return cartUserList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemsImg: ImageView = itemView.findViewById(R.id.itemsImg)
        val ItemName: TextView = itemView.findViewById(R.id.ItemName)
        val itemPrice: TextView = itemView.findViewById(R.id.itemPrice)
        val itemsPrice: TextView = itemView.findViewById(R.id.itemsPrice)
        val quantityTextItemView: TextView = itemView.findViewById(R.id.quantityTextItemView)
        val decreaseItemLayout: LinearLayout = itemView.findViewById(R.id.decreaseItemLayout)
        val increaseItemLayout: LinearLayout = itemView.findViewById(R.id.increaseItemLayout)
    }
}
