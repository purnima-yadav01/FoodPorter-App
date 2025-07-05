package com.food.foodporterapplication.customer.fragment.homepage

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.cremation.funeralcremation.utils.ErrorUtil
import com.food.foodporterapplication.customer.adapter.*
import com.food.foodporterapplication.customer.classes.CustomProgressDialog
import com.food.foodporterapplication.customer.fragment.homepage.getallcategory.GetAllCategoryModelView
import com.food.foodporterapplication.customer.fragment.homepage.getallcategory.GetAllCategoryResponse
import com.food.foodporterapplication.customer.fragment.homepage.getfilterapi.GetFilterListModelView
import com.food.foodporterapplication.customer.fragment.homepage.getfilterapi.GetFilterListResponse
import com.food.foodporterapplication.customer.fragment.homepage.getfilteritemlistapi.GetFilterItemListModelView
import com.food.foodporterapplication.customer.fragment.homepage.getfilteritemlistapi.GetFilterItemListResponse
import com.food.foodporterapplication.customer.fragment.homepage.model.GetAllRestaurantModelView
import com.food.foodporterapplication.customer.fragment.homepage.model.GetAllRestaurantResponse
import com.food.foodporterapplication.databinding.FragmentFoodBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentFoodBinding
    private val getAllRestaurantModelView: GetAllRestaurantModelView by viewModels()
    private val getGetAllCategoryModelView: GetAllCategoryModelView by viewModels()
    private val getFilterItemListModelView: GetFilterItemListModelView by viewModels()
    private val getFilterListModelView: GetFilterListModelView by viewModels()
    private val progressDialog by lazy { CustomProgressDialog(requireContext()) }
    private var allRestaurantList: List<GetAllRestaurantResponse.Datum> = ArrayList()
    private var allCategoryList: List<GetAllCategoryResponse.Datum> = ArrayList()
    private var getFilterList: List<GetFilterListResponse.Filter> = ArrayList()
    private var getFilterItemList: List<GetFilterItemListResponse.Filter> = ArrayList()
    private var exploreRestaurantAdapter: ExploreRestaurantAdapter? = null
    private var offerAdapter: OfferAdapter? = null
    private var foodAdapter: FoodVarietiesAdapter? = null
    private var TrendingOfferAdapter: TrendingOfferAdapter? = null
    private lateinit var activity: Activity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentFoodBinding.inflate(inflater, container, false)

        getFilterItemObserver()
        getFilterItemList()
        getFilterList()
        getFilterObserver()
        getAllRestaurantListApi()
        getAllCategoryListApi()
        getAllRestaurantListObserver()
        getAllCategoryListObserver()

        activity = requireActivity()
        return binding.root

    }

    //get all restaurant api
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
                    allRestaurantList = response.data!!

                    if (success == true) {
                        exploreRestaurantAdapter =
                            ExploreRestaurantAdapter(requireContext(), allRestaurantList)
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
                    requireContext(),
                    "Something went wrong: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        getAllRestaurantModelView.errorResponse.observe(viewLifecycleOwner) { error ->
            ErrorUtil.handlerGeneralError(requireContext(), error)
        }

    }

    //all category  list api
    private fun getAllCategoryListApi() {

        getGetAllCategoryModelView.getAllCategory(requireActivity())

    }

    private fun getAllCategoryListObserver() {
        getGetAllCategoryModelView.progressIndicator.observe(viewLifecycleOwner, Observer {

        })

        getGetAllCategoryModelView.mAllCategoryResponse.observe(viewLifecycleOwner) { event ->

            try {

                event.getContentIfNotHandled()?.let { response ->
                    val message = response.message
                    val success = response.success
                    allCategoryList = response.data!!

                    if (success == true) {
                        foodAdapter = FoodVarietiesAdapter(requireContext(), allCategoryList)
                        binding.foodVarietiesRecyclerview.layoutManager = LinearLayoutManager(
                            requireContext(), LinearLayoutManager.HORIZONTAL, false
                        )

                        binding.foodVarietiesRecyclerview.adapter = foodAdapter
                    } else {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) { e.printStackTrace()
                Toast.makeText(
                    requireContext(),
                    "Something went wrong:${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        getGetAllCategoryModelView.errorResponse.observe(viewLifecycleOwner) { error ->
            ErrorUtil.handlerGeneralError(requireContext(), error)
        }


    }

    //get filter list api
    private fun getFilterList() {

        getFilterListModelView.getFilterUser(requireActivity())
    }

    private fun getFilterObserver() {
        getFilterListModelView.mGetFilterListResponse.observe(viewLifecycleOwner) {
            val message = it.peekContent().message
            val success = it.peekContent().success
            getFilterList = it.peekContent().filters!!

            try {
                if (success == true) {
                    offerAdapter = OfferAdapter(requireContext(), getFilterList)
                    binding.foodOfferRecyclerview.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    binding.foodOfferRecyclerview.adapter = offerAdapter
                } else {

                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            getFilterListModelView.errorResponse.observe(viewLifecycleOwner) {
                ErrorUtil.handlerGeneralError(requireContext(), it)
            }
        }
    }

    //get filter item list api
    private fun getFilterItemList() {

        getFilterItemListModelView.getFilterItemUser(requireActivity())

    }

    private fun getFilterItemObserver() {
        getFilterItemListModelView.mGetFilterListResponse.observe(viewLifecycleOwner) {
            val message = it.peekContent().message
            val success = it.peekContent().success
            getFilterItemList = it.peekContent().filters!!

            try {
                if (success == true) {
                    TrendingOfferAdapter = TrendingOfferAdapter(requireContext(), getFilterItemList)
                    binding.trendingOfferRecyclerview.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    binding.trendingOfferRecyclerview.adapter = TrendingOfferAdapter
                } else {

                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            getFilterItemListModelView.errorResponse.observe(viewLifecycleOwner) {
                ErrorUtil.handlerGeneralError(requireContext(), it)
            }
        }
    }
}