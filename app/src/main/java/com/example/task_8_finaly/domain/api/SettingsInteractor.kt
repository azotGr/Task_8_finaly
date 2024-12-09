package com.example.task_8_finaly.domain.api

import android.content.Context

interface SettingsInteractor {
    fun getThemePreference(): Boolean

    fun saveThemePreferences(isDarkTheme: Boolean)
}