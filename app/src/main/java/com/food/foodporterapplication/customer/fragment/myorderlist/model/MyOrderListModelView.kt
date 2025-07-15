package com.food.foodporterapplication.customer.fragment.myorderlist.model

import android.app.Activity
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.food.foodporterapplication.customer.fragment.checkoutorderapi.model.CheckoutOrderBody
import com.food.foodporterapplication.customer.fragment.checkoutorderapi.model.CheckoutOrderResponse
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
class MyOrderListModelView @Inject constructor(
    application: Application,
    private val repository: Repository,
) : AndroidViewModel(application) {

    val progressIndicator = MutableLiveData<Boolean>()
    val errorResponse = MutableLiveData<Throwable>()
    val mMyOrderListResponse = MutableLiveData<Event<MyOrderListResponse>>()

    // Pass category name
    fun orderListUser(activity: Activity) =
        viewModelScope.launch {
            getOrderList(activity)
        }

    private suspend fun getOrderList(activity: Activity) {
        progressIndicator.value = true

        repository.orderListRepository()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<MyOrderListResponse>() {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onNext(value: MyOrderListResponse) {
                    progressIndicator.value = false
                    mMyOrderListResponse.value = Event(value)

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