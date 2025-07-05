package com.food.foodporterapplication.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.model.RestaurantItemOfferModel

class RestaurantItemOfferAdapter (val context: Context, private val offerList: List<RestaurantItemOfferModel>) :
    RecyclerView.Adapter<RestaurantItemOfferAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantItemOfferAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_item_offer_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantItemOfferAdapter.ViewHolder, position: Int) {

        val modelView = offerList[position]
        holder.offerImage.setImageResource(modelView.image)
        holder.offerText.text = modelView.text
        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return offerList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val offerImage: ImageView = itemView.findViewById(R.id.flatOffersImg)
        val offerText: TextView = itemView.findViewById(R.id.offerText)
    }
}