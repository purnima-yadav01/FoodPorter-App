package com.food.foodporterapplication.customer.activity.addtocartitemapi.model

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
class AddToCartModelView  @Inject constructor(
    application: Application,
    private val repository: Repository,
) : AndroidViewModel(application) {

    val progressIndicator = MutableLiveData<Boolean>()
    val errorResponse = MutableLiveData<Throwable>()
    val mAddToCartResponse = MutableLiveData<Event<AddToCartResponse>>()

    // Pass category name
    fun addToCartUser(activity: Activity, addToCartBody: AddToCartItemBody) =
        viewModelScope.launch {
            getAddToCart(activity, addToCartBody)
        }

    private suspend fun getAddToCart(activity: Activity, addToCartBody: AddToCartItemBody) {
        progressIndicator.value = true

        repository.addToCartRepository(addToCartBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<AddToCartResponse>() {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onNext(value: AddToCartResponse) {
                    progressIndicator.value = false
                    mAddToCartResponse.value = Event(value)

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