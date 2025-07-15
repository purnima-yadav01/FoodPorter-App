package com.food.foodporterapplication.customer.fragment.cartItemDetail.model

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cremation.funeralcremation.utils.ErrorUtil
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.MenuItemDetailActivity
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.OnUpdateQuantityListener
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.model.AddCategoryItemModelView
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.model.AddCategoryItemResponse
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.updatecartapi.UpdateQuantityBody
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.updatecartapi.UpdateQuantityModelView
import com.food.foodporterapplication.customer.adapter.CardItemDetailAdapter
import com.food.foodporterapplication.customer.adapter.CompleteYourMealAdapter
import com.food.foodporterapplication.customer.application.FoodPorter
import com.food.foodporterapplication.databinding.FragmentCartItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartItemFragment : Fragment(), OnUpdateQuantityListener {
    private lateinit var binding: FragmentCartItemBinding
    private val cartItemDetailModelView: CartItemDetailModelView by viewModels()
    private val addCategoryItemModelView: AddCategoryItemModelView by viewModels()
    private val updateQuantityModelView: UpdateQuantityModelView by viewModels()
    private var cartUserList: List<CardItemDetailResponse.Item> = ArrayList()
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
            listener?.onCartNextButtonClickListener()

        }

        binding.addInstructionLayout.setOnClickListener {
            val intent = Intent(requireContext(), MenuItemDetailActivity::class.java)
            startActivity(intent)
        }

        getCartItemDetailApi()
        completeYourMealApi()
        completeYourMealObserver()
        updateQuantityObserver()
        getCartItemObserver()

        return binding.root
    }

    interface OnCartItemClickListener {
        fun onCartNextButtonClickListener()

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
            val response = it.peekContent()
            if (response.success == true && response.cart?.items != null) {
                val cartList = response.cart?.items!!

                // Adapter setup with real-time price listener
                adapter = CardItemDetailAdapter(requireContext(), cartList, this) { subtotal, total ->
                    binding.subtotalPriceText.text = "Rs %.2f".format(subtotal)
                    binding.deliveryOfferText.text = "Rs ${response.cart?.standardDelivery}"
                    binding.totalPriceText.text = "Rs $total"
                }

                binding.addToItemRecyclerview.layoutManager =
                    LinearLayoutManager(requireContext())
                binding.addToItemRecyclerview.adapter = adapter

                // Set initial prices
                val subtotal = cartList.sumOf {
                    val price = it.dishPrice?.toDoubleOrNull() ?: 0.0
                    val qty = it.quantity ?: 1
                    price * qty
                }
                val total = cartList.sumOf {
                    it.totalPrice ?: ((it.dishPrice?.toDoubleOrNull() ?: 0.0) * (it.quantity ?: 1)).toInt()
                }

                binding.subtotalPriceText.text = "Rs %.2f".format(subtotal)
                binding.deliveryOfferText.text = "Rs ${response.cart?.standardDelivery}"
                binding.totalPriceText.text = "Rs $total"

            } else {

                Toast.makeText(requireContext(), response.message ?: "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }

        cartItemDetailModelView.errorResponse.observe(viewLifecycleOwner) {
            ErrorUtil.handlerGeneralError(requireContext(), it)
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

    override fun quantityClick(position: Int, itemId: Int, quantity: Int) {
        val updateBody = UpdateQuantityBody(
            dish_id = itemId.toString(),
            quantity = quantity.toString()
        )
        updateQuantityModelView.updateQuantityUser(requireActivity(), updateBody)
    }

    private fun updateQuantityObserver() {
        updateQuantityModelView.mUpdateQuantityResponse.observe(this) {
            val response = it.peekContent()
            if (response.success == true) {
                Toast.makeText(requireContext(), "Quantity updated successfully!", Toast.LENGTH_SHORT).show()
                getCartItemDetailApi()
            } else {
                Toast.makeText(requireContext(), response.message ?: "Failed to update quantity", Toast.LENGTH_SHORT).show()
            }
        }
    }
}