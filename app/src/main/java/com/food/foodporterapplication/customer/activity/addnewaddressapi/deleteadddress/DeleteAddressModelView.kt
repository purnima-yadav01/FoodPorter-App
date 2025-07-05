package com.food.foodporterapplication.customer.activity.addnewaddressapi.deleteadddress


import android.app.Activity
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.food.foodporterapplication.customer.activity.addnewaddressapi.savedaddressapi.SavedAddressResponse
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
class DeleteAddressModelView @Inject constructor(
    application: Application,
    private val repository: Repository,
) : AndroidViewModel(application) {

    val progressIndicator = MutableLiveData<Boolean>()
    val errorResponse = MutableLiveData<Throwable>()
    val mDeleteAddressResponse = MutableLiveData<Event<DeleteAddressResponse>>()

    // Pass category name
    fun deleteAddUser(activity: Activity, id: Int) =
        viewModelScope.launch {
            getDeleteAdd(activity, id)
        }

    private suspend fun getDeleteAdd(activity: Activity, id: Int) {
        progressIndicator.value = true

        repository.deleteAddressRepository(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<DeleteAddressResponse>() {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onNext(value: DeleteAddressResponse) {
                    progressIndicator.value = false
                    mDeleteAddressResponse.value = Event(value)

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