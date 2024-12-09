package com.example.task_8_finaly

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.task_8_finaly.creator.Creator

const val dark_theme = "dark_theme"

class App : Application() {
    var darkTheme = false

    override fun onCreate() {
        super.onCreate()

        val settingsInteractor = Creator.provideSettingsInteractor(this)

        // Считать сохраненное состояние темы
        darkTheme = settingsInteractor.getThemePreference()

        // Применить тему
        AppCompatDelegate.setDefaultNightMode(
            if (darkTheme) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )

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
}
