package com.food.foodporterapplication.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.model.BestOfferModel

class BestOfferAdapter(val context: Context, private val offerList: List<BestOfferModel>) :
    RecyclerView.Adapter<BestOfferAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestOfferAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.best_offers_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BestOfferAdapter.ViewHolder, position: Int) {

        val modelView = offerList[position]
        holder.bestOfferImg.setImageResource(modelView.image)

    }

    override fun getItemCount(): Int {
        return offerList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bestOfferImg: ImageView = itemView.findViewById(R.id.bestOfferImg)

    }
}