package com.food.foodporterapplication.customer.fragment.homepage.getfilterapi

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
class GetFilterListModelView @Inject constructor(
    application: Application,
    private val repository: Repository,
) : AndroidViewModel(application) {

    val progressIndicator = MutableLiveData<Boolean>()
    val errorResponse = MutableLiveData<Throwable>()
    val mGetFilterListResponse = MutableLiveData<Event<GetFilterListResponse>>()

    // Pass category name
    fun getFilterUser(activity: Activity) =
        viewModelScope.launch {
            getFilter(activity)
        }

    private suspend fun getFilter(activity: Activity) {
        progressIndicator.value = true

        repository.getFilterListRepository()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<GetFilterListResponse>() {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onNext(value: GetFilterListResponse) {
                    progressIndicator.value = false
                    mGetFilterListResponse.value = Event(value)
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