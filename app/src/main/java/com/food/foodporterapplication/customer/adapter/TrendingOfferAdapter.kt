package com.food.foodporterapplication.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.fragment.homepage.getfilteritemlistapi.GetFilterItemListResponse

class TrendingOfferAdapter(val context: Context, private val filterItemList: List<GetFilterItemListResponse.Filter>) :
    RecyclerView.Adapter<TrendingOfferAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_offer_layout, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val modelView = filterItemList[position]
        holder.filterOfferText.text = modelView.name
    }

    override fun getItemCount(): Int {
        return filterItemList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val filterOfferText: TextView = itemView.findViewById(R.id.filterOfferText)
    }
}