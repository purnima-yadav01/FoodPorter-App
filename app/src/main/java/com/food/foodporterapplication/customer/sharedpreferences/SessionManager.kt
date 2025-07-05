package com.webn.foodporterapplication.customer.sharedpreferences

import android.content.Context
import android.preference.PreferenceManager

class SessionManager(context: Context?) {
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    private val prefsNew = PreferenceManager.getDefaultSharedPreferences(context)

    companion object {

        private const val IS_LOGIN = "islogin"
        private const val SELECTED_LANGUAGE="selected_Language"
        private const val SELECTED_POSITION = "selected_position"
    }

    var isLogin: Boolean
        get() = prefs.getBoolean(IS_LOGIN, false)
        set(isLogin) {
            prefs.edit().putBoolean(IS_LOGIN, isLogin).apply()
        }

    fun logout() {
        prefs.edit().clear().apply()
    }

    var selectedLanguage: String?
        get() = prefs.getString(SELECTED_LANGUAGE, "")
        set(selectedLanguage) {
            prefs.edit().putString(SELECTED_LANGUAGE, selectedLanguage).apply()
        }

    // Save the selected position
    fun setSelectedPosition(position: Int) {
        prefs.edit().putInt(SELECTED_POSITION, position).apply()
    }

    // Retrieve the selected position (returns -1 if no position is stored)
    fun getSelectedPosition(): Int {
        return prefs.getInt(SELECTED_POSITION, -1)
    }
}