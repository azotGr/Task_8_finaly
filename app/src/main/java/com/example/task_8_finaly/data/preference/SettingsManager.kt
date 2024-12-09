package com.example.task_8_finaly.data.preference

import android.content.Context
import android.content.Context.MODE_PRIVATE

const val APP_THEME_PREFERENCES = "app_theme_preferences"
const val dark_theme = "dark_theme"

class SettingsManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(APP_THEME_PREFERENCES, MODE_PRIVATE)


    fun getThemePreference(): Boolean {
        return sharedPreferences.getBoolean(dark_theme, false)
    }

    fun saveThemePreferences(isDarkTheme: Boolean) {
        sharedPreferences.edit()
            .putBoolean(com.example.task_8_finaly.dark_theme, isDarkTheme)
            .apply()
    }

}