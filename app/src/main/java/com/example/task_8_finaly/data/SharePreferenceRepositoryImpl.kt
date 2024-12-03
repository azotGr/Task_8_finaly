package com.example.task_8_finaly.data

import android.content.Context
import com.example.task_8_finaly.App
import com.example.task_8_finaly.data.preference.SettingsManager
import com.example.task_8_finaly.domain.api.SharedPreferenceRepository

class SharedPreferenceRepositoryImp(
    private val settingsManager: SettingsManager = SettingsManager(App.getContext())
) : SharedPreferenceRepository {

    override fun getBoolean(themeKey: String): Boolean {
        return settingsManager.sharedPreferences.getBoolean(themeKey, false)
    }
    override fun putBoolean(themeKey: String, valueDarkTheme: Boolean) {
        settingsManager.sharedPreferences.edit().putBoolean(themeKey, valueDarkTheme).apply()
    }

    override fun share(activityContext: Context) {
        settingsManager.share(activityContext)
    }

    override fun help(activityContext: Context) {
        settingsManager.help(activityContext)
    }

}