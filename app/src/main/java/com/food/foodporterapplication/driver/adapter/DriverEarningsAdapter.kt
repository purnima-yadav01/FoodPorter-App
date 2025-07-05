package com.food.foodporterapplication.driver.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.food.foodporterapplication.R
import com.food.foodporterapplication.driver.model.DriverEarningsModel

class DriverEarningsAdapter (val context: Context, private val favoriteList: List<DriverEarningsModel>) :
    RecyclerView.Adapter<DriverEarningsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DriverEarningsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.driver_earing_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DriverEarningsAdapter.ViewHolder, position: Int) {

        val modelView = favoriteList[position]

    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}