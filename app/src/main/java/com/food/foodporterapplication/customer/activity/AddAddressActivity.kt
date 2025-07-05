package com.food.foodporterapplication.customer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.foodporterapplication.customer.adapter.AddAddressesAdapter
import com.food.foodporterapplication.customer.model.AddAddressModel
import com.food.foodporterapplication.databinding.ActivityAddAddressBinding

class AddAddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddAddressBinding
    private lateinit var adapter: AddAddressesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.imaBackMain.setOnClickListener {
            finish()
        }

        val addressList = listOf(
            AddAddressModel("Raj home pg room number 32, 2 floor, block M, Mamura, sector 66, Noida"),
            AddAddressModel("Raj home pg room number 32, 2 floor, block M, Mamura, sector 66, Noida"),
            AddAddressModel("Raj home pg room number 32, 2 floor, block M, Mamura, sector 66, Noida"),
            AddAddressModel("Raj home pg room number 32, 2 floor, block M, Mamura, sector 66, Noida"),
            AddAddressModel("Raj home pg room number 32, 2 floor, block M, Mamura, sector 66, Noida")
        )

        adapter = AddAddressesAdapter(this, addressList)
        binding.addAddressRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.addAddressRecycler.adapter = adapter
    }


}