package com.example.task_8_finaly.data

import com.example.task_8_finaly.data.preference.SettingsManager
import com.example.task_8_finaly.domain.api.SettingsRepository

class SettingsRepositoryImpl(private val settingsManager: SettingsManager): SettingsRepository {
    override fun getThemePreference(): Boolean {
        return settingsManager.getThemePreference()
    }

    override fun saveThemePreferences(isDarkTheme: Boolean) {
        settingsManager.saveThemePreferences(isDarkTheme)
    }
}