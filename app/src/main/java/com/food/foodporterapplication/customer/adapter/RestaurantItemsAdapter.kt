package com.food.foodporterapplication.customer.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.activity.addtocartitemapi.MenuItemAddActivity
import com.food.foodporterapplication.customer.activity.getrestaurantdetailsapi.RestaurantDetailModelView
import com.food.foodporterapplication.customer.activity.getrestaurantdetailsapi.RestaurantDetailResponse
import com.food.foodporterapplication.customer.model.RestaurantDishesModel

class RestaurantItemsAdapter (val context: Context, private val restDetailList: List<RestaurantDishesModel>) :
    RecyclerView.Adapter<RestaurantItemsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_dishes_layout, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {

        val modelView = restDetailList[position]
       // val imageUrl = modelView.image
      //  Glide.with(context).load(imageUrl).into(holder.imageFood)
      //  holder.textFoodName.text = modelView.name
     //   holder.textPrice.text = modelView.price
        holder.itemView.setOnClickListener {
            val intent = Intent(context, MenuItemAddActivity::class.java)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return restDetailList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageFood: ImageView = itemView.findViewById(R.id.imageFood)
        val textFoodName: TextView = itemView.findViewById(R.id.textFoodName)
        val textPrice: TextView = itemView.findViewById(R.id.textPrice)

    }
}