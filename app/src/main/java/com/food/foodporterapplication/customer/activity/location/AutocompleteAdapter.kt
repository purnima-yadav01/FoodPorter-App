package com.food.foodporterapplication.customer.activity.location

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.food.foodporterapplication.R
import com.google.android.libraries.places.api.model.AutocompletePrediction

class AutocompleteAdapter (
    private val context: Context,
    private val predictions: List<AutocompletePrediction>,
    private val onItemClick: (AutocompletePrediction) -> Unit
) : RecyclerView.Adapter<AutocompleteAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeNameTextView: TextView = view.findViewById(R.id.placeName)
        val placeAddressTextView: TextView = view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_autocomplete, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val prediction = predictions[position]
        holder.placeNameTextView.text = prediction.getPrimaryText(null)
        holder.placeAddressTextView.text = prediction.getSecondaryText(null)

        holder.itemView.setOnClickListener {
            onItemClick(prediction)
        }
    }

    override fun getItemCount(): Int {
        return predictions.size
    }
}