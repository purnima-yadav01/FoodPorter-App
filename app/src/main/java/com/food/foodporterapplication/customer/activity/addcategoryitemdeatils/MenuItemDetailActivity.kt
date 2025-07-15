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
import com.food.foodporterapplication.databinding.ActivityMenuItemDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuItemDetailActivity : AppCompatActivity(), OnAddItemClickListener, OnAddOnItemListener,
    OnUpdateQuantityListener {
    private lateinit var binding: ActivityMenuItemDetailBinding
    private lateinit var adapter: MenuOfferAdapter
    private val addCategoryItemModelView: AddCategoryItemModelView by viewModels()
    private val updateQuantityModelView: UpdateQuantityModelView by viewModels()
    private val addToCartModelView: AddToCartModelView by viewModels()
    private val addOnMealModelView: AddOnMealModelView by viewModels()
    private var addCategoryItemList: List<AddCategoryItemResponse.Datum> = ArrayList()
    private var addToCartItemAdapter: AddToCartItemAdapter? = null
    private val selectedAddonsMap = mutableMapOf<Int, Int>()
    private var currentPosition = 0
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

    companion object {
        var quantity = 0
    }

    private lateinit var activity: MenuItemDetailActivity
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuItemDetailBinding.inflate(layoutInflater)
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
        // binding.addressText.text = restaurantAddress
        binding.distanceText.text = restaurantTime

        val restAddress = "${restaurantTime}: ${restaurantAddress}"

        binding.distanceText.text = restAddress

        binding.imaBackMain.setOnClickListener {
            finish()
        }

        Log.e("description", itemDescription)
        binding.itemAddedConst.setOnClickListener {
            val i = Intent(this@MenuItemDetailActivity, CardItemDetailPageActivity::class.java)
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

        adapter = MenuOfferAdapter(this, data)
        binding.menuOfferRecyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.menuOfferRecyclerview.adapter = adapter

        startAutoScroll(data.size)

    }

    //get all category item menu list api
    private fun getCategoryDetailsApi() {
        addCategoryItemModelView.getCategoryItemUser(activity, categoryId, restaurantId)
    }

    private fun getCategoryDetailsObserver() {
        addCategoryItemModelView.mAddCategoryItemResponse.observe(this) {
            val success = it.peekContent().success
            val message = it.peekContent().message
            addCategoryItemList = it.peekContent().data!!
            val specialdeal = it.peekContent().deal
            binding.offerDealTextview.text = specialdeal

            try {
                if (success == true) {
                    addToCartItemAdapter =
                        AddToCartItemAdapter(this, addCategoryItemList, this, this)
                    binding.menuItemAddRecyclerview.layoutManager = LinearLayoutManager(this)
                    binding.menuItemAddRecyclerview.adapter = addToCartItemAdapter

                    updateBottomCartUI()
                } else {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            addCategoryItemModelView.errorResponse.observe(this) {
                ErrorUtil.handlerGeneralError(this, it)
            }
        }
    }

    private fun updateBottomCartUI() {
        val totalQty = getTotalCartQuantity()
        if (totalQty > 0) {
            binding.itemAddedConst.visibility = View.VISIBLE
            binding.itemAddedQuantityText.text = totalQty.toString()
        } else {
            binding.itemAddedConst.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        if (addCategoryItemList.isNotEmpty()) {
            updateBottomCartUI()
        }
    }

    private fun getTotalCartQuantity(): Int {
        var total = 0
        for (item in addCategoryItemList) {
            val id = item.id ?: continue
            total += FoodPorter.quantityEncryptedPrefs.getQuantity(id)
        }
        return total
    }

    private fun startAutoScroll(itemCount: Int) {
        runnable = object : Runnable {
            override fun run() {
                if (currentPosition == itemCount) {
                    currentPosition = 0  // loop back to first item
                }
                binding.menuOfferRecyclerview.smoothScrollToPosition(currentPosition)
                currentPosition++
                handler.postDelayed(this, 2000)  // scroll every 3 seconds
            }
        }

        handler.postDelayed(runnable, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }

    override fun onAddItemClicked(
        itemId: Int,
        image: String,
        itemName: String,
        itemPrice: String,
        description: String,
    ) {
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

        val bottomSheet =
            dialog!!.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.layoutParams?.height =
            (context.resources.displayMetrics.heightPixels * 0.8).toInt()
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
        baseItemTotal = itemPerPrice * quantity

        selectedAddonsMap.clear()
        updateTotalPriceInDialog()

        quantityTextItemView.text = quantity.toString()
        addItemPrice.text = "Rs.${"%.2f".format(baseItemTotal)}"

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
            if (quantity > 0) {
                quantity--
                updateQuantity()
            }
        }

        closeLayout.setOnClickListener {
            dialog?.dismiss()
        }

        addItemsPriceConst.setOnClickListener {
            addToCartItemApi()
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
                itemAddedRecyclerview.adapter = AddonItemAdapter(finalList, this)
            } else {
                Toast.makeText(
                    context,
                    response.message ?: "Failed to load Addons",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        addOnMealModelView.errorResponse.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }

        dialog!!.show()
    }

    //add to cart api
    private fun addToCartItemApi() {
        val savedQuantity = FoodPorter.quantityEncryptedPrefs.getQuantity(dishesId)
        if (savedQuantity <= 0) {
            Toast.makeText(this, "Quantity cannot be zero", Toast.LENGTH_SHORT).show()
            return
        }

       /* val addToCartBody = AddToCartItemBody(
            dish_id = dishesId,
            quantity = savedQuantity
        )*/
       // addToCartModelView.addToCartUser(activity, addToCartBody)
    }

    private fun addToCartItemObserver() {
        addToCartModelView.progressIndicator.observe(this) {}
        addToCartModelView.mAddToCartResponse.observe(this) {
            val success = it.peekContent().success
            val message = it.peekContent().message

            try {
                if (success == true) {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    dialog?.dismiss()
                    binding.itemAddedQuantityText.text = quantity.toString()
                    Log.e("dishesIdASD", "dishesId..$dishesId.....$quantity")
                 //   addToCartItemAdapter?.updateItemQuantity(dishesId, quantity)

                } else {

                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

        addToCartModelView.errorResponse.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }

    //update quantity api
    override fun quantityClick(position: Int, itemId: Int, quantity: Int) {
        updateBottomCartUI()

        val updateBody = UpdateQuantityBody(
            dish_id = itemId.toString(),
            quantity = quantity.toString()
        )

        updateQuantityModelView.updateQuantityUser(this, updateBody)
    }

    // Observer method
    private fun updateQuantityObserver() {
        updateQuantityModelView.progressIndicator.observe(this) {
        }

        updateQuantityModelView.mUpdateQuantityResponse.observe(this) {
            val response = it.peekContent()
            if (response.success == true) {
                Log.d("QuantityUpdate", "Success: ${response.message}")
                getCategoryDetailsApi()  // ✅ Refresh item list with updated data
            } else {
                Toast.makeText(this, response.message ?: "Failed to update quantity", Toast.LENGTH_SHORT).show()
            }
        }

        updateQuantityModelView.errorResponse.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
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

}
