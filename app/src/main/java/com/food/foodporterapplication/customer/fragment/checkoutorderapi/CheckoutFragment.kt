package com.food.foodporterapplication.customer.fragment.checkoutorderapi

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.food.foodporterapplication.customer.activity.applycouponsapi.AppyVoucherActivity
import com.food.foodporterapplication.customer.activity.searchbyrestanddihses.CustomerUserDashboardActivity
import com.food.foodporterapplication.customer.adapter.PaymentMethodAdapter
import com.food.foodporterapplication.customer.fragment.checkoutorderapi.model.CheckoutOrderBody
import com.food.foodporterapplication.customer.fragment.checkoutorderapi.model.CheckoutOrderModelView
import com.food.foodporterapplication.databinding.FragmentCheckoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutFragment : Fragment() {
    private lateinit var binding: FragmentCheckoutBinding
    private lateinit var adapter: PaymentMethodAdapter
    private val checkoutOrderModelView: CheckoutOrderModelView by viewModels()
    private var listener: OnCheckoutClickListener? = null
    private lateinit var activity: Activity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentCheckoutBinding.inflate(inflater, container, false)


        binding.applyVoucherText.setOnClickListener {
            val i = Intent(requireContext(), AppyVoucherActivity::class.java)
            startActivity(i)
        }

        binding.checkoutBackBtn.setOnClickListener {
            listener?.onCheckoutBackButtonListener()
        }

        binding.checkoutBtn.setOnClickListener {
            checkoutOrderApi()
        }

        checkoutOrderObserver()

        activity = requireActivity()
        return binding.root
    }

    interface OnCheckoutClickListener {
        fun onCheckoutBackButtonListener()
    }

    private fun checkoutOrderApi() {
        val body = CheckoutOrderBody(
            address = "raj home pg",
            payment_method = "COD",
            cupon_code = "null"
        )
        checkoutOrderModelView.checkoutOrder(requireActivity(), body)

    }

    private fun checkoutOrderObserver() {
        checkoutOrderModelView.mCheckoutOrderResponse.observe(viewLifecycleOwner) {
            val success = it.peekContent().success
            val message = it.peekContent().message
            if (success == true) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                val intent = Intent(requireContext(), CustomerUserDashboardActivity::class.java)
                startActivity(intent)
            } else {

            }
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as OnCheckoutClickListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement OnNextButtonClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


}