package com.admaja.myfirstsubmission

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.admaja.myfirstsubmission.data.SettingPreferences
import com.admaja.myfirstsubmission.ui.SettingViewModel
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