package com.food.foodporterapplication.customer.fragment.cartItemDetail.model

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cremation.funeralcremation.utils.ErrorUtil
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.MenuItemDetailActivity
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.model.AddCategoryItemModelView
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.model.AddCategoryItemResponse
import com.food.foodporterapplication.customer.adapter.AddToCartItemAdapter
import com.food.foodporterapplication.customer.adapter.CardItemDetailAdapter
import com.food.foodporterapplication.customer.adapter.CompleteYourMealAdapter
import com.food.foodporterapplication.customer.application.FoodPorter
import com.food.foodporterapplication.databinding.FragmentCartItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartItemFragment : Fragment() {
    private lateinit var binding: FragmentCartItemBinding
    private val cartItemDetailModelView: CartItemDetailModelView by viewModels()
    private val addCategoryItemModelView: AddCategoryItemModelView by viewModels()
    private var cartUserList: List<CardItemDetailResponse.CartItem> = ArrayList()
    private var addCategoryItemList: List<AddCategoryItemResponse.Datum> = ArrayList()
    private var completeYourMealAdapter: CompleteYourMealAdapter? = null
    private  var adapter: CardItemDetailAdapter? = null
    private var listener: OnCartItemClickListener? = null
    private var restaurantId: Int = 0
    private var categoryId: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentCartItemBinding.inflate(inflater, container, false)

        categoryId = FoodPorter.encryptedPrefs.categoryId
        restaurantId = FoodPorter.encryptedPrefs.restaurantId

        binding.reviewBtn.setOnClickListener {
            listener?.onCartButtonClickListener()

        }

        binding.addInstructionLayout.setOnClickListener {
            val intent = Intent(requireContext(), MenuItemDetailActivity::class.java)
            startActivity(intent)
        }

        getCartItemDetailApi()
        completeYourMealApi()
        completeYourMealObserver()
        getCartItemObserver()
        return binding.root
    }

    interface OnCartItemClickListener {
        fun onCartButtonClickListener()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as OnCartItemClickListener
        } catch (e: java.lang.ClassCastException) {
            throw ClassCastException("$context  must implement OnCartItemClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

   // cartItem Api
    private fun getCartItemDetailApi() {

        cartItemDetailModelView.cartUser(requireActivity())

    }

    private fun getCartItemObserver() {
        cartItemDetailModelView.mCardItemDetailResponse.observe(viewLifecycleOwner) {
            val message = it.peekContent().message
            val success = it.peekContent().success
            cartUserList = it.peekContent().data?.cartItems ?: emptyList()
            val subtotal = it.peekContent().data?.subtotal
            val total = it.peekContent().data?.total
            val standardDelivery = it.peekContent().data?.deliveryCharge
            val carItems = it.peekContent().data?.cartItems

            binding.subtotalPriceText.text = subtotal.toString()
            binding.deliveryOfferText.text = standardDelivery.toString()
            binding.totalPriceText.text = total.toString()
            binding.subtotalPriceText.text = subtotal.toString()

            Log.e("cartapiData", "$subtotal, $total")

            try {
                if (success == true) {
                    adapter = CardItemDetailAdapter(requireContext(), cartUserList)
                    binding.addToItemRecyclerview.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    binding.addToItemRecyclerview.adapter = adapter
                } else {

                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            cartItemDetailModelView.errorResponse.observe(viewLifecycleOwner) {
                ErrorUtil.handlerGeneralError(requireContext(), it)
            }
        }
    }

    //complete meal api
    private fun completeYourMealApi() {

        addCategoryItemModelView.getCategoryItemUser(requireActivity(), categoryId, restaurantId)

    }

    private fun completeYourMealObserver() {
        addCategoryItemModelView.mAddCategoryItemResponse.observe(viewLifecycleOwner) {
            val success = it.peekContent().success
            val message = it.peekContent().message
            addCategoryItemList = it.peekContent().data!!

            try {
                if (success == true) {
                    completeYourMealAdapter = CompleteYourMealAdapter(requireContext(), addCategoryItemList)
                    binding.completeMealRecyclerview.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    binding.completeMealRecyclerview.adapter = completeYourMealAdapter

                } else {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            addCategoryItemModelView.errorResponse.observe(viewLifecycleOwner) {
                ErrorUtil.handlerGeneralError(requireContext(), it)
            }
        }
    }
}