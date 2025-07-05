package com.food.foodporterapplication.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.model.MyFavoriteModel

class MyFavoriteAdapter(val context: Context, private val favoriteList: List<MyFavoriteModel>) :
    RecyclerView.Adapter<MyFavoriteAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MyFavoriteAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_favorite_dishes_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyFavoriteAdapter.ViewHolder, position: Int) {

        val modelView = favoriteList[position]
        holder.foodImage.setImageResource(modelView.image)
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodImage: ImageView = itemView.findViewById(R.id.foodImage)

    }
}