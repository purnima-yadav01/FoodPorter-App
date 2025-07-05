package com.food.foodporterapplication.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.activity.addnewaddressapi.OnDeleteAddressListener
import com.food.foodporterapplication.customer.activity.addnewaddressapi.UpdateAddressListener
import com.food.foodporterapplication.customer.activity.addnewaddressapi.savedaddressapi.SavedAddressResponse

class AddAddressAdapter(
    val context: Context, private val addressList: MutableList<SavedAddressResponse.Datum>,
    private val deleteListener: OnDeleteAddressListener, private val updateListener: UpdateAddressListener,
) :
    RecyclerView.Adapter<AddAddressAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AddAddressAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.add_address_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddAddressAdapter.ViewHolder, position: Int) {
        val modelView = addressList[position]
        val id = modelView.id

        val fullAddress = buildList {
            addIfValid(modelView.street?.toString())
            addIfValid(modelView.buildingName?.toString())
            addIfValid(modelView.addressLine)
            addIfValid(modelView.landmark)
            addIfValid(modelView.city)
            addIfValid(modelView.state)
            addIfValid(modelView.pincode)
        }.joinToString(", ")

        val addressTypeText = when (modelView.type?.uppercase()?.trim()){
            "HOME" ->  "Home"
            "WORK" -> "Work"
            "OTHER" -> "Other"
            else -> ""
        }

        holder.addressTypeText.text = addressTypeText
        holder.addressText.text = fullAddress
        holder.countryCodeText.text = modelView.countryCode ?: ""
        holder.phoneNumberText.text = modelView.phone ?: ""

        holder.deleteLayout.setOnClickListener {
            if (id != null) {
                deleteListener.onDeleteClick(position, id)
            }
        }

        holder.editAddressLayout.setOnClickListener {
            if (id!= null){
                updateListener.updateAddressClick(position, id)
            }
        }
    }

    override fun getItemCount(): Int = addressList.size

    fun removeItem(position: Int) {
        addressList.removeAt(position)
        notifyItemRemoved(position)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addressText: TextView = itemView.findViewById(R.id.addAddressText)
        val countryCodeText: TextView = itemView.findViewById(R.id.countryCodeText)
        val phoneNumberText: TextView = itemView.findViewById(R.id.phoneNumberText)
        val deleteLayout: LinearLayout = itemView.findViewById(R.id.deleteLayout)
        val editAddressLayout: LinearLayout = itemView.findViewById(R.id.editAddressLayout)
        val addressTypeText: TextView = itemView.findViewById(R.id.addressTypeText)
    }

    // ✅ Helper extension to avoid null/blank/"null"
    private fun MutableList<String>.addIfValid(value: String?) {
        if (!value.isNullOrBlank() && value.lowercase() != "null") {
            add(value.trim())
        }
    }
}