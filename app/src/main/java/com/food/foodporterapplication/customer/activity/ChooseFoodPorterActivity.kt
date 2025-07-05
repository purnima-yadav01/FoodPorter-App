package com.food.foodporterapplication.customer.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.activity.loginwithphonemunberapi.CustomerLoginActivity
import com.food.foodporterapplication.customer.activity.searchbyrestanddihses.CustomerUserDashboardActivity
import com.food.foodporterapplication.databinding.ActivityChooseFoodPorterBinding
import com.food.foodporterapplication.driver.activity.DriverUserDashboardActivity

class ChooseFoodPorterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseFoodPorterBinding
    private var foodPorterList = ArrayList<String>()
    private var selectFood = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChooseFoodPorterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.nextBtn.setOnClickListener {
            val intent = Intent(this@ChooseFoodPorterActivity, CustomerLoginActivity::class.java)
            startActivity(intent)
        }

        foodPorterList.add("Select your portal")
        foodPorterList.add("Customer")
        foodPorterList.add("Driver")
        foodPorterList.add("Restaurant")

        val portalAdapter = SpinnerAdapter(this, R.layout.custom_spinner_layout, foodPorterList)
        portalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.foodPorterSpinner.adapter = portalAdapter

        binding.foodPorterSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    selectFood = foodPorterList[position]
                    Log.e("SpinnerItem", "Selected Item: $selectFood")

                    when(selectFood){
                        "Customer" ->{
                            val intent = Intent(this@ChooseFoodPorterActivity, CustomerUserDashboardActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        "Driver" ->{
                            val intent = Intent(this@ChooseFoodPorterActivity, DriverUserDashboardActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        "Restaurant" ->{

                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
    }

    // Custom Spinner Adapter
    class SpinnerAdapter(context: Context, textViewResourceId: Int, items: List<String>) :
        ArrayAdapter<String>(context, textViewResourceId, items) {

        override fun getCount(): Int {
            return super.getCount()
        }
    }
}