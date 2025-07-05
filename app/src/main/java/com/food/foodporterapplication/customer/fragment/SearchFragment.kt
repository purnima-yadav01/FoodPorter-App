package com.food.foodporterapplication.customer.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.foodporterapplication.customer.adapter.ExploreRestaurantAdapter
import com.food.foodporterapplication.customer.classes.CustomProgressDialog
import com.food.foodporterapplication.customer.fragment.homepage.model.GetAllRestaurantModelView
import com.food.foodporterapplication.customer.fragment.homepage.model.GetAllRestaurantResponse
import com.food.foodporterapplication.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val getAllRestaurantModelView: GetAllRestaurantModelView by viewModels()
    private val progressDialog by lazy { CustomProgressDialog(requireContext()) }
    private var allRestaurantList: MutableList<GetAllRestaurantResponse.Datum> = mutableListOf()
    private var exploreRestaurantAdapter: ExploreRestaurantAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        getAllRestaurantListApi()
        getAllRestaurantListObserver()

        return binding.root
    }

    private fun getAllRestaurantListApi() {

        getAllRestaurantModelView.getAllRest(requireActivity())

    }

    private fun getAllRestaurantListObserver() {
        getAllRestaurantModelView.progressIndicator.observe(requireActivity(), Observer {

        })


        getAllRestaurantModelView.mAllRestResponse.observe(requireActivity()) { event ->
            try {

                event.getContentIfNotHandled()?.let { response ->
                    val message = response.message
                    val success = response.success
                    val data = response.data

                    if (success == true && data != null) {
                        exploreRestaurantAdapter = ExploreRestaurantAdapter(requireContext(), data)
                        binding.exploreRecyclerview.layoutManager = LinearLayoutManager(
                            requireContext(), LinearLayoutManager.VERTICAL, false
                        )
                        binding.exploreRecyclerview.adapter = exploreRestaurantAdapter
                    } else {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    requireContext(), "Something went wrong: ${e.message}", Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}