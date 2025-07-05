package com.food.foodporterapplication.driver.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.food.foodporterapplication.databinding.FragmentDriverRatingBinding


class DriverRatingFragment : Fragment() {
private lateinit var binding: FragmentDriverRatingBinding
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
     binding = FragmentDriverRatingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}