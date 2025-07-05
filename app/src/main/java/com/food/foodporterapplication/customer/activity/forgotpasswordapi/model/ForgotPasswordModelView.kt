package com.food.foodporterapplication.customer.activity.forgotpasswordapi.model

import android.app.Activity
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.food.foodporterapplication.customer.activity.loginwithphonemunberapi.model.LoginPhoneNumberBody
import com.food.foodporterapplication.customer.activity.loginwithphonemunberapi.model.LoginPhoneNumberResponse
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
class ForgotPasswordModelView @Inject constructor(
    application: Application,
    private val repository: Repository,
) : AndroidViewModel(application) {

    val progressIndicator = MutableLiveData<Boolean>()
    val errorResponse = MutableLiveData<Throwable>()
    val mForgotPasswordResponse = MutableLiveData<Event<ForgotPasswordResponse>>()

    // Pass category name
    fun forgotUser(activity: Activity, body: ForgotPasswordBody) =
        viewModelScope.launch {
            getForgot(activity, body)
        }

    private suspend fun getForgot(activity: Activity, body: ForgotPasswordBody) {
        progressIndicator.value = true

        repository.forgotPasswordRepository(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<ForgotPasswordResponse>() {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onNext(value: ForgotPasswordResponse) {
                    progressIndicator.value = false
                    mForgotPasswordResponse.value = Event(value)

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