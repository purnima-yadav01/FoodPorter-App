package com.food.foodporterapplication.customer.activity.searchbyrestanddihses

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.activity.cartitemdetailpageapi.CardItemDetailPageActivity
import com.food.foodporterapplication.customer.activity.FavoriteFoodActivity
import com.food.foodporterapplication.customer.activity.addnewaddressapi.savedaddressapi.SavedAddressModelView
import com.food.foodporterapplication.customer.activity.addnewaddressapi.savedaddressapi.SavedAddressResponse
import com.food.foodporterapplication.customer.activity.searchbyrestanddihses.model.SearchByRestAndDishesModelView
import com.food.foodporterapplication.customer.bottomsheet.AddBottomSheet
import com.food.foodporterapplication.customer.fragment.AccountFragment
import com.food.foodporterapplication.customer.fragment.homepage.HomeFragment
import com.food.foodporterapplication.customer.fragment.myorderlist.MyOrderFragment
import com.food.foodporterapplication.customer.fragment.SearchFragment
import com.food.foodporterapplication.databinding.ActivityCustomerUserDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerUserDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerUserDashboardBinding
    private val searchByRestAndDishesModelView: SearchByRestAndDishesModelView by viewModels()
    private val savedAddressModelView: SavedAddressModelView by viewModels()
    private var navItemIndex = 0
    private val TAG_DASH_BOARD = "dashboard"
    private var CURRENT_TAG = TAG_DASH_BOARD
    private val TAG_NEXT = "next"
    private val FOOD_FRAGMENT_TAG = "FoodFragment"
    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCustomerUserDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.favoriteIcon.setOnClickListener {
            val intent = Intent(this@CustomerUserDashboardActivity, FavoriteFoodActivity::class.java)
            startActivity(intent)

        }

        getSearchItemObserver()

        binding.cartIcon.setOnClickListener {
            val intent = Intent(this@CustomerUserDashboardActivity, CardItemDetailPageActivity::class.java)
            startActivity(intent)
        }

        binding.edtSearch.setOnClickListener {
            getSearchItemApi()
        }

        getSavedAddressObserver()

        binding.locationConst.setOnClickListener {
            savedAddressModelView.savedAddUser(this) // Call API
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleBackPress()
            }
        })

        if (savedInstanceState == null) {
            navItemIndex = 1
            CURRENT_TAG = TAG_NEXT

            binding.bottomMainConst.visibility = View.VISIBLE

            binding.foodIconUser.setImageResource(R.drawable.home_fill)
            binding.myOrderIconUsers.setImageResource(R.drawable.checklist)
            binding.searchIconUsers.setImageResource(R.drawable.search_icon)
            binding.accountIconUser.setImageResource(R.drawable.profile_border)

            binding.myOrderTxtUSer.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.foodTextUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.searchTextUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.accountTextViewUser.setTextColor(ContextCompat.getColor(this, R.color.white))

            binding.edtSearch.text = Editable.Factory.getInstance().newEditable("")
            binding.edtSearch.clearFocus()

            showHomeFragment()
        }

        binding.foodFragmentUser.setOnClickListener {
            binding.bottomMainConst.visibility = View.VISIBLE

            binding.foodIconUser.setImageResource(R.drawable.home_fill)
            binding.myOrderIconUsers.setImageResource(R.drawable.checklist)
            binding.searchIconUsers.setImageResource(R.drawable.search_icon)
            binding.accountIconUser.setImageResource(R.drawable.profile_border)

            binding.myOrderTxtUSer.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.foodTextUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.searchTextUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.accountTextViewUser.setTextColor(ContextCompat.getColor(this, R.color.white))

            replaceFragment(HomeFragment(), true)
        }

        binding.myOrderFragmentUser.setOnClickListener {
            binding.bottomMainConst.visibility = View.GONE

            binding.foodIconUser.setImageResource(R.drawable.home)
            binding.myOrderIconUsers.setImageResource(R.drawable.checklist_fill)
            binding.searchIconUsers.setImageResource(R.drawable.search_icon)
            binding.accountIconUser.setImageResource(R.drawable.profile_border)

            binding.myOrderTxtUSer.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.foodTextUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.searchTextUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.accountTextViewUser.setTextColor(ContextCompat.getColor(this, R.color.white))

            replaceFragment(MyOrderFragment(), true)
        }

        binding.searchFragmentUser.setOnClickListener {
            binding.bottomMainConst.visibility = View.VISIBLE

            binding.foodIconUser.setImageResource(R.drawable.home)
            binding.myOrderIconUsers.setImageResource(R.drawable.checklist)
            binding.searchIconUsers.setImageResource(R.drawable.search_fill)
            binding.accountIconUser.setImageResource(R.drawable.profile_border)

            binding.myOrderTxtUSer.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.foodTextUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.searchTextUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.accountTextViewUser.setTextColor(ContextCompat.getColor(this, R.color.white))

            replaceFragment(SearchFragment(), true)
        }

        binding.accountFragmentUser.setOnClickListener {
            binding.bottomMainConst.visibility = View.GONE

            binding.foodIconUser.setImageResource(R.drawable.home)
            binding.myOrderIconUsers.setImageResource(R.drawable.checklist)
            binding.searchIconUsers.setImageResource(R.drawable.search_icon)
            binding.accountIconUser.setImageResource(R.drawable.img_2)

            binding.myOrderTxtUSer.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.foodTextUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.searchTextUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.accountTextViewUser.setTextColor(ContextCompat.getColor(this, R.color.white))

            replaceFragment(AccountFragment(), true)
        }
    }

    private fun getSavedAddressObserver() {
        savedAddressModelView.progressIndicator.observe(this) {

        }

        savedAddressModelView.mSavedAddressResponse.observe(this) { event ->
            val response = event.getContentIfNotHandled() ?: return@observe
            if (response.success == true) {
                val addressList = response.data ?: emptyList()
                val bottomSheet = AddBottomSheet(addressList as MutableList<SavedAddressResponse.Datum>)
                bottomSheet.show(supportFragmentManager, bottomSheet.tag)
            } else {
                Toast.makeText(this, response.message ?: "No address found", Toast.LENGTH_SHORT).show()
            }
        }

        savedAddressModelView.errorResponse.observe(this) { error ->
            Toast.makeText(this, "Error: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSearchItemApi() {
        val q = binding.edtSearch.text.toString().trim()
        if (q.isNotEmpty()) {
            //searchByRestAndDishesModelView.searchItemUser( )
        } else {
            Toast.makeText(this, "Please enter a search keyword", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSearchItemObserver() {

    }

    private fun showHomeFragment() {
        val homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction().replace(
            R.id.userFrameLayout, homeFragment, FOOD_FRAGMENT_TAG
        ).commit()
    }

    private fun handleBackPress() {
        if (navItemIndex != 1) {
            navItemIndex = 1
            CURRENT_TAG = TAG_NEXT

            binding.bottomMainConst.visibility = View.VISIBLE
            binding.foodIconUser.setImageResource(R.drawable.home_fill)
            binding.myOrderIconUsers.setImageResource(R.drawable.myorder_border)
            binding.searchIconUsers.setImageResource(R.drawable.search_icon)
            binding.accountIconUser.setImageResource(R.drawable.profile_border)

            binding.myOrderTxtUSer.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.foodTextUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.searchTextUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.accountTextViewUser.setTextColor(ContextCompat.getColor(this, R.color.white))

            showHomeFragment()

        } else {

            finish()

        }
    }

    private fun replaceFragment(fragment: Fragment, addToBackStack: Boolean) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.userFrameLayout, fragment)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null)
        }

        fragmentTransaction.commit()
    }
}