package com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.model

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
class AddCategoryItemModelView  @Inject constructor(
    application: Application,
    private val repository: Repository,
) : AndroidViewModel(application) {

    val progressIndicator = MutableLiveData<Boolean>()
    val errorResponse = MutableLiveData<Throwable>()
    val mAddCategoryItemResponse = MutableLiveData<Event<AddCategoryItemResponse>>()

    // Pass category name
    fun getCategoryItemUser(activity: Activity, categoryId: Int, restaurantId: Int) =
        viewModelScope.launch {
            getCategoryItem(activity, categoryId,restaurantId)
        }

    private suspend fun getCategoryItem(activity: Activity,categoryId: Int, restaurantId: Int) {
        progressIndicator.value = true

        repository.getAllCategoryItemRepository(categoryId,restaurantId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<AddCategoryItemResponse>() {
                @RequiresApi(Build.VERSION_CODES.S)
                override fun onNext(value: AddCategoryItemResponse) {
                    progressIndicator.value = false
                    mAddCategoryItemResponse.value = Event(value)

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