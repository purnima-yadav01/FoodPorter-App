package com.food.foodporterapplication.customer.activity.categoryorderitem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cremation.funeralcremation.utils.ErrorUtil
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.activity.categoryorderitem.model.FavoriteCuisinesModelView
import com.food.foodporterapplication.customer.activity.categoryorderitem.model.FavoriteCuisinesResponse
import com.food.foodporterapplication.customer.adapter.TrendingOfferAdapter
import com.food.foodporterapplication.customer.adapter.YourFavoriteCuisinesAdapter
import com.food.foodporterapplication.customer.application.FoodPorter
import com.food.foodporterapplication.customer.fragment.homepage.getfilteritemlistapi.GetFilterItemListModelView
import com.food.foodporterapplication.customer.fragment.homepage.getfilteritemlistapi.GetFilterItemListResponse
import com.food.foodporterapplication.databinding.ActivityFavoriteCuisinesBinding
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class FavoriteCuisinesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteCuisinesBinding
    private val favoriteCuisinesModelView: FavoriteCuisinesModelView by viewModels()
    private val getFilterItemListModelView: GetFilterItemListModelView by viewModels()

    private var allCategoryItemList: List<FavoriteCuisinesResponse.Datum> = ArrayList()
    private var getFilterItemList: List<GetFilterItemListResponse.Filter> = ArrayList()
    private var adapter: YourFavoriteCuisinesAdapter? = null
    private var trendingOfferAdapter: TrendingOfferAdapter? = null
    private lateinit var activity: FavoriteCuisinesActivity
    private var categoryId: Int = 0
    private var categoryItemName = ""
    private var categoryDistance = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteCuisinesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activity = this

        // Back button
        binding.imaBackMain.setOnClickListener {
            finish()
        }

        // Setup shimmer animation
        binding.shimmerLayout.setShimmer(
            Shimmer.AlphaHighlightBuilder()
                .setDuration(1500)
                .setBaseAlpha(0.9f)
                .setHighlightAlpha(1f)
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .build()
        )

        binding.shimmerLayout.startShimmer()

        // Get data from intent
        categoryId = FoodPorter.encryptedPrefs.categoryId
        categoryItemName = intent.getStringExtra("categoryItemName").orEmpty()
        categoryDistance = intent.getStringExtra("distance").orEmpty()

        binding.foodItemName.text = categoryItemName

        getFilterItemList()
        getAllCategoryNameList()
        getAllCategoryNameObserver()
        getFilterItemObserver()

    }

    private fun getFilterItemList() {
        getFilterItemListModelView.getFilterItemUser(activity)
    }

    private fun getFilterItemObserver() {
        getFilterItemListModelView.mGetFilterListResponse.observe(this) {
            val message = it.peekContent().message
            val success = it.peekContent().success
            getFilterItemList = it.peekContent().filters ?: emptyList()

            try {
                if (success == true) {
                    trendingOfferAdapter = TrendingOfferAdapter(this, getFilterItemList)
                    binding.filterDishesRecyclerview.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    binding.filterDishesRecyclerview.adapter = trendingOfferAdapter
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        getFilterItemListModelView.errorResponse.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }

    private fun getAllCategoryNameList() {
        favoriteCuisinesModelView.getAllCategory(activity, categoryItemName)
    }

    private fun getAllCategoryNameObserver() {
        favoriteCuisinesModelView.mFavoriteCuisinesResponse.observe(this) { event ->
            val response = event.peekContent()
            val success = response.success
            val message = response.message
            val data = response.data

            if (success == true && !data.isNullOrEmpty()) {
                allCategoryItemList = data
                adapter = YourFavoriteCuisinesAdapter(this, allCategoryItemList)

                binding.cuisineRecyclerview.layoutManager = GridLayoutManager(this, 2)
                binding.cuisineRecyclerview.adapter = adapter

                // Stop shimmer and show data
                binding.shimmerLayout.stopShimmer()
                binding.shimmerLayout.visibility = View.GONE
                binding.cuisineRecyclerview.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, message ?: "Something went wrong", Toast.LENGTH_LONG).show()
            }
        }

        favoriteCuisinesModelView.errorResponse.observe(this) {
            binding.shimmerLayout.stopShimmer()
            binding.shimmerLayout.visibility = View.GONE
            binding.cuisineRecyclerview.visibility = View.GONE
            ErrorUtil.handlerGeneralError(this, it)
        }
    }
}
