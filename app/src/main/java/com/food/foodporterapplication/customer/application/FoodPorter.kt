package com.food.foodporterapplication.customer.application

import android.app.Application
import android.content.res.Configuration
import com.food.foodporterapplication.customer.utils.PreferenceManager
import com.food.foodporterapplication.customer.utils.EncryptedPrefs
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FoodPorter: Application() {
    companion object {
        lateinit var encryptedPrefs: PreferenceManager
        lateinit var quantityEncryptedPrefs: EncryptedPrefs
        lateinit var instance: FoodPorter
    }

    override  fun onCreate() {
        super.onCreate()
        encryptedPrefs = PreferenceManager(applicationContext).getInstance(applicationContext)
        quantityEncryptedPrefs = EncryptedPrefs(this)
        instance = this


        /* // ✅ Initialize Mastercard Gateway SDK
         CoroutineScope(Dispatchers.IO).launch {
             GatewaySDK.initialize(
                 this@Safer,
                 "TEST100532051",
                 "Safer",
                 "https://afs.gateway.mastercard.com/ma/",
                 GatewayRegion.MTF
             )
         }*/

        // FirebaseApp.initializeApp(this) // Uncomment if needed
    }

    fun isDarkThemeOn(): Boolean {
        return resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
}
