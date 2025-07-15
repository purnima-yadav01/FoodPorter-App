package com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.updatecartapi

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
class UpdateQuantityModelView @Inject constructor(
    application: Application,
    private val repository: Repository,
) : AndroidViewModel(application) {

    val progressIndicator = MutableLiveData<Boolean>()
    val errorResponse = MutableLiveData<Throwable>()
    val mUpdateQuantityResponse = MutableLiveData<Event<UpdateQuantityResponse>>()

    // Pass category name
    fun updateQuantityUser(activity: Activity, body: UpdateQuantityBody) =
        viewModelScope.launch {
            getUpdateAdd(activity, body)
        }

    private suspend fun getUpdateAdd(activity: Activity, body: UpdateQuantityBody) {
        progressIndicator.value = true

        repository.updateQuantityRepository(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<UpdateQuantityResponse>() {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onNext(value: UpdateQuantityResponse) {
                    progressIndicator.value = false
                    mUpdateQuantityResponse.value = Event(value)

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