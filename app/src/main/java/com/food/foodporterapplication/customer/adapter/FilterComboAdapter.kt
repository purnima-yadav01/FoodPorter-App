package com.food.foodporterapplication.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.model.FilterComboModel

class FilterComboAdapter (val context: Context, private val offerList: List<FilterComboModel>) :
    RecyclerView.Adapter<FilterComboAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterComboAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.filter_combo_offer_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterComboAdapter.ViewHolder, position: Int) {

        val modelView = offerList[position]
        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return offerList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val filterTextName: TextView = itemView.findViewById(R.id.filterTextName)

    }
}