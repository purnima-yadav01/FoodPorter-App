package com.food.foodporterapplication.customer.activity.searchbyrestanddihses.model

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
class SearchByRestAndDishesModelView @Inject constructor(
    application: Application,
    private val repository: Repository,
) : AndroidViewModel(application) {

    val progressIndicator = MutableLiveData<Boolean>()
    val errorResponse = MutableLiveData<Throwable>()
    val mSearchByRestAndDishesResponse = MutableLiveData<Event<SearchByRestAndDishesResponse>>()

    // Pass category name
    fun searchItemUser(activity: String, q: String) =
        viewModelScope.launch {
            getSearchItem(activity, q)
        }

    private suspend fun getSearchItem(activity: String, q: String) {
        progressIndicator.value = true

        repository.searchByRestAndDishesRepository(q)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<SearchByRestAndDishesResponse>() {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onNext(value: SearchByRestAndDishesResponse) {
                    progressIndicator.value = false
                    mSearchByRestAndDishesResponse.value = Event(value)
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