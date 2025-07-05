package com.food.foodporterapplication.customer.activity.otpverificationapi.model

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
class OtpVerifyModelView @Inject constructor(
    application: Application,
    private val repository: Repository,
) : AndroidViewModel(application) {

    val progressIndicator = MutableLiveData<Boolean>()
    val errorResponse = MutableLiveData<Throwable>()
    val mOtpVerifyResponse = MutableLiveData<Event<OtpVerifyResponse>>()

    // Pass category name
    fun otpVerifyUser(activity: Activity, body: OtpVerifyBody) =
        viewModelScope.launch {
            getVerify(activity, body)
        }

    private suspend fun getVerify(activity: Activity, body: OtpVerifyBody) {
        progressIndicator.value = true

        repository.otpVerifyRepository(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<OtpVerifyResponse>() {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onNext(value: OtpVerifyResponse) {
                    progressIndicator.value = false
                    mOtpVerifyResponse.value = Event(value)

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