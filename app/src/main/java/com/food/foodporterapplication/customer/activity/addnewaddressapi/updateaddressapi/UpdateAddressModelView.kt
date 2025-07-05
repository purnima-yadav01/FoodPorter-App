package com.food.foodporterapplication.customer.activity.addnewaddressapi.updateaddressapi

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
class UpdateAddressModelView @Inject constructor(
    application: Application,
    private val repository: Repository,
) : AndroidViewModel(application) {

    val progressIndicator = MutableLiveData<Boolean>()
    val errorResponse = MutableLiveData<Throwable>()
    val mUpdateAddressResponse = MutableLiveData<Event<UpdateAddressResponse>>()

    // Pass category name
    fun updateAddUser(activity: Activity, id: Int, body: UpdateAddressBody) =
        viewModelScope.launch {
            getUpdateAdd(activity, id, body)
        }

    private suspend fun getUpdateAdd(activity: Activity, id: Int, body: UpdateAddressBody) {
        progressIndicator.value = true

        repository.updateAddressRepository(id, body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<UpdateAddressResponse>() {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onNext(value: UpdateAddressResponse) {
                    progressIndicator.value = false
                    mUpdateAddressResponse.value = Event(value)

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