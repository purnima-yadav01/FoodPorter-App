package com.food.foodporterapplication.customer.activity.confirmpasswordapi.model

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
class ConfirmPasswordModelView @Inject constructor(
    application: Application,
    private val repository: Repository,
) : AndroidViewModel(application) {

    val progressIndicator = MutableLiveData<Boolean>()
    val errorResponse = MutableLiveData<Throwable>()
    val mConfirmPasswordResponse = MutableLiveData<Event<ConfirmPasswordResponse>>()

    // Pass category name
    fun confirmPasswordUser(activity: Activity, body: ConfirmPasswordBody) =
        viewModelScope.launch {
            getConfirmPassword(activity, body)
        }

    private suspend fun getConfirmPassword(activity: Activity, body: ConfirmPasswordBody) {
        progressIndicator.value = true

        repository.confirmPasswordRepository(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<ConfirmPasswordResponse>() {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onNext(value: ConfirmPasswordResponse) {
                    progressIndicator.value = false
                    mConfirmPasswordResponse.value = Event(value)

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