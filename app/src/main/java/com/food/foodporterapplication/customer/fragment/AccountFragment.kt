package com.food.foodporterapplication.customer.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.food.foodporterapplication.customer.activity.*
import com.food.foodporterapplication.customer.activity.profileapi.ViewProfileActivity
import com.food.foodporterapplication.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentAccountBinding.inflate(layoutInflater, container, false)

        binding.viewProfileYText.setOnClickListener {
            val intent = Intent(requireContext(), ViewProfileActivity::class.java)
            startActivity(intent)
        }

        binding.addressesConst.setOnClickListener {
            val intent = Intent(requireContext(), AddAddressActivity::class.java)
            startActivity(intent)
        }

        binding.settingIcon.setOnClickListener {
            val intent = Intent(requireContext(), SettingActivity::class.java)
            startActivity(intent)
        }

        binding.orderConstraint.setOnClickListener {
            val intent = Intent(requireContext(), MyOrderActivity::class.java)
            startActivity(intent)
        }

        binding.paymentConstraint.setOnClickListener {
            val intent = Intent(requireContext(), PaymentMethodActivity::class.java)
            startActivity(intent)
        }

        binding.favoriteConstraint.setOnClickListener {
            val intent = Intent(requireContext(), FavoriteFoodActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}