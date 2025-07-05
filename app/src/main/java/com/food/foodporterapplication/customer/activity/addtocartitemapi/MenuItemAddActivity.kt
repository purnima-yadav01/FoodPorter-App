package com.food.foodporterapplication.customer.activity.addtocartitemapi

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.activity.cartitemdetailpageapi.CardItemDetailPageActivity
import com.food.foodporterapplication.customer.activity.addtocartitemapi.model.AddToCartModelView
import com.food.foodporterapplication.customer.adapter.AddToItemAdapter
import com.food.foodporterapplication.customer.model.AddToItemModel
import com.food.foodporterapplication.databinding.ActivityMenuItemAddBinding

class MenuItemAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuItemAddBinding
    private val addToCartModelView: AddToCartModelView by viewModels()
    private lateinit var adapter: AddToItemAdapter
    private var clickValue = "1"
    private var dishesId: Int = 0
    private var dishName = ""
    private var dishPrice = ""
    private var itemDescription = ""
    private lateinit var activity: Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuItemAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dishesId = intent.getIntExtra("selectedItemId", 0)
        dishName = intent.getStringExtra("selectedItemName").toString()
        dishPrice = intent.getStringExtra("selectedItemPrice").toString()
        itemDescription = intent.getStringExtra("selectedItemDes").toString()

        Log.e("description", itemDescription)

        val imageUrl = intent.getStringExtra("selectedItemImage")
        Glide.with(this).load(imageUrl).into(binding.itemMainImage)

        binding.foodItemName.text = dishName
        binding.itemPriceText.text = dishPrice
        binding.aboutItem.text = itemDescription

        binding.closeImg.setOnClickListener {
            finish()
        }

        binding.incrementImage.setOnClickListener {

        }

        binding.addToCartBtn.setOnClickListener {
            val i = Intent(this@MenuItemAddActivity, CardItemDetailPageActivity::class.java)
            startActivity(i)
        }

        if (clickValue == "1") {
            binding.addInstructionLayout.setOnClickListener {
                val isVisible = binding.addInstructionsLayout.visibility == View.VISIBLE

                binding.addInstructionsLayout.visibility =
                    if (isVisible) View.GONE else View.VISIBLE
                binding.removeOrderConstaint.visibility = if (isVisible) View.GONE else View.VISIBLE
            }

        } else {
            binding.addInstructionsLayout.visibility = View.GONE
            binding.removeOrderConstaint.visibility = View.GONE
        }

        val data = listOf(
            AddToItemModel(R.drawable.favorite_cuisines, "PizzaExpress(MOKO)"),
            AddToItemModel(R.drawable.best_offer2, "Maxi Slice Pizza"),
            AddToItemModel(R.drawable.burger, "PizzaExpress(MOKO)"),
            AddToItemModel(R.drawable.favorite_cuisines, "Maxi Slice Pizza"),
            AddToItemModel(R.drawable.burger, "PizzaExpress(MOKO)")

        )

        adapter = AddToItemAdapter(this, data)
        binding.ItemListRecyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.ItemListRecyclerview.adapter = adapter

    }
}