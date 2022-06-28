package com.admaja.myfirstsubmission.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.admaja.myfirstsubmission.data.SettingPreferences
import java.lang.IllegalArgumentException

class ViewModelFactory(private val pref: SettingPreferences):ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)){
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class :"+modelClass.name)
    }
}