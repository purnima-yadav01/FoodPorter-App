package com.food.foodporterapplication.customer.activity.addcategoryitemdeatils

interface OnAddItemClickListener {
    fun onAddItemClicked(itemId: Int, image: String, itemName: String, itemPrice: String, description:String)
}