package com.food.foodporterapplication.customer.activity.categoryorderitem.model

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
class FavoriteCuisinesModelView @Inject constructor(
    application: Application,
    private val repository: Repository,
) : AndroidViewModel(application) {

    val progressIndicator = MutableLiveData<Boolean>()
    val errorResponse = MutableLiveData<Throwable>()
    val mFavoriteCuisinesResponse = MutableLiveData<Event<FavoriteCuisinesResponse>>()

    // Pass category name
    fun getAllCategory(activity: Activity, categoryName: String) =
        viewModelScope.launch {
            getCategory(activity, categoryName)
        }

    private suspend fun getCategory(activity: Activity, categoryName: String) {
        progressIndicator.value = true

        repository.fetchCategoryItemRepository(categoryName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<FavoriteCuisinesResponse>() {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onNext(value: FavoriteCuisinesResponse) {
                    progressIndicator.value = false
                    mFavoriteCuisinesResponse.value = Event(value)
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
