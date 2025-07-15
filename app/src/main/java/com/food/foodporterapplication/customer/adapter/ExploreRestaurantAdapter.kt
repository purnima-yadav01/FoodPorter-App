package com.food.foodporterapplication.customer.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.activity.RestaurantDishesActivity
import com.food.foodporterapplication.customer.activity.getrestaurantdetailsapi.RestaurantDetailsActivity
import com.food.foodporterapplication.customer.fragment.homepage.model.GetAllRestaurantResponse

class ExploreRestaurantAdapter(
    val context: Context,
    private val exploreRestaurantList: List<GetAllRestaurantResponse.Datum>,
) :
    RecyclerView.Adapter<ExploreRestaurantAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ):ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.explore_restaurant_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {

        val modelView = exploreRestaurantList[position]
        val url = modelView.image
        Glide.with(context).load(url).into(holder.restaurantImg)
        Log.e("GlideDebug", "Image URL: $url")
        holder.restuarntName.text = modelView.name
        holder.delivereyTime.text = modelView.delivereyTime
        holder.offerText.text = modelView.offer
        holder.ratingTextPoint.text = modelView.rating.toString()
        holder.itemView.setOnClickListener {
            val i = Intent(context, RestaurantDetailsActivity::class.java)
            i.putExtra("restaurantId", modelView.id)
            i.putExtra("restaurantName", modelView.name)
            i.putExtra("restaurantImage", modelView.image)
            i.putExtra("delivereyTime", modelView.delivereyTime)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        Log.d("ExploreAdapter", "Total restaurants: ${exploreRestaurantList.size}")
        return exploreRestaurantList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restaurantImg: ImageView = itemView.findViewById(R.id.restaurantImg)
        val restuarntName: TextView = itemView.findViewById(R.id.restuarntName)
        val ratingTextPoint: TextView = itemView.findViewById(R.id.ratingTextPoint)
        val delivereyTime: TextView = itemView.findViewById(R.id.delivereyTime)
        val offerText: TextView = itemView.findViewById(R.id.offerText)

    }
}