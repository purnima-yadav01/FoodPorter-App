package com.food.foodporterapplication.customer.fragment.checkoutorderapi.model

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
class CheckoutOrderModelView @Inject constructor(
    application: Application,
    private val repository: Repository,
) : AndroidViewModel(application) {

    val progressIndicator = MutableLiveData<Boolean>()
    val errorResponse = MutableLiveData<Throwable>()
    val mCheckoutOrderResponse = MutableLiveData<Event<CheckoutOrderResponse>>()

    // Pass category name
    fun checkoutOrder(activity: Activity, body: CheckoutOrderBody) =
        viewModelScope.launch {
            getCheckout(activity, body)
        }

    private suspend fun getCheckout(activity: Activity, body: CheckoutOrderBody) {
        progressIndicator.value = true

        repository.checkoutOrderRepository(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<CheckoutOrderResponse>() {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onNext(value: CheckoutOrderResponse) {
                    progressIndicator.value = false
                    mCheckoutOrderResponse.value = Event(value)

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