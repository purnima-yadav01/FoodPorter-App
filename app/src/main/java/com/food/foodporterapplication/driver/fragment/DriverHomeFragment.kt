package com.food.foodporterapplication.driver.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.food.foodporterapplication.databinding.FragmentDriverHomeBinding
import com.food.foodporterapplication.driver.adapter.DriverHomePageAdapter

class DriverHomeFragment : Fragment() {
   private lateinit var binding: FragmentDriverHomeBinding
    private lateinit var DriverHomePageAdapter: DriverHomePageAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

       binding = FragmentDriverHomeBinding.inflate(layoutInflater, container, false)

    /*    val data = listOf(
            DriverHomePageModel("Rohan yadav", "123 Street, New Delhi","Picked up"),
            DriverHomePageModel("Rohan yadav", "123 Street, New Delhi","Picked up"),
            DriverHomePageModel("Rohan yadav", "123 Street, New Delhi","Picked up"),
            DriverHomePageModel("Rohan yadav", "123 Street, New Delhi","Picked up"),
            DriverHomePageModel("Rohan yadav", "123 Street, New Delhi","Picked up"),

        )

        DriverHomePageAdapter = DriverHomePageAdapter(requireContext(), data)
        binding.recyclerDeliveries.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerDeliveries.adapter = DriverHomePageAdapter*/

        return binding.root

    }
}