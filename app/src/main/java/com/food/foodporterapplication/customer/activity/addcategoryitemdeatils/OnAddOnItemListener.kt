package com.food.foodporterapplication.customer.activity.addcategoryitemdeatils

interface OnAddOnItemListener {
    fun onItemCheckedChanged(price: Int, isChecked: Boolean, id: Int)
}