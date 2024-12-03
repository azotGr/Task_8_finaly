package com.example.task_8_finaly.domain.api

import android.content.Context

interface SettingsInteractor {
    fun getDarkTheme(): Boolean
    fun setDarkTheme(valueDarkTheme: Boolean)
    fun getThemePreference(): Boolean
    fun share(activityContext: Context)
    fun help(activityContext: Context)
}