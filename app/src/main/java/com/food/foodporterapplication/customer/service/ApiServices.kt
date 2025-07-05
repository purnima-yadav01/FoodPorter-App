package com.food.foodporterapplication.customer.service

import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.model.AddCategoryItemResponse
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
import com.food.foodporterapplication.customer.activity.customersignupapi.model.CustomerSignUpBody
import com.food.foodporterapplication.customer.activity.customersignupapi.model.CustomerSignUpResponse
import com.food.foodporterapplication.customer.activity.forgotpasswordapi.model.ForgotPasswordBody
import com.food.foodporterapplication.customer.activity.forgotpasswordapi.model.ForgotPasswordResponse
import com.food.foodporterapplication.customer.activity.getrestaurantdetailsapi.RestaurantDetailResponse
import com.food.foodporterapplication.customer.activity.loginwithphonemunberapi.model.LoginPhoneNumberBody
import com.food.foodporterapplication.customer.activity.loginwithphonemunberapi.model.LoginPhoneNumberResponse
import com.food.foodporterapplication.customer.activity.otpverificationapi.model.OtpVerifyBody
import com.food.foodporterapplication.customer.activity.otpverificationapi.model.OtpVerifyResponse
import com.food.foodporterapplication.customer.activity.profileapi.model.GetProfileResponse
import com.food.foodporterapplication.customer.activity.profileapi.updateprofileapi.UpdateProfileResponse
import com.food.foodporterapplication.customer.activity.searchbyrestanddihses.model.SearchByRestAndDishesResponse
import com.food.foodporterapplication.customer.application.FoodPorter
import com.food.foodporterapplication.customer.fragment.homepage.getallcategory.GetAllCategoryResponse
import com.food.foodporterapplication.customer.fragment.homepage.getfilterapi.GetFilterListResponse
import com.food.foodporterapplication.customer.fragment.homepage.getfilteritemlistapi.GetFilterItemListResponse
import com.food.foodporterapplication.customer.fragment.homepage.model.GetAllRestaurantResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiServices {

    @Headers("Content-Type:application/json")
    @POST("auth/signup")
    fun customerSignUpApi(
        @Body body: CustomerSignUpBody,
    ): Observable<CustomerSignUpResponse>


    @Headers("Content-Type:application/json")
    @POST("auth/login/customer")
    fun customerLoginByEmail(
        @Body body: CustomerLoginEmailBody,
    ): Observable<CustomerLoginEmailResponse>


    @Headers("Content-Type:application/json")
    @POST("restaurants/list")
    fun getAllRestaurantApi(
        @Header("Authorization") token: String = FoodPorter.encryptedPrefs.token,
    ): Observable<GetAllRestaurantResponse>


    @Headers("Content-Type:application/json")
    @GET("categories")
    fun getAllCategory(
        @Header("Authorization") token: String = FoodPorter.encryptedPrefs.token,
    ): Observable<GetAllCategoryResponse>


    @Headers("Content-Type:application/json")
    @GET("categories/restaurants-by-category/{categoryName}")
    fun fetchCategoryItemListApi(
        @Path("categoryName") categoryName: String,
        @Header("Authorization") token: String = FoodPorter.encryptedPrefs.token,
    ): Observable<FavoriteCuisinesResponse>


    @Headers("Content-Type:application/json")
    @GET("dishes/restaurant/{id}")
    fun getRestaurantDetailApi(
        @Path("id") id: Int,
        @Header("Authorization") token: String = FoodPorter.encryptedPrefs.token,
    ): Observable<RestaurantDetailResponse>


    @Headers("Content-Type:application/json")
    @GET("search")
    fun searchByRestAndDishesApi(
        @Query("q") keyword: String,
        @Header("Authorization") token: String = FoodPorter.encryptedPrefs.token,
    ): Observable<SearchByRestAndDishesResponse>


    @Headers("Content-Type:application/json")
    @GET("filters/image-list")
    fun getFilterListApi(
        @Header("Authorization") token: String = FoodPorter.encryptedPrefs.token,
    ): Observable<GetFilterListResponse>


    @Headers("Content-Type:application/json")
    @GET("filters/text-list")
    fun getFilterItemsApi(
        @Header("Authorization") token: String = FoodPorter.encryptedPrefs.token,
    ): Observable<GetFilterItemListResponse>


    @Headers("Content-Type:application/json")
    @GET("dishes/category/{category_id}/restaurant/{restaurant_id}")
    fun getAllCategoryItemApi(
        @Path("category_id") categoryId: Int,
        @Path("restaurant_id") restaurantId: Int,
    ): Observable<AddCategoryItemResponse>


    @Headers("Content-Type:application/json")
    @POST("cart/add")
    fun postAddToCartApi(
        @Header("Authorization") token: String = FoodPorter.encryptedPrefs.token,
        @Body body: AddToCartItemBody,
    ): Observable<AddToCartResponse>


    @Headers("Content-Type:application/json")
    @POST("cart/list")
    fun getCartDetailApi(
        @Header("Authorization") token: String = FoodPorter.encryptedPrefs.token,
    ): Observable<CardItemDetailResponse>


    @Headers("Content-Type:application/json")
    @POST("coupons/view-all")
    fun postViewAllCouponApi(
        @Header("Authorization") token: String = FoodPorter.encryptedPrefs.token,
        @Body body: ViewAllCouponBody,
    ): Observable<ViewAllCouponsResponse>


    @Headers("Content-Type: application/json")
    @POST("customers/profile")
    fun getProfile(
        @Header("Authorization") token: String = FoodPorter.encryptedPrefs.token,
    ): Observable<GetProfileResponse>


    @Multipart
    @POST("customers/update_profile")
    fun updateProfileApi(
        @Part("firstName") firstName: RequestBody,
        @Part("lastName") lastName: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("countryCode") countryCode: RequestBody,
        @Part("address") address: RequestBody,
        @Part profile_image: MultipartBody.Part,
        @Header("Authorization") token: String = FoodPorter.encryptedPrefs.token,
    ): Observable<UpdateProfileResponse>


    @Headers("Content-Type:application/json")
    @POST("otp/send-otp")
    fun loginWithPhoneNumber(
        @Body body: LoginPhoneNumberBody,
    ): Observable<LoginPhoneNumberResponse>


    @Headers("Content-Type:application/json")
    @POST("auth/forgot-password")
    fun forgotPasswordApi(
        @Body body: ForgotPasswordBody,
    ): Observable<ForgotPasswordResponse>


    @Headers("Content-Type:application/json")
    @POST("auth/verify-otp")
    fun otpVerifyApi(
        @Body body: OtpVerifyBody,
    ): Observable<OtpVerifyResponse>


    @Headers("Content-Type:application/json")
    @POST("auth/change-password")
    fun confirmPasswordApi(
        @Body body: ConfirmPasswordBody,
    ): Observable<ConfirmPasswordResponse>


    @Headers("Content-Type:application/json")
    @POST("address/add")
    fun addAddressApi(
        @Header("Authorization") token: String = FoodPorter.encryptedPrefs.token,
        @Body body: AddAddressBody,
    ): Observable<AddAddressResponse>


    @Headers("Content-Type:application/json")
    @POST("address")
    fun getAddressApi(
        @Header("Authorization") token: String = FoodPorter.encryptedPrefs.token,
    ): Observable<SavedAddressResponse>


    @Headers("Content-Type:application/json")
    @POST("address/delete/{id}")
    fun deleteAddressApi(
        @Path("id") id: Int,
        @Header("Authorization") token: String = FoodPorter.encryptedPrefs.token,
    ): Observable<DeleteAddressResponse>


    @Headers("Content-Type:application/json")
    @POST("address/change-address/{id}")
    fun updateAddressApi(
        @Path("id") id: Int,
        @Body body: UpdateAddressBody,
        @Header("Authorization") token: String = FoodPorter.encryptedPrefs.token,
        ): Observable<UpdateAddressResponse>

    @Headers("Content-Type:application/json")
    @POST("addons/add-meal")
    fun addOnMeal(

    )
}