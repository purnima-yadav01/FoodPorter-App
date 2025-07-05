package com.food.foodporterapplication.driver.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.food.foodporterapplication.databinding.ActivityDriverUserDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DriverUserDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDriverUserDashboardBinding
    private var navItemIndex = 0
    private val TAG_DASH_BOARD = "dashboard"
    private var CURRENT_TAG = TAG_DASH_BOARD
    private val TAG_NEXT = "next"
    private val FOOD_FRAGMENT_TAG = "HomeFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDriverUserDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleBackPress()
            }
        })



        if (savedInstanceState == null) {
            navItemIndex = 1
            CURRENT_TAG = TAG_NEXT

            binding.homeIcon.setImageResource(R.drawable.home_fill)
            binding.earningIconUsers.setImageResource(R.drawable.earnings_border)
            binding.supportIconUsers.setImageResource(R.drawable.support_border)
            binding.profileIconUser.setImageResource(R.drawable.profile_border)

            binding.driverHomeUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.earningTxtUSer.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.supportTextUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.profileTextViewUser.setTextColor(ContextCompat.getColor(this, R.color.white))

            showDriverHomeFragment()
        }

        binding.homeFragmentUser.setOnClickListener {

            binding.homeIcon.setImageResource(R.drawable.home_fill)
            binding.earningIconUsers.setImageResource(R.drawable.earnings_border)
            binding.supportIconUsers.setImageResource(R.drawable.support_border)
            binding.profileIconUser.setImageResource(R.drawable.profile_border)

            binding.driverHomeUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.earningTxtUSer.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.supportTextUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.profileTextViewUser.setTextColor(ContextCompat.getColor(this, R.color.white))

            replaceFragment(HomeFragment(), true)
        }

        binding.earningFragmentUser.setOnClickListener {
            binding.bottomMainConst.visibility = View.GONE

            binding.homeIcon.setImageResource(R.drawable.home)
            binding.earningIconUsers.setImageResource(R.drawable.earnings_fill)
            binding.supportIconUsers.setImageResource(R.drawable.support_border)
            binding.profileIconUser.setImageResource(R.drawable.profile_border)

            binding.driverHomeUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.earningTxtUSer.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.supportTextUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.profileTextViewUser.setTextColor(ContextCompat.getColor(this, R.color.white))

            replaceFragment(MyOrderFragment(), true)
        }

        binding.supportFragmentUser.setOnClickListener {
            binding.bottomMainConst.visibility = View.VISIBLE

            binding.homeIcon.setImageResource(R.drawable.home)
            binding.earningIconUsers.setImageResource(R.drawable.myorder_border)
            binding.supportIconUsers.setImageResource(R.drawable.collaboration_fill)
            binding.profileIconUser.setImageResource(R.drawable.profile_border)

            binding.driverHomeUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.earningTxtUSer.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.supportTextUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.profileTextViewUser.setTextColor(ContextCompat.getColor(this, R.color.white))

            replaceFragment(SearchFragment(), true)
        }

        binding.profileFragmentUser.setOnClickListener {

            binding.homeIcon.setImageResource(R.drawable.home)
            binding.earningIconUsers.setImageResource(R.drawable.myorder_border)
            binding.supportIconUsers.setImageResource(R.drawable.collaboration_fill)
            binding.profileIconUser.setImageResource(R.drawable.img_2)

            binding.driverHomeUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.earningTxtUSer.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.supportTextUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.profileTextViewUser.setTextColor(ContextCompat.getColor(this, R.color.white))

            replaceFragment(AccountFragment(), true)
        }
    }

    private fun showDriverHomeFragment() {
        val homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction().replace(
            R.id.userFrameLayout, homeFragment, FOOD_FRAGMENT_TAG
        ).commit()
    }

    private fun handleBackPress() {
        if (navItemIndex != 1) {
            navItemIndex = 1
            CURRENT_TAG = TAG_NEXT

            binding.homeIcon.setImageResource(R.drawable.home_fill)
            binding.earningIconUsers.setImageResource(R.drawable.earnings_border)
            binding.supportIconUsers.setImageResource(R.drawable.support_border)
            binding.profileIconUser.setImageResource(R.drawable.profile_border)

            binding.driverHomeUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.earningTxtUSer.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.supportTextUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.profileTextViewUser.setTextColor(ContextCompat.getColor(this, R.color.white))

            showDriverHomeFragment()

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
    }*/
    }
}
