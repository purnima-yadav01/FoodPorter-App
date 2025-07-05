package com.food.foodporterapplication.customer.model

sealed class CustomizationItem {

    data class SectionHeader(
        val title: String,
        val subtitle: String,
        val maxSelect: Int
    ) : CustomizationItem()

    data class OptionItem(
        val name: String,
        val price: Int,
        var isSelected: Boolean = false
    ) : CustomizationItem()
}
