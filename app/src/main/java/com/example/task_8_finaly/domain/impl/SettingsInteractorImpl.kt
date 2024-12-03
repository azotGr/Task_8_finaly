package com.example.task_8_finaly.domain.impl

import android.content.Context
import com.example.task_8_finaly.domain.api.SettingsInteractor
import com.example.task_8_finaly.domain.api.SharedPreferenceRepository

class SettingsInteractorImpl(private var repository: SharedPreferenceRepository) : SettingsInteractor {
    private val THEME_KEY = "theme_key"

    override fun getDarkTheme(): Boolean {
        return repository.getBoolean(THEME_KEY)
    }
    override fun setDarkTheme(valueDarkTheme: Boolean) {
        repository.putBoolean(THEME_KEY, valueDarkTheme)
    }
    override fun getThemePreference(): Boolean {
        return getDarkTheme()
    }

    override fun share(activityContext: Context) {
        repository.share(activityContext)
    }

    override fun help(activityContext: Context) {
        repository.help(activityContext)
    }
}