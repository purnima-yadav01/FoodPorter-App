package com.food.foodporterapplication.customer.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class EncryptedPrefs(context: Context) {

    private val masterKeyAlias = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_cart_pref", // File name
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun getQuantity(dishId: Int): Int {
        return prefs.getInt("quantity_$dishId", 0)
    }

    fun setQuantity(dishId: Int, value: Int) {
        prefs.edit().putInt("quantity_$dishId", value).apply()
    }

    fun removeQuantity(dishId: Int) {
        prefs.edit().remove("quantity_$dishId").apply()
    }
}
