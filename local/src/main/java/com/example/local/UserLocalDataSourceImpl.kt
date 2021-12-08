package com.example.local

import com.example.data.local.UserLocalDataSource
import com.example.local.prefs.PrefsHelper
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(private val prefs: PrefsHelper) : UserLocalDataSource {

    override var language: String
        get() = prefs.language
        set(value) {
            prefs.language = value
        }

}