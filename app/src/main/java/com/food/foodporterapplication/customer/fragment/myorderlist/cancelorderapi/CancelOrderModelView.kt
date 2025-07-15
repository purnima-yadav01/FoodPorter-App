package com.food.foodporterapplication.customer.fragment.myorderlist.cancelorderapi

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
class CancelOrderModelView @Inject constructor(
    application: Application,
    private val repository: Repository,
) : AndroidViewModel(application) {

    val progressIndicator = MutableLiveData<Boolean>()
    val errorResponse = MutableLiveData<Throwable>()
    val mCancelOrderResponse = MutableLiveData<Event<CancelOrderResponse>>()

    // Pass category name
    fun cancelUser(activity: Activity, body: CancelOrderBody) =
        viewModelScope.launch {
            getCancelOrder(activity, body)
        }

    private suspend fun getCancelOrder(activity: Activity, body: CancelOrderBody) {
        progressIndicator.value = true

        repository.cancelOrderRepository(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<CancelOrderResponse>() {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onNext(value: CancelOrderResponse) {
                    progressIndicator.value = false
                    mCancelOrderResponse.value = Event(value)

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