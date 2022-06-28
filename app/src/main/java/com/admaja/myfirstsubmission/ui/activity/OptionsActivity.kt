package com.admaja.myfirstsubmission.ui.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.admaja.myfirstsubmission.R
import com.admaja.myfirstsubmission.data.SettingPreferences
import com.admaja.myfirstsubmission.ui.SettingViewModel
import com.admaja.myfirstsubmission.ui.ViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name="Settings")
class OptionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)
        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        settingViewModel.getThemeSettings().observe(this) {
                isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener{_: CompoundButton?, isChecked: Boolean->
            settingViewModel.saveThemeSetting(isChecked)
        }
    }
}