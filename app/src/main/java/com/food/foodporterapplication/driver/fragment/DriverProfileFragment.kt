package com.food.foodporterapplication.driver.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.food.foodporterapplication.customer.activity.*
import com.food.foodporterapplication.customer.activity.profileapi.ViewProfileActivity
import com.food.foodporterapplication.databinding.FragmentDriverProfileBinding

class DriverProfileFragment : Fragment() {
    private lateinit var binding: FragmentDriverProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentDriverProfileBinding.inflate(layoutInflater,container, false)


        binding.viewProfileYText.setOnClickListener {
            val intent = Intent(requireContext(), ViewProfileActivity::class.java)
            startActivity(intent)
        }

        binding.settingIcon.setOnClickListener {
            val intent = Intent(requireContext(), SettingActivity::class.java)
            startActivity(intent)
        }

        binding.earningConstraint.setOnClickListener {
            val intent = Intent(requireContext(), PaymentMethodActivity::class.java)
            startActivity(intent)
        }

        binding.ratingConstraint.setOnClickListener {
            val intent = Intent(requireContext(), FavoriteFoodActivity::class.java)
            startActivity(intent)
        }


        return binding.root
    }


}