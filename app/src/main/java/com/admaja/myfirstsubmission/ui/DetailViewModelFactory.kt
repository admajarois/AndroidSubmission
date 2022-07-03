package com.admaja.myfirstsubmission.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.admaja.myfirstsubmission.data.di.Injection
import com.admaja.myfirstsubmission.data.local.FavoriteRepository
import java.lang.IllegalArgumentException

class DetailViewModelFactory(private val favoriteRepository: FavoriteRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(favoriteRepository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class: "+modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: DetailViewModelFactory? = null
        fun getInstance(context: Context): DetailViewModelFactory =
            instance?: synchronized(this) {
                instance?: DetailViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}