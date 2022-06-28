package com.admaja.myfirstsubmission.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.admaja.myfirstsubmission.data.SettingPreferences
import com.admaja.myfirstsubmission.databinding.ActivitySplashBinding
import com.admaja.myfirstsubmission.ui.SettingViewModel
import com.admaja.myfirstsubmission.ui.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name="Settings")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        settingViewModel.getThemeSettings().observe(this) {
            isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, delay)
    }

    companion object {
        const val delay: Long = 3000L
    }
}