package com.food.foodporterapplication.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.model.MenuOfferModel

class MenuOfferAdapter (val context: Context, private val offerList: List<MenuOfferModel>) :
    RecyclerView.Adapter<MenuOfferAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuOfferAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_offer_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuOfferAdapter.ViewHolder, position: Int) {

        val modelView = offerList[position]
        holder.offerImg.setImageResource(modelView.image)
    }

    override fun getItemCount(): Int {
        return offerList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val offerImg: ImageView = itemView.findViewById(R.id.offerImg)
    }
}