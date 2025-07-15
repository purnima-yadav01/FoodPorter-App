package com.food.foodporterapplication.customer.repository

import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.addonmeal.AddOnMealResponse
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.model.AddCategoryItemResponse
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.updatecartapi.UpdateQuantityBody
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.updatecartapi.UpdateQuantityResponse
import com.food.foodporterapplication.customer.activity.addnewaddressapi.addaddressmodel.AddAddressBody
import com.food.foodporterapplication.customer.activity.addnewaddressapi.addaddressmodel.AddAddressResponse
import com.food.foodporterapplication.customer.activity.addnewaddressapi.deleteadddress.DeleteAddressResponse
import com.food.foodporterapplication.customer.activity.addnewaddressapi.savedaddressapi.SavedAddressResponse
import com.food.foodporterapplication.customer.activity.addnewaddressapi.updateaddressapi.UpdateAddressBody
import com.food.foodporterapplication.customer.activity.addnewaddressapi.updateaddressapi.UpdateAddressResponse
import com.food.foodporterapplication.customer.activity.addtocartitemapi.model.AddToCartItemBody
import com.food.foodporterapplication.customer.activity.addtocartitemapi.model.AddToCartResponse
import com.food.foodporterapplication.customer.activity.applycouponsapi.viewallcoupons.ViewAllCouponBody
import com.food.foodporterapplication.customer.activity.applycouponsapi.viewallcoupons.ViewAllCouponsResponse
import com.food.foodporterapplication.customer.fragment.cartItemDetail.model.CardItemDetailResponse
import com.food.foodporterapplication.customer.activity.categoryorderitem.model.FavoriteCuisinesResponse
import com.food.foodporterapplication.customer.activity.confirmpasswordapi.model.ConfirmPasswordBody
import com.food.foodporterapplication.customer.activity.confirmpasswordapi.model.ConfirmPasswordResponse
import com.food.foodporterapplication.customer.activity.customerloginbyemail.model.CustomerLoginEmailBody
import com.food.foodporterapplication.customer.activity.customerloginbyemail.model.CustomerLoginEmailResponse
import io.reactivex.Observable

import com.food.foodporterapplication.customer.activity.customersignupapi.model.CustomerSignUpBody
import com.food.foodporterapplication.customer.activity.customersignupapi.model.CustomerSignUpResponse
import com.food.foodporterapplication.customer.activity.forgotpasswordapi.model.ForgotPasswordBody
import com.food.foodporterapplication.customer.activity.forgotpasswordapi.model.ForgotPasswordResponse
import com.food.foodporterapplication.customer.activity.getrestaurantdetailsapi.RestaurantDetailResponse
import com.food.foodporterapplication.customer.activity.loginwithphonemunberapi.model.LoginPhoneNumberBody
import com.food.foodporterapplication.customer.activity.loginwithphonemunberapi.model.LoginPhoneNumberResponse
import com.food.foodporterapplication.customer.activity.orderdetailpage.model.OrderDetailBody
import com.food.foodporterapplication.customer.activity.orderdetailpage.model.OrderDetailResponse
import com.food.foodporterapplication.customer.activity.otpverificationapi.model.OtpVerifyBody
import com.food.foodporterapplication.customer.activity.otpverificationapi.model.OtpVerifyResponse
import com.food.foodporterapplication.customer.activity.profileapi.model.GetProfileResponse
import com.food.foodporterapplication.customer.activity.profileapi.updateprofileapi.UpdateProfileResponse
import com.food.foodporterapplication.customer.activity.searchbyrestanddihses.model.SearchByRestAndDishesResponse
import com.food.foodporterapplication.customer.application.FoodPorter
import com.food.foodporterapplication.customer.fragment.checkoutorderapi.model.CheckoutOrderBody
import com.food.foodporterapplication.customer.fragment.checkoutorderapi.model.CheckoutOrderResponse
import com.food.foodporterapplication.customer.fragment.homepage.getallcategory.GetAllCategoryResponse
import com.food.foodporterapplication.customer.fragment.homepage.getfilterapi.GetFilterListResponse
import com.food.foodporterapplication.customer.fragment.homepage.getfilteritemlistapi.GetFilterItemListResponse
import com.food.foodporterapplication.customer.fragment.homepage.model.GetAllRestaurantResponse
import com.food.foodporterapplication.customer.fragment.myorderlist.cancelorderapi.CancelOrderBody
import com.food.foodporterapplication.customer.fragment.myorderlist.cancelorderapi.CancelOrderResponse
import com.food.foodporterapplication.customer.fragment.myorderlist.model.MyOrderListResponse
import com.food.foodporterapplication.customer.service.ApiServices
import com.google.zxing.oned.OneDReader
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class Repository @Inject constructor(private val services: ApiServices) {

    fun customerSignUpRepository(body: CustomerSignUpBody): Observable<CustomerSignUpResponse> {
        return services.customerSignUpApi(body)
    }

    fun customerLoginByEmailRepository(body: CustomerLoginEmailBody): Observable<CustomerLoginEmailResponse>{
        return  services.customerLoginByEmail(body)
    }

    fun getAllRestaurantRepository(): Observable<GetAllRestaurantResponse>{
        return  services.getAllRestaurantApi(FoodPorter.encryptedPrefs.token)
    }

    fun getAllCategoryRepository(): Observable<GetAllCategoryResponse>{
        return services.getAllCategory(FoodPorter.encryptedPrefs.token)
    }

    fun fetchCategoryItemRepository(categoryName: String): Observable<FavoriteCuisinesResponse> {
        return services.fetchCategoryItemListApi(categoryName, FoodPorter.encryptedPrefs.token)
    }

    fun getRestaurantDetailsRepository(id: Int): Observable<RestaurantDetailResponse> {
        return services.getRestaurantDetailApi(id, FoodPorter.encryptedPrefs.token)
    }

    fun searchByRestAndDishesRepository(q: String): Observable<SearchByRestAndDishesResponse>{
        return services.searchByRestAndDishesApi(q, FoodPorter.encryptedPrefs.token)
    }

    fun getFilterListRepository(): Observable<GetFilterListResponse>{
        return services.getFilterListApi(FoodPorter.encryptedPrefs.token)
    }

    fun getFilterItemListRepository(): Observable<GetFilterItemListResponse>{
        return services.getFilterItemsApi(FoodPorter.encryptedPrefs.token)
    }

    fun getAllCategoryItemRepository(categoryId: Int, restaurantId: Int): Observable<AddCategoryItemResponse>{
        val token = "Bearer ${FoodPorter.encryptedPrefs.token}"
        return services.getAllCategoryItemApi(token, categoryId, restaurantId)
    }

    fun addToCartRepository(body: AddToCartItemBody): Observable<AddToCartResponse>{
        val token = "Bearer ${FoodPorter.encryptedPrefs.token}"
        return services.postAddToCartApi(token, body)
    }

    fun getCartDetailRepository(): Observable<CardItemDetailResponse>{
        val token = "Bearer ${FoodPorter.encryptedPrefs.token}"
        return services.getCartDetailApi(token )
    }

    fun viewAllCouponsRepository(body: ViewAllCouponBody): Observable<ViewAllCouponsResponse>{
        val token = "Bearer ${FoodPorter.encryptedPrefs.token}"
        return services.postViewAllCouponApi(token, body)
    }

    fun getProfileRepository(): Observable<GetProfileResponse> {
        val token = "Bearer ${FoodPorter.encryptedPrefs.token}"
        return services.getProfile(token)
    }

    fun postUpdateProfile(
        firstName: RequestBody,
        lastName: RequestBody,
        email: RequestBody,
        phone: RequestBody,
        countryCode: RequestBody,
        address: RequestBody,
        profile_image: MultipartBody.Part,
    ): Observable<UpdateProfileResponse> {
        val token = "Bearer ${FoodPorter.encryptedPrefs.token}"
        return services.updateProfileApi(
            firstName,
            lastName,
            email,
            phone,
            countryCode,
            address,
            profile_image,
            token,

        )
    }

    fun loginWithNumberRepository(body: LoginPhoneNumberBody): Observable<LoginPhoneNumberResponse>{
        return services.loginWithPhoneNumber(body)
    }

    fun forgotPasswordRepository(body: ForgotPasswordBody): Observable<ForgotPasswordResponse>{
        return services.forgotPasswordApi(body)
    }

    fun otpVerifyRepository(body: OtpVerifyBody): Observable<OtpVerifyResponse>{
        return services.otpVerifyApi(body)
    }

    fun confirmPasswordRepository(body: ConfirmPasswordBody): Observable<ConfirmPasswordResponse>{
        return services.confirmPasswordApi(body)
    }

    fun addAddressRepository(body: AddAddressBody): Observable<AddAddressResponse>{
        val token = "Bearer ${FoodPorter.encryptedPrefs.token}"
        return services.addAddressApi(token, body)
    }

    fun getAddressRepository(): Observable<SavedAddressResponse>{
        val token = "Bearer ${FoodPorter.encryptedPrefs.token}"
        return services.getAddressApi(token)
    }

    fun deleteAddressRepository(id: Int): Observable<DeleteAddressResponse>{
        val token = "Bearer ${FoodPorter.encryptedPrefs.token}"
        return services.deleteAddressApi(id, token)
    }

    fun updateAddressRepository(id: Int, body: UpdateAddressBody): Observable<UpdateAddressResponse>{
        val token = "Bearer ${FoodPorter.encryptedPrefs.token}"
        return services.updateAddressApi(id, body, token)
    }

    fun addOnMealRepository(id:  Int): Observable<AddOnMealResponse>{
        return services.addOnMealApi(id)
    }

    fun updateQuantityRepository(body: UpdateQuantityBody): Observable<UpdateQuantityResponse>{
        val token = "Bearer ${FoodPorter.encryptedPrefs.token}"
        return services.updateQuantityApi(body, token)
    }


    fun checkoutOrderRepository(body: CheckoutOrderBody): Observable<CheckoutOrderResponse>{
        val token = "Bearer ${FoodPorter.encryptedPrefs.token}"
        return services.checkoutOrderApi(body, token)
    }

    fun orderListRepository(): Observable<MyOrderListResponse>{
        val token = "Bearer ${FoodPorter.encryptedPrefs.token}"
        return services.myOrderListApi(token)
    }


    fun cancelOrderRepository(body: CancelOrderBody): Observable<CancelOrderResponse>{
        val token = "Bearer ${FoodPorter.encryptedPrefs.token}"
        return services.cancelOrderApi(body, token)
    }

    fun orderDetailRepository(body: OrderDetailBody): Observable<OrderDetailResponse>{
        val token = "Bearer ${FoodPorter.encryptedPrefs.token}"
        return  services.orderDetailsApi(body, token)
    }

}
