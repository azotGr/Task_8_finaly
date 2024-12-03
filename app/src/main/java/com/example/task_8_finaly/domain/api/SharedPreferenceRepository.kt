package com.example.task_8_finaly.domain.api

import android.content.Context

interface SharedPreferenceRepository {
        fun getBoolean(themeKey: String): Boolean
        fun putBoolean(themeKey: String, valueDarkTheme: Boolean)
        fun share(activityContext: Context)
        fun help(activityContext: Context)
}