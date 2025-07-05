package com.food.foodporterapplication.driver.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.food.foodporterapplication.databinding.FragmentDriverEarningBinding

class DriverEarningFragment : Fragment() {
    private lateinit var binding: FragmentDriverEarningBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDriverEarningBinding.inflate(layoutInflater, container, false)

        return binding.root
    }
}