package com.food.foodporterapplication.customer.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.foodporterapplication.customer.adapter.OrderListAdapter
import com.food.foodporterapplication.customer.model.OrderListModel
import com.food.foodporterapplication.databinding.FragmentMyOrderBinding

class MyOrderFragment : Fragment() {
    private lateinit var binding: FragmentMyOrderBinding
    private lateinit var adapter: OrderListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentMyOrderBinding.inflate(layoutInflater,container,false)

        val data = listOf(
            OrderListModel(),
            OrderListModel(),
            OrderListModel(),
            OrderListModel(),
            OrderListModel(),

            )

        adapter = OrderListAdapter(requireContext(), data)
        binding.myOrderRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.myOrderRecycler.adapter = adapter

        return binding.root

    }
}