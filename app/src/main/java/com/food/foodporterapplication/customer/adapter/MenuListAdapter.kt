package com.food.foodporterapplication.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.model.MenuListModel

class MenuListAdapter (val context: Context, private val offerList: List<MenuListModel>) :
    RecyclerView.Adapter<MenuListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuListAdapter.ViewHolder, position: Int) {
        val modelView = offerList[position]
        holder.menuName.text = modelView.ItemName
        holder.quantityText.text = modelView.quantity

    }

    override fun getItemCount(): Int {

        return offerList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuName: TextView = itemView.findViewById(R.id.menuName)
        val quantityText: TextView = itemView.findViewById(R.id.quantityText)

    }
}