package com.food.foodporterapplication.customer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.foodporterapplication.customer.activity.addnewaddressapi.OnDeleteAddressListener
import com.food.foodporterapplication.customer.activity.addnewaddressapi.UpdateAddressListener
import com.food.foodporterapplication.customer.activity.addnewaddressapi.savedaddressapi.SavedAddressModelView
import com.food.foodporterapplication.customer.activity.addnewaddressapi.savedaddressapi.SavedAddressResponse
import com.food.foodporterapplication.customer.adapter.*
import com.food.foodporterapplication.customer.model.AddAddressModel
import com.food.foodporterapplication.databinding.ActivityAddAddressBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAddressActivity : AppCompatActivity(), OnDeleteAddressListener, UpdateAddressListener {
    private lateinit var binding: ActivityAddAddressBinding
    private val savedAddressModelView: SavedAddressModelView by viewModels()
    private var addressList: MutableList<SavedAddressResponse.Datum> = ArrayList()
    private lateinit var activity: AddAddressActivity
    private var adapter: AddAddressAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this // initialize activity

        binding.imaBackMain.setOnClickListener {
            finish()
        }

        getAddressList()
        getAddressObserver()
    }

    private fun getAddressList() {
        savedAddressModelView.savedAddUser(activity)
    }

    private fun getAddressObserver() {
        savedAddressModelView.mSavedAddressResponse.observe(this) {
            val response = it.peekContent()
            val success = response.success
            val message = response.message

            addressList = (response.data ?: emptyList()).toMutableList()

            adapter = AddAddressAdapter(this, addressList, this, this)
            binding.addAddressRecycler.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.addAddressRecycler.adapter = adapter
        }
    }

    override fun onDeleteClick(position: Int, deleteAddressId: Int) {

    }

    override fun updateAddressClick(position: Int, updateAddressId: Int) {

    }
}
