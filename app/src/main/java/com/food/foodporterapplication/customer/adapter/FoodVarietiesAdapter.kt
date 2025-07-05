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
import com.food.foodporterapplication.customer.activity.categoryorderitem.FavoriteCuisinesActivity
import com.food.foodporterapplication.customer.application.FoodPorter
import com.food.foodporterapplication.customer.fragment.homepage.getallcategory.GetAllCategoryResponse

class FoodVarietiesAdapter(
    val context: Context,
    private val categoryList: List<GetAllCategoryResponse.Datum>,
) :
    RecyclerView.Adapter<FoodVarietiesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.food_varieties_layout, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val modelView = categoryList[position]
        val url = modelView.image
        Glide.with(context).load(url).into(holder.restaurantImg)
        holder.foodText.text = modelView.name
        holder.restaurantDisName.text = modelView.deliveryTimeRange.toString()
        holder.itemView.setOnClickListener {
            val intent = Intent(context, FavoriteCuisinesActivity::class.java)
            FoodPorter.encryptedPrefs.categoryId = modelView.id!!.toInt()
            intent.putExtra("categoryItemName", modelView.name)
            intent.putExtra("distance", modelView.deliveryTimeRange.toString())
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {

        return categoryList.size

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restaurantImg: ImageView = itemView.findViewById(R.id.restaurantImg)
        val foodText: TextView = itemView.findViewById(R.id.restuarntName)
        val restaurantDisName: TextView = itemView.findViewById(R.id.restaurantDisName)

    }
}