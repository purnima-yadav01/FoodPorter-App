package com.food.foodporterapplication.customer.activity.addcategoryitemdeatils

import android.icu.text.Transliterator.Position

interface OnUpdateQuantityListener {
    fun quantityClick(position: Int, itemId: Int, quantity: Int)
}