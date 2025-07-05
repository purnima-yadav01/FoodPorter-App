package com.food.foodporterapplication.customer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.adapter.MyFavoriteAdapter
import com.food.foodporterapplication.customer.model.MyFavoriteModel
import com.food.foodporterapplication.databinding.ActivityFavoriteFoodBinding

class FavoriteFoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteFoodBinding
    private lateinit var adapter: MyFavoriteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBackImg.setOnClickListener {
            finish()
        }

        val data = listOf(
            MyFavoriteModel(R.drawable.pizza),
            MyFavoriteModel(R.drawable.burger),
            MyFavoriteModel(R.drawable.best_offer2),
            MyFavoriteModel(R.drawable.best_offers1),
            MyFavoriteModel(R.drawable.restaurant_img),
            MyFavoriteModel(R.drawable.best_offers1),

            )

        adapter = MyFavoriteAdapter(this, data)
        binding.favoriteRecyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.favoriteRecyclerview.adapter = adapter

    }
}