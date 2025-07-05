package com.food.foodporterapplication.customer.activity.customerloginbyemail.model

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
class CustomerLoginEmailModelView  @Inject constructor(application: Application, private val repository: Repository) :
    AndroidViewModel(application) {
    val progressIndicator = MutableLiveData<Boolean>()
    val errorResponse = MutableLiveData<Throwable>()
    val mLoginResponse = MutableLiveData<Event<CustomerLoginEmailResponse>>()

    fun loginUpUser(activity: Activity, body: CustomerLoginEmailBody) =
        viewModelScope.launch {
            getLoginUp(activity, body)
        }

    private suspend fun getLoginUp(
        activity: Activity,
        body: CustomerLoginEmailBody
    ) {

        //progressDialog.start(activity.getString(R.string.please_wait))
        progressIndicator.value = true
        repository.customerLoginByEmailRepository(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<CustomerLoginEmailResponse>() {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onNext(value: CustomerLoginEmailResponse) {
                    /* progressIndicator.value = false
                       progressDialog.stop()*/
                    progressIndicator.value = false
                    // progressDialog.stop()
                    mLoginResponse.value = Event(value)
                }

                override fun onError(e: Throwable) {
                    progressIndicator.value = false
                    errorResponse.value = e
                }

                override fun onComplete() {
//                    progressDialog.stop()
                    progressIndicator.value = false
                }
            })
    }
}