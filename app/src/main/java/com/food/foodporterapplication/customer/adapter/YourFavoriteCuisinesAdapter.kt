package com.food.foodporterapplication.customer.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.CategoryByRestDishesActivity
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.MenuItemDetailActivity
import com.food.foodporterapplication.customer.activity.categoryorderitem.model.FavoriteCuisinesResponse
import com.food.foodporterapplication.customer.application.FoodPorter

class YourFavoriteCuisinesAdapter (val context: Context, private val categoryItemList: List<FavoriteCuisinesResponse.Datum>) :
    RecyclerView.Adapter<YourFavoriteCuisinesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_related_restaurant, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {

        val modelView = categoryItemList[position]
        val url = modelView.image
        Glide.with(context).load(url).into(holder.imageRestaurant)
        holder.textRestaurantName.text =modelView.name
        holder.textTime.text =modelView.estimatedDeliveryTime
        holder.textRating.text = modelView.rating.toString()
        holder.textDiscount.text = modelView.discount.toString()

        holder.itemView.setOnClickListener {
            val i = Intent(context, CategoryByRestDishesActivity::class.java)
           FoodPorter.encryptedPrefs.restaurantId = modelView.id!!.toInt()
            i.putExtra("restaurantName", modelView.name)
            i.putExtra("restaurantRating", modelView.rating)
            i.putExtra("restaurantAddress", modelView.address)
            i.putExtra("restaurantTime", modelView.estimatedDeliveryTime)
            context.startActivity(i)
            Log.e("restaurantRating", "${modelView.rating}")

        }
    }

    override fun getItemCount(): Int {
        return categoryItemList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageRestaurant: ImageView = itemView.findViewById(R.id.imageRestaurant)
        val textRestaurantName: TextView = itemView.findViewById(R.id.textRestaurantName)
        val textDiscount: TextView = itemView.findViewById(R.id.textDiscount)
        val textTime: TextView = itemView.findViewById(R.id.textTime)
        val textRating: TextView = itemView.findViewById(R.id.textRatingValue)

    }
}