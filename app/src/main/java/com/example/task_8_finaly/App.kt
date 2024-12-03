package com.example.task_8_finaly

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.task_8_finaly.presentation.Creator

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        context = applicationContext
        val settingsInteractor = Creator.provideSettingsInteractor()

        darkTheme = settingsInteractor.getThemePreference()
        settingsInteractor.setDarkTheme(settingsInteractor.getThemePreference())
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

    companion object {
        fun getContext(): Context {
            return context
        }

        private lateinit var context: Context
        var darkTheme: Boolean = false

    }
}
