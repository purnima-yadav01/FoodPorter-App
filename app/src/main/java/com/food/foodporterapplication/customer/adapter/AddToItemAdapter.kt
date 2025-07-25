package com.food.foodporterapplication.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.model.AddToItemModel

class AddToItemAdapter (val context: Context, private val offerList: List<AddToItemModel>) :
    RecyclerView.Adapter<AddToItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddToItemAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.add_to_items_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddToItemAdapter.ViewHolder, position: Int) {

        val modelView = offerList[position]
        holder.itemImg.setImageResource(modelView.image)
        holder.itemTextview.text = modelView.itemName


    }

    override fun getItemCount(): Int {
        return offerList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImg: ImageView = itemView.findViewById(R.id.itemImg)
        val itemTextview: TextView = itemView.findViewById(R.id.ItemTextview)
    }

}