package com.food.foodporterapplication.customer.activity.applycouponsapi.viewallcoupons

import android.app.Activity
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.food.foodporterapplication.customer.repository.Repository
import com.food.foodporterapplication.customer.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ViewAllCouponsModelView @Inject constructor(
    application: Application,
    private val repository: Repository,
) : AndroidViewModel(application) {

    val progressIndicator = MutableLiveData<Boolean>()
    val errorResponse = MutableLiveData<Throwable>()
    val mViewAllCouponsResponse = MutableLiveData<Event<ViewAllCouponsResponse>>()

    // Pass category name
    fun viewAllUser(activity: Activity, viewAllCouponBody: ViewAllCouponBody) =
        viewModelScope.launch {
            getViewAllCoupon(activity,viewAllCouponBody)
        }

    private suspend fun getViewAllCoupon(activity: Activity, viewAllCouponBody: ViewAllCouponBody) {
        progressIndicator.value = true

        repository.viewAllCouponsRepository(viewAllCouponBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<ViewAllCouponsResponse>() {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onNext(value: ViewAllCouponsResponse) {
                    progressIndicator.value = false
                    mViewAllCouponsResponse.value = Event(value)

                }

                override fun onError(e: Throwable) {
                    progressIndicator.value = false
                    errorResponse.value = e
                }

                override fun onComplete() {
                    progressIndicator.value = false
                }
            })
    }
}