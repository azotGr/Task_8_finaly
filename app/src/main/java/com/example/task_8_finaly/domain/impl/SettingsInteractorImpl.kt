package com.example.task_8_finaly.domain.impl

import com.example.task_8_finaly.domain.api.SettingsInteractor
import com.example.task_8_finaly.domain.api.SettingsRepository

class SettingsInteractorImpl(private val repository: SettingsRepository): SettingsInteractor {
    override fun getThemePreference(): Boolean {
        return repository.getThemePreference()
    }

    override fun saveThemePreferences(isDarkTheme: Boolean) {
        repository.saveThemePreferences(isDarkTheme)
    }
}