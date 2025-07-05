package com.food.foodporterapplication.customer.activity.addnewaddressapi.addaddressmodel

import android.app.Activity
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.food.foodporterapplication.customer.activity.confirmpasswordapi.model.ConfirmPasswordBody
import com.food.foodporterapplication.customer.activity.confirmpasswordapi.model.ConfirmPasswordResponse
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
class AddAddressModelView @Inject constructor(
    application: Application,
    private val repository: Repository,
) : AndroidViewModel(application) {

    val progressIndicator = MutableLiveData<Boolean>()
    val errorResponse = MutableLiveData<Throwable>()
    val mAddAddressResponse = MutableLiveData<Event<AddAddressResponse>>()

    // Pass category name
    fun addNewUser(activity: Activity, body: AddAddressBody) =
        viewModelScope.launch {
            getAddNew(activity, body)
        }

    private suspend fun getAddNew(activity: Activity, body: AddAddressBody) {
        progressIndicator.value = true

        repository.addAddressRepository(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<AddAddressResponse>() {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onNext(value: AddAddressResponse) {
                    progressIndicator.value = false
                    mAddAddressResponse.value = Event(value)

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