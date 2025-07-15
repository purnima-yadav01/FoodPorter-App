package com.food.foodporterapplication.customer.activity.addcategoryitemdeatils

import AddonItemAdapter
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cremation.funeralcremation.utils.ErrorUtil
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.MenuItemDetailActivity.Companion.quantity
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.addonmeal.AddOnMealModelView
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.model.AddCategoryItemModelView
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.model.AddCategoryItemResponse
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.updatecartapi.UpdateQuantityBody
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.updatecartapi.UpdateQuantityModelView
import com.food.foodporterapplication.customer.activity.addtocartitemapi.model.AddToCartItemBody
import com.food.foodporterapplication.customer.activity.addtocartitemapi.model.AddToCartModelView
import com.food.foodporterapplication.customer.activity.cartitemdetailpageapi.CardItemDetailPageActivity
import com.food.foodporterapplication.customer.adapter.AddToCartItemAdapter
import com.food.foodporterapplication.customer.adapter.MenuOfferAdapter
import com.food.foodporterapplication.customer.application.FoodPorter
import com.food.foodporterapplication.customer.model.CustomizationItem
import com.food.foodporterapplication.customer.model.MenuOfferModel
import com.food.foodporterapplication.databinding.ActivityCategoryByRestDishesBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class CategoryByRestDishesActivity : AppCompatActivity(), OnAddItemClickListener,
    OnAddOnItemListener, OnUpdateQuantityListener {

    private lateinit var binding: ActivityCategoryByRestDishesBinding
    private val addCategoryItemModelView: AddCategoryItemModelView by viewModels()
    private val updateQuantityModelView: UpdateQuantityModelView by viewModels()
    private val addToCartModelView: AddToCartModelView by viewModels()
    private val addOnMealModelView: AddOnMealModelView by viewModels()

    private var addCategoryItemList: List<AddCategoryItemResponse.Datum> = ArrayList()
    private var addToCartItemAdapter: AddToCartItemAdapter? = null
    private var addonItemAdapter: AddonItemAdapter? = null
    private val selectedAddonsMap = mutableMapOf<Int, Int>()
    private var currentPosition = 0
    private val selectedAddonIds = mutableListOf<Int>()
    private var restaurantId: Int = 0
    private var categoryId: Int = 0
    private var dishesId: Int = 0
    private var restaurantName = ""
    private var restaurantTime = ""
    private var itemPerPrice = 0.0
    private var selectedItemImage = ""
    private var selectedItemName = ""
    private var selectedItemPrice = ""
    private var restaurantAddress = ""
    private var itemDescription = ""
    private var selectedAddonTotal = 0.0
    private var baseItemTotal = 0.0
    private var restaurantRating = 0.0
    private var dialog: BottomSheetDialog? = null

    private lateinit var activity: CategoryByRestDishesActivity
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryByRestDishesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this
        categoryId = FoodPorter.encryptedPrefs.categoryId
        restaurantId = FoodPorter.encryptedPrefs.restaurantId
        restaurantName = intent.getStringExtra("restaurantName").toString()
        restaurantAddress = intent.getStringExtra("restaurantAddress").toString()
        restaurantTime = intent.getStringExtra("restaurantTime").toString()
        restaurantRating = intent.getDoubleExtra("restaurantRating", 0.0)

        binding.textOffer.text = String.format("%.1f", restaurantRating)
        binding.dishesRestarantName.text = restaurantName
        binding.restuarntName.text = restaurantName
        binding.distanceText.text = "$restaurantTime: $restaurantAddress"

        binding.imaBackMain.setOnClickListener { finish() }

        binding.itemAddedConst.setOnClickListener {
            val i = Intent(this, CardItemDetailPageActivity::class.java)
            i.putExtra("selectedItemId", dishesId)
            i.putExtra("selectedItemImage", selectedItemImage)
            i.putExtra("selectedItemName", selectedItemName)
            i.putExtra("selectedItemPrice", selectedItemPrice)
            i.putExtra("selectedItemDes", itemDescription)
            startActivity(i)
        }

        getCategoryDetailsApi()
        updateQuantityObserver()
        getCategoryDetailsObserver()
        addToCartItemObserver()

        val data = listOf(
            MenuOfferModel(R.drawable.gift),
            MenuOfferModel(R.drawable.gift),
            MenuOfferModel(R.drawable.gift),
            MenuOfferModel(R.drawable.gift),
            MenuOfferModel(R.drawable.gift)
        )

        binding.menuOfferRecyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.menuOfferRecyclerview.adapter = MenuOfferAdapter(this, data)
        startAutoScroll(data.size)
    }

    private fun startAutoScroll(itemCount: Int) {
        runnable = object : Runnable {
            override fun run() {
                if (currentPosition == itemCount) currentPosition = 0
                binding.menuOfferRecyclerview.smoothScrollToPosition(currentPosition++)
                handler.postDelayed(this, 2000)
            }
        }
        handler.postDelayed(runnable, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }

    private fun getCategoryDetailsApi() {
        addCategoryItemModelView.getCategoryItemUser(activity, categoryId, restaurantId)
    }

    private fun getCategoryDetailsObserver() {
        addCategoryItemModelView.mAddCategoryItemResponse.observe(this) {
            val response = it.peekContent()
            if (response.success == true) {
                addCategoryItemList = response.data ?: emptyList()
                binding.offerDealTextview.text = response.deal ?: ""
                addToCartItemAdapter = AddToCartItemAdapter(this, addCategoryItemList, this, this)
                binding.menuItemAddRecyclerview.layoutManager = LinearLayoutManager(this)
                binding.menuItemAddRecyclerview.adapter = addToCartItemAdapter
                updateBottomCartUI()
            } else {
                Toast.makeText(this, response.message ?: "Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onAddItemClicked(itemId: Int, image: String, itemName: String, itemPrice: String, description: String) {
        dishesId = itemId
        selectedItemImage = image
        selectedItemName = itemName
        selectedItemPrice = itemPrice
        itemDescription = description
        showCustomizationDialog(this, image, itemName, itemPrice, description)
    }

    private fun showCustomizationDialog(
        context: Context,
        image: String,
        itemName: String,
        itemPrice: String,
        description: String,
    ) {
        dialog = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
        val view = LayoutInflater.from(context).inflate(R.layout.item_added_dialog, null)
        dialog!!.setContentView(view)

        val bottomSheet = dialog!!.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.layoutParams?.height = (context.resources.displayMetrics.heightPixels * 0.8).toInt()
        dialog!!.behavior.peekHeight = (context.resources.displayMetrics.heightPixels * 0.8).toInt()

        val itemImage = view.findViewById<ImageView>(R.id.itemImg)
        val itemTextView = view.findViewById<TextView>(R.id.ItemNameTextview)
        val addItemPrice = view.findViewById<TextView>(R.id.addItemPrice)
        val minusLayout = view.findViewById<LinearLayout>(R.id.minusLayout)
        val quantityTextItemView = view.findViewById<TextView>(R.id.quantityTextItemView)
        val addLayout = view.findViewById<LinearLayout>(R.id.addLayout)
        val closeLayout = view.findViewById<LinearLayout>(R.id.closeLayout)
        val itemAddedRecyclerview = view.findViewById<RecyclerView>(R.id.itemAddedRecyclerview)
        val addItemsPriceConst = view.findViewById<ConstraintLayout>(R.id.addItemsPriceConst)

        itemTextView.text = itemName
        Glide.with(context).load(image).into(itemImage)

        val cleanPrice = itemPrice.replace(",", "").replace(Regex("[^\\d.]"), "")
        itemPerPrice = cleanPrice.toDoubleOrNull() ?: 0.0
        quantity = 1

        // ✅ Clear previous data
        selectedAddonsMap.clear()
        selectedAddonIds.clear()

        baseItemTotal = itemPerPrice * quantity

        fun updateTotalPriceInDialog() {
            selectedAddonTotal = (selectedAddonsMap.values.sum() * quantity).toDouble()
            val total = baseItemTotal + selectedAddonTotal
            addItemPrice.text = "Rs.${"%.2f".format(total)}"
        }

        quantityTextItemView.text = quantity.toString()
        updateTotalPriceInDialog()

        fun updateQuantity() {
            quantityTextItemView.text = quantity.toString()
            baseItemTotal = itemPerPrice * quantity
            updateTotalPriceInDialog()

            if (quantity > 0) {
                binding.itemAddedConst.visibility = View.VISIBLE
                binding.itemAddedQuantityText.text = quantity.toString()
            } else {
                binding.itemAddedConst.visibility = View.GONE
            }
        }

        addLayout.setOnClickListener {
            quantity++
            updateQuantity()
        }

        minusLayout.setOnClickListener {
            if (quantity > 1) {
                quantity--
                updateQuantity()
            }
        }

        closeLayout.setOnClickListener {
            dialog?.dismiss()
        }

        addItemsPriceConst.setOnClickListener {
            val selectedAddons = selectedAddonIds.toList()
            val requestBody = AddToCartItemBody(
                dish_id = dishesId,
                quantity = quantity,
                addons_ids = selectedAddons
            )
            addToCartModelView.addToCartUser(this@CategoryByRestDishesActivity, requestBody)
            dialog?.dismiss()

        }

        addOnMealModelView.updateAddUser(activity = this, id = dishesId)

        addOnMealModelView.mAddOnMealResponse.observe(this) { event ->
            val response = event.peekContent()
            if (response.success == true) {
                val addonItemList = response.data?.addonItems ?: emptyList()
                val finalList = mutableListOf<CustomizationItem>()

                addonItemList.forEach { addonItem ->
                    finalList.add(
                        CustomizationItem.SectionHeader(
                            title = addonItem.name ?: "",
                            subtitle = "Select up to ${addonItem.maxSelection}",
                            maxSelect = addonItem.maxSelection ?: 0
                        )
                    )

                    addonItem.addons?.forEach { addon ->
                        val priceString = addon.price ?: "0"
                        val cleanedPrice = priceString.replace(Regex("[^\\d]"), "")
                        val priceInt = cleanedPrice.toIntOrNull() ?: 0

                        finalList.add(
                            CustomizationItem.OptionItem(
                                id = addon.id ?: 0,
                                name = addon.name ?: "",
                                price = priceInt
                            )
                        )
                    }
                }

                itemAddedRecyclerview.layoutManager = LinearLayoutManager(context)
                itemAddedRecyclerview.adapter = AddonItemAdapter(finalList, object : OnAddOnItemListener {
                    override fun onItemCheckedChanged(price: Int, isChecked: Boolean, itemId: Int) {
                        if (isChecked) {
                            selectedAddonsMap[itemId] = price
                            if (!selectedAddonIds.contains(itemId)) {
                                selectedAddonIds.add(itemId)
                            }
                        } else {
                            selectedAddonsMap.remove(itemId)
                            selectedAddonIds.remove(itemId)
                        }
                        updateTotalPriceInDialog()
                    }
                })
            } else {
                Toast.makeText(context, response.message ?: "Failed to load Addons", Toast.LENGTH_SHORT).show()
            }
        }

        addOnMealModelView.errorResponse.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }

        dialog!!.show()
    }

    override fun onItemCheckedChanged(price: Int, isChecked: Boolean, itemId: Int) {
        if (isChecked) {
            selectedAddonsMap[itemId] = price

        } else {
            selectedAddonsMap.remove(itemId)
        }

        updateTotalPriceInDialog()
    }

    private fun updateTotalPriceInDialog() {
        selectedAddonTotal = (selectedAddonsMap.values.sum() * quantity).toDouble()
        val total = baseItemTotal + selectedAddonTotal
        dialog?.findViewById<TextView>(R.id.addItemPrice)?.text = "Rs.${"%.2f".format(total)}"
    }

    override fun quantityClick(position: Int, itemId: Int, quantity: Int) {
        val updateBody = UpdateQuantityBody(
            dish_id = itemId.toString(),
            quantity = quantity.toString()
        )
        updateQuantityModelView.updateQuantityUser(this, updateBody)
    }

    private fun updateQuantityObserver() {
        updateQuantityModelView.mUpdateQuantityResponse.observe(this) {
            val response = it.peekContent()
            if (response.success == true) {
                Toast.makeText(this, "Quantity updated successfully!", Toast.LENGTH_SHORT).show()
                getCategoryDetailsApi()
            } else {
                Toast.makeText(this, response.message ?: "Failed to update quantity", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addToCartItemObserver() {
        addToCartModelView.mAddToCartResponse.observe(this) {
            val response = it.peekContent()
            if (response.success == true) {
                Toast.makeText(this, response.message ?: "Item added to cart!", Toast.LENGTH_SHORT).show()
                updateBottomCartUI()
                getCategoryDetailsApi()
            } else {
                Toast.makeText(this, response.message ?: "Failed to add item", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateBottomCartUI() {
        val totalQty = addCategoryItemList.sumOf { it.quantity ?: 0 }
        if (totalQty > 0) {
            binding.itemAddedConst.visibility = View.VISIBLE
            binding.itemAddedQuantityText.text = totalQty.toString()
        } else {
            binding.itemAddedConst.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        if (addCategoryItemList.isNotEmpty()) updateBottomCartUI()
    }
}

