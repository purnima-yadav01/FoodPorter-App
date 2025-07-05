package com.food.foodporterapplication.customer.activity.getrestaurantdetailsapi

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
class RestaurantDetailModelView @Inject constructor(
    application: Application,
    private val repository: Repository,
) : AndroidViewModel(application) {

    val progressIndicator = MutableLiveData<Boolean>()
    val errorResponse = MutableLiveData<Throwable>()
    val mRestaurantDetailResponse = MutableLiveData<Event<RestaurantDetailResponse>>()

    // Pass category name
    fun getAllCategory(activity: Activity, id: Int) =
        viewModelScope.launch {
            getCategory(activity, id)
        }

    private suspend fun getCategory(activity: Activity, id: Int) {
        progressIndicator.value = true

        repository.getRestaurantDetailsRepository(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<RestaurantDetailResponse>() {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onNext(value: RestaurantDetailResponse) {
                    progressIndicator.value = false
                    mRestaurantDetailResponse.value = Event(value)
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