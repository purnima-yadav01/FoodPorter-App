package com.food.foodporterapplication.customer.activity.customersignupapi.model

import android.app.Activity
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.food.foodporterapplication.customer.utils.Event
import com.food.foodporterapplication.customer.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CustomerSignUpModelView @Inject constructor(application: Application, private val repository: Repository) :
    AndroidViewModel(application) {
    val progressIndicator = MutableLiveData<Boolean>()
    val errorResponse = MutableLiveData<Throwable>()
    val mRegisterResponse = MutableLiveData<Event<CustomerSignUpResponse>>()

    fun signUpUser(activity: Activity, body: CustomerSignUpBody) =
        viewModelScope.launch {
            getSignUp(activity, body)
        }

    private suspend fun getSignUp(
        activity: Activity,
        body: CustomerSignUpBody
    ) {

        //progressDialog.start(activity.getString(R.string.please_wait))
        progressIndicator.value = true
        repository.customerSignUpRepository(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<CustomerSignUpResponse>() {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onNext(value: CustomerSignUpResponse) {
                    /* progressIndicator.value = false
                       progressDialog.stop()*/
                    progressIndicator.value = false
                    // progressDialog.stop()
                    mRegisterResponse.value = Event(value)
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