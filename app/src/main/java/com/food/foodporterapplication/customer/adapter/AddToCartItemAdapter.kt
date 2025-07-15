package com.food.foodporterapplication.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.OnAddItemClickListener
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.OnUpdateQuantityListener
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.model.AddCategoryItemResponse
import com.food.foodporterapplication.customer.application.FoodPorter

class AddToCartItemAdapter(
    val context: Context,
    private val categoryItemList: List<AddCategoryItemResponse.Datum>,
    private val listener: OnAddItemClickListener,
    private val quantityUpdate: OnUpdateQuantityListener
) : RecyclerView.Adapter<AddToCartItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_add_to_card_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modelView = categoryItemList[position]
        val id = modelView.id ?: return
        val quantity = modelView.quantity ?: 0

        Glide.with(context).load(modelView.image).into(holder.itemImg)
        holder.foodItemName.text = modelView.name
        holder.aboutItemText.text = modelView.description
        holder.itemPriceText.text = modelView.price
        holder.flatOfferText.text = modelView.offer
        holder.ratingTextPoint.text = modelView.rating.toString()
        holder.quantityTextItemView.text = quantity.toString()

        if (quantity > 0) {
            holder.addItemsPriceConst.visibility = View.VISIBLE
            holder.addItemText.visibility = View.GONE
        } else {
            holder.addItemText.visibility = View.VISIBLE
            holder.addItemsPriceConst.visibility = View.GONE
        }

        holder.addItemText.setOnClickListener {
            listener.onAddItemClicked(
                id,
                modelView.image ?: "",
                modelView.name ?: "",
                modelView.price ?: "",
                modelView.description ?: ""
            )
        }

        holder.incrementImage.setOnClickListener {
            quantityUpdate.quantityClick(position, id, quantity + 1)
        }

        holder.decrementImages.setOnClickListener {
            if (quantity > 1) {
                quantityUpdate.quantityClick(position, id, quantity - 1)
            } else {
                quantityUpdate.quantityClick(position, id, 0)
            }
        }
    }

    override fun getItemCount(): Int = categoryItemList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodItemName: TextView = itemView.findViewById(R.id.foodItemName)
        val itemImg: ImageView = itemView.findViewById(R.id.itemImg)
        val aboutItemText: TextView = itemView.findViewById(R.id.aboutItemText)
        val itemPriceText: TextView = itemView.findViewById(R.id.itemPriceText)
        val ratingTextPoint: TextView = itemView.findViewById(R.id.ratingTextPoint)
        val flatOfferText: TextView = itemView.findViewById(R.id.flatOfferText)
        val addItemText: TextView = itemView.findViewById(R.id.addItemText)
        val addItemsPriceConst: ConstraintLayout = itemView.findViewById(R.id.addItemsPriceConst)
        val decrementImages: ImageView = itemView.findViewById(R.id.decrementImage)
        val quantityTextItemView: TextView = itemView.findViewById(R.id.itemQuantityText)
        val incrementImage: ImageView = itemView.findViewById(R.id.incrementImage)
    }
}

