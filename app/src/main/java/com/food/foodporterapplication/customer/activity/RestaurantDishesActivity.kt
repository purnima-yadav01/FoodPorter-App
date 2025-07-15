package com.food.foodporterapplication.customer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.cremation.funeralcremation.utils.ErrorUtil
import com.food.foodporterapplication.customer.activity.getrestaurantdetailsapi.RestaurantDetailModelView
import com.food.foodporterapplication.customer.activity.getrestaurantdetailsapi.RestaurantDetailResponse
import com.food.foodporterapplication.customer.activity.getrestaurantdetailsapi.RestaurantDetailsActivity
import com.food.foodporterapplication.customer.adapter.*
import com.food.foodporterapplication.customer.fragment.homepage.getfilteritemlistapi.GetFilterItemListModelView
import com.food.foodporterapplication.customer.fragment.homepage.getfilteritemlistapi.GetFilterItemListResponse
import com.food.foodporterapplication.customer.model.FilterComboModel
import com.food.foodporterapplication.customer.model.FilterDishesModel
import com.food.foodporterapplication.customer.model.PaymentMethodModel
import com.food.foodporterapplication.customer.model.RestaurantDishesModel
import com.food.foodporterapplication.databinding.ActivityRestaurantDishesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantDishesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantDishesBinding
    private lateinit var raestaurantItemOfferAdapter: RestaurantItemOfferAdapter
    private val restaurantDetailModelView: RestaurantDetailModelView by viewModels()
    private val getFilterItemListModelView: GetFilterItemListModelView by viewModels()
    private var restaurantDishesList: List<RestaurantDetailResponse.Datum> = ArrayList()
    private var getFilterItemList: List<GetFilterItemListResponse.Filter> = ArrayList()
    private var adapter: FilterDishesAdapter? = null
    private var restaurantItemsAdapter: RestaurantItemsAdapter? = null
    private var filterComboAdapter: FilterComboAdapter? = null
    private var trendingOfferAdapter: TrendingOfferAdapter? = null
    private var currentPosition = 0
    private lateinit var activity: RestaurantDishesActivity
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private var restaurantId: Int = 0
    private var restaurantName = ""
    private var restaurantImage = ""
    private var delivereyTime = ""
    private var restaurantAddress = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRestaurantDishesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this

        binding.imgBackArrow.setOnClickListener {
            finish()
        }


        val data = listOf(
            FilterDishesModel(),
            FilterDishesModel(),
            FilterDishesModel(),
            FilterDishesModel(),
            FilterDishesModel(),

            )

        adapter = FilterDishesAdapter(this, data )
        binding.filterNameRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.filterNameRecycler.adapter = adapter

       /* val data1 = listOf(
            RestaurantDishesModel(),
            RestaurantDishesModel(),
            RestaurantDishesModel(),
            RestaurantDishesModel(),
            RestaurantDishesModel(),

            )

        restaurantItemsAdapter = RestaurantItemsAdapter(this, data1)
        binding.popularRecycler.layoutManager =
            GridLayoutManager(this,2)
        binding.popularRecycler.adapter = restaurantItemsAdapter
*/

        val data2 = listOf(
            FilterComboModel(),
            FilterComboModel(),
            FilterComboModel(),
            FilterComboModel(),
            FilterComboModel(),

            )

        filterComboAdapter = FilterComboAdapter(this, data2)
        binding.comboRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.comboRecycler.adapter = filterComboAdapter


        //  restaurantId = intent.getIntExtra("restaurantId", 0)
      //  restaurantName = intent.getStringExtra("restaurantName").toString()
      //  restaurantAddress = intent.getStringExtra("restaurantAddress").toString()
      //  delivereyTime = intent.getStringExtra("delivereyTime").toString()

    ///    binding.restNameText.text = restaurantName
    //    binding.restuarntName.text = restaurantName
     //   binding.distanceText.text = delivereyTime

        //val imageUrl = intent.getStringExtra("restaurantImage")
        // binding.locationText.text = restaurantAddress

      //  getRestaurantDetailsApi()
      //  getFilterItemList()
       // getRestaurantDetailsObserver()
      //  getFilterItemObserver()

    }

    // filter dishes
  /*  private fun getFilterItemList() {
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
                    binding.filterRecyclerview.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    binding.filterRecyclerview.adapter = trendingOfferAdapter
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        getFilterItemListModelView.errorResponse.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }

    private fun getRestaurantDetailsApi() {

        restaurantDetailModelView.getAllCategory(activity, restaurantId)
    }

    private fun getRestaurantDetailsObserver() {
        restaurantDetailModelView.mRestaurantDetailResponse.observe(this) {
            val success = it.peekContent().success
            val message = it.peekContent().message
            restaurantDishesList = it.peekContent().data!!

            try {
                if (success == true) {
                    adapter = RestaurantItemsAdapter(this, restaurantDishesList)
                    binding.filterListDihsesRecycler.layoutManager =
                        GridLayoutManager(this, 2)
                    binding.filterListDihsesRecycler.adapter = adapter

                } else {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        restaurantDetailModelView.errorResponse.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }*/
}