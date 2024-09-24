package com.example.task_8_finaly

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class App : Application() {

    var darkTheme = false

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE)
        if (!sharedPreferences.contains("DARK_THEME")) {
            val isSystemDark = (resources.configuration.uiMode and
                    android.content.res.Configuration.UI_MODE_NIGHT_MASK) ==
                    android.content.res.Configuration.UI_MODE_NIGHT_YES
            sharedPreferences.edit().putBoolean("DARK_THEME", isSystemDark).apply()
        }

        applyTheme()
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        val sharedPreferences = getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("DARK_THEME", darkThemeEnabled).apply()
        applyTheme()
    }

    private fun applyTheme() {
        val sharedPreferences = getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE)
        val darkTheme = sharedPreferences.getBoolean("DARK_THEME", false)
        AppCompatDelegate.setDefaultNightMode(
            if (darkTheme) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }
}
