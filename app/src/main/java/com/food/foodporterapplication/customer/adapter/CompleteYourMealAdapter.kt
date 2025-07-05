package com.food.foodporterapplication.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.model.AddCategoryItemResponse
import com.food.foodporterapplication.customer.model.CompleteYourMealModel

class CompleteYourMealAdapter (val context: Context, private val offerList: List<AddCategoryItemResponse.Datum>) :
    RecyclerView.Adapter<CompleteYourMealAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompleteYourMealAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.complete_meal_recycerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompleteYourMealAdapter.ViewHolder, position: Int) {

        val modelView = offerList[position]
        val url = modelView.image
        Glide.with(context).load(url).into(holder.restaurantMealImg)
        holder.mealItemName.text = modelView.name
        holder.mealPriceText.text = modelView.price

    }

    override fun getItemCount(): Int {
        return offerList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restaurantMealImg: ImageView = itemView.findViewById(R.id.restaurantMealImg)
        val mealItemName: TextView = itemView.findViewById(R.id.mealItemName)
        val mealPriceText: TextView = itemView.findViewById(R.id.mealPriceText)
        val addItemsPriceConst: ConstraintLayout = itemView.findViewById(R.id.addItemsPriceConst)
    }
}