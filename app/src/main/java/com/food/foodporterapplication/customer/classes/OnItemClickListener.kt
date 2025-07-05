package com.food.foodporterapplication.customer.classes

interface OnItemClickListener {
    fun onItemClick(position: Int, id: Int?)
    fun onItemClickValue(position: Int, cuisineTypee: String?)
}