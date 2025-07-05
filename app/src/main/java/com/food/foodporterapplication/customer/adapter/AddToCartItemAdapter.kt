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
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.OnQuantityListener
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.model.AddCategoryItemResponse
import com.food.foodporterapplication.customer.application.FoodPorter


class AddToCartItemAdapter(
    val context: Context,
    private val categoryItemList: List<AddCategoryItemResponse.Datum>,
    private val listener: OnAddItemClickListener,
    private val quantityUpdate: OnQuantityListener
) : RecyclerView.Adapter<AddToCartItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_add_to_card_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modelView = categoryItemList[position]
        val id = modelView.id ?: return

        val quantity = FoodPorter.quantityEncryptedPrefs.getQuantity(id)

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
            FoodPorter.quantityEncryptedPrefs.setQuantity(id, 1)
            notifyItemChanged(position)
            listener.onAddItemClicked(
                id,
                modelView.image ?: "",
                modelView.name ?: "",
                modelView.price ?: "",
                modelView.description ?: ""
            )
            quantityUpdate.quantityClick(id, 1)
        }

        holder.incrementImage.setOnClickListener {
            val newQty = quantity + 1
            FoodPorter.quantityEncryptedPrefs.setQuantity(id, newQty)
            notifyItemChanged(position)
            quantityUpdate.quantityClick(id, newQty)
        }

        holder.decrementImages.setOnClickListener {
            val newQty = quantity - 1
            if (newQty <= 0) {
                FoodPorter.quantityEncryptedPrefs.removeQuantity(id)
            } else {
                FoodPorter.quantityEncryptedPrefs.setQuantity(id, newQty)
            }
            notifyItemChanged(position)
            quantityUpdate.quantityClick(id, newQty)
        }
    }

    override fun getItemCount(): Int = categoryItemList.size

    fun updateItemQuantity(itemId: Int, quantity: Int) {
        FoodPorter.quantityEncryptedPrefs.setQuantity(itemId, quantity)
        val index = categoryItemList.indexOfFirst { it.id == itemId }
        if (index != -1) notifyItemChanged(index)
    }

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

