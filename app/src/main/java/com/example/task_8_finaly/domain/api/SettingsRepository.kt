package com.example.task_8_finaly.domain.api


interface SettingsRepository {
        fun getThemePreference(): Boolean

        fun saveThemePreferences(isDarkTheme: Boolean)
}