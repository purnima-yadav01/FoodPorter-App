package com.food.foodporterapplication.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.fragment.homepage.getfilterapi.GetFilterListResponse

class OfferAdapter(val context: Context, private val offerList: List<GetFilterListResponse.Filter>) :
    RecyclerView.Adapter<OfferAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.food_porter_offer_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferAdapter.ViewHolder, position: Int) {

        val modelView = offerList[position]
        val imageUrl = modelView.image
        Glide.with(context).load(imageUrl).into(holder.offerImage)
        holder.offerText.text = modelView.name
        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return offerList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val offerImage: ImageView = itemView.findViewById(R.id.offerImage)
        val offerText: TextView = itemView.findViewById(R.id.offerText)
    }
}