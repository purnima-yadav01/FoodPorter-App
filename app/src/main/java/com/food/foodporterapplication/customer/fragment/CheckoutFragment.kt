package com.food.foodporterapplication.customer.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.foodporterapplication.customer.activity.applycouponsapi.AppyVoucherActivity
import com.food.foodporterapplication.customer.adapter.PaymentMethodAdapter
import com.food.foodporterapplication.customer.model.PaymentMethodModel
import com.food.foodporterapplication.databinding.FragmentCheckoutBinding

class CheckoutFragment : Fragment() {
    private lateinit var binding: FragmentCheckoutBinding
    private lateinit var adapter: PaymentMethodAdapter
    private var listener: OnCheckoutClickListener? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentCheckoutBinding.inflate(inflater, container, false)

        binding.verifyPhoneBtn.setOnClickListener {
            listener?.onCheckoutButtonListener()
        }
        binding.appyVoucherText.setOnClickListener {
            val i = Intent(requireContext(), AppyVoucherActivity::class.java)
            startActivity(i)
        }

        val data = listOf(
            PaymentMethodModel(),
            PaymentMethodModel(),
            PaymentMethodModel(),


        )

        adapter = PaymentMethodAdapter(requireContext(), data)
        binding.paymentMethodRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.paymentMethodRecyclerview.adapter = adapter

        return binding.root
    }

    interface OnCheckoutClickListener {
        fun onCheckoutButtonListener()
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