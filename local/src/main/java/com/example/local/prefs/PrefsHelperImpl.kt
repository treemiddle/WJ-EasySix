package com.example.local.prefs

import android.content.Context
import androidx.core.content.edit
import com.example.local.utils.PREFS_APP_NAME
import com.example.local.utils.PREFS_LANGUAGE
import javax.inject.Inject

class PrefsHelperImpl @Inject constructor(applicationContext: Context) : PrefsHelper {

    private val prefs = applicationContext.getSharedPreferences(PREFS_APP_NAME, Context.MODE_PRIVATE)

    override var language: String
        get() = prefs.getString(PREFS_LANGUAGE, "kr") ?: "kr"
        @Synchronized
        set(value) {
            prefs.edit(false) {
                putString(PREFS_LANGUAGE, value)
            }
        }


}