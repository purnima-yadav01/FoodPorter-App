package com.food.foodporterapplication.customer.activity.orderdetailpage.model

import android.app.Activity
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.food.foodporterapplication.customer.utils.Event
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.food.foodporterapplication.customer.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class OrderDetailModelView @Inject constructor(
    application: Application,
    private val repository: Repository,
) :
    AndroidViewModel(application) {

    val progressIndicator = MutableLiveData<Boolean>()
    val errResponse = MutableLiveData<Throwable>()
    val mOrderDetailResponse = MutableLiveData<Event<OrderDetailResponse>>()

    fun orderDetailUser(activity: Activity, body: OrderDetailBody) =
        viewModelScope.launch {
            getOrderDetail(activity, body)
        }

    private fun getOrderDetail(activity: Activity, body: OrderDetailBody) {
        progressIndicator.value = true

        repository.orderDetailRepository(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<OrderDetailResponse>() {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onNext(value: OrderDetailResponse) {
                    progressIndicator.value = false
                    mOrderDetailResponse.value = Event(value)

                }

                override fun onError(e: Throwable) {
                    progressIndicator.value = false
                    errResponse.value = e
                }

                override fun onComplete() {
                    progressIndicator.value = false
                }

            })
    }
}
