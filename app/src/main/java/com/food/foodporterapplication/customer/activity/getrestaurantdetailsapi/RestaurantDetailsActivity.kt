package com.food.foodporterapplication.customer.activity.getrestaurantdetailsapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cremation.funeralcremation.utils.ErrorUtil
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.adapter.MenuListAdapter
import com.food.foodporterapplication.customer.adapter.RestaurantItemOfferAdapter
import com.food.foodporterapplication.customer.adapter.RestaurantItemsAdapter
import com.food.foodporterapplication.customer.model.MenuListModel
import com.food.foodporterapplication.customer.model.RestaurantItemOfferModel
import com.food.foodporterapplication.databinding.ActivityRestaurantDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantDetailsBinding
    private lateinit var raestaurantItemOfferAdapter: RestaurantItemOfferAdapter
    private val restaurantDetailModelView: RestaurantDetailModelView by viewModels()
    private var restaurantDishesList: List<RestaurantDetailResponse.Datum> = ArrayList()
    private var adapter: RestaurantItemsAdapter? = null
    private var currentPosition = 0
    private lateinit var activity: RestaurantDetailsActivity
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private var restaurantId: Int = 0
    private var restaurantName = ""
    private var restaurantImage = ""
    private var delivereyTime = ""
    private var restaurantAddress = ""
    private var menuDialog: BottomSheetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRestaurantDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this
       restaurantId = intent.getIntExtra("restaurantId", 0)
        restaurantName = intent.getStringExtra("restaurantName").toString()
        restaurantAddress = intent.getStringExtra("restaurantAddress").toString()
        delivereyTime = intent.getStringExtra("delivereyTime").toString()

        binding.restaurentName.text = restaurantName
        binding.distanceText.text = delivereyTime

        val imageUrl = intent.getStringExtra("restaurantImage")
        Glide.with(this).load(imageUrl).into(binding.restaurantImg)
        binding.locationText.text = restaurantAddress

       getRestaurantDetailsApi()
       getRestaurantDetailsObserver()

        binding.closeImg.setOnClickListener {
            finish()
        }

        binding.menuConst.setOnClickListener {
            if (menuDialog?.isShowing == true) {
                menuDialog?.dismiss()
                binding.menuText.text = "Menu"

            } else {
                showMenuBottomSheet()
                binding.menuText.text = "Cancel"
            }
        }

        val data = listOf(
            RestaurantItemOfferModel(R.drawable.offer, "flat Rs 125 OFF above Rs 299"),
            RestaurantItemOfferModel(R.drawable.gift, "Free deliver"),
            RestaurantItemOfferModel(R.drawable.offer, "flat Rs 125 OFF above Rs 299"),
            RestaurantItemOfferModel(R.drawable.gift, "Free deliver"),
            RestaurantItemOfferModel(R.drawable.offer, "flat Rs 125 OFF above Rs 29)")

        )

        raestaurantItemOfferAdapter = RestaurantItemOfferAdapter(this, data)
        binding.offerRecyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.offerRecyclerview.adapter = raestaurantItemOfferAdapter

        startAutoScroll(data.size)

    }

    // get restaurant detail api
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
                    binding.restaurantItemRecycle.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    binding.restaurantItemRecycle.adapter = adapter

                } else {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            restaurantDetailModelView.errorResponse.observe(this){
                ErrorUtil.handlerGeneralError(this, it)
            }
        }
    }

    private fun showMenuBottomSheet() {
        val view = layoutInflater.inflate(R.layout.menu_list_dialog, null)
        val menuListRecyclerview = view.findViewById<RecyclerView>(R.id.menuListRecyclerview)

        val menuItems = listOf(
            MenuListModel("Special Offer starting @ ₹59 only", "110"),
            MenuListModel("Recommended for you", "25"),
            MenuListModel("Korean Spicy Fest (Limited Time Only)", "17"),
            MenuListModel("Burgers, Wraps & Tacos", "30"),
            MenuListModel("Sides & Snacks", "31"),
            MenuListModel("BK Café (Coffee & Shakes)", "15")
        )

        val adapter = MenuListAdapter(this, menuItems)
        menuListRecyclerview.layoutManager = LinearLayoutManager(this)
        menuListRecyclerview.adapter = adapter

        menuDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        menuDialog?.setContentView(view)

        menuDialog?.setOnDismissListener {
            binding.menuText.text = "Menu"
        }

        menuDialog?.show()
    }

    private fun startAutoScroll(itemCount: Int) {
        val scrollRunnable = object : Runnable {
            override fun run() {
                if (currentPosition < itemCount) {

                    binding.offerRecyclerview.smoothScrollToPosition(currentPosition)
                    currentPosition++

                } else {

                    currentPosition = 0
                    binding.offerRecyclerview.smoothScrollToPosition(currentPosition)
                }
                handler.postDelayed(this, 2000)
            }
        }

        handler.post(scrollRunnable)
    }
}

