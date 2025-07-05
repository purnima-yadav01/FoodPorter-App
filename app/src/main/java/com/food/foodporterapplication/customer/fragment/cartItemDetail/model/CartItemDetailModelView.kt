package com.food.foodporterapplication.customer.fragment.cartItemDetail.model

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
class CartItemDetailModelView @Inject constructor(
    application: Application,
    private val repository: Repository,
) : AndroidViewModel(application) {

    val progressIndicator = MutableLiveData<Boolean>()
    val errorResponse = MutableLiveData<Throwable>()
    val mCardItemDetailResponse = MutableLiveData<Event<CardItemDetailResponse>>()

    // Pass category name
    fun cartUser(activity: Activity ) =
        viewModelScope.launch {
            getCartUser(activity)
        }

    private suspend fun getCartUser(activity: Activity) {
        progressIndicator.value = true

        repository.getCartDetailRepository()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<CardItemDetailResponse>() {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onNext(value: CardItemDetailResponse) {
                    progressIndicator.value = false
                    mCardItemDetailResponse.value = Event(value)

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