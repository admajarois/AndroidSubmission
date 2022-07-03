package com.admaja.myfirstsubmission.data.di

import android.content.Context
import com.admaja.myfirstsubmission.data.api.ApiConfig
import com.admaja.myfirstsubmission.data.local.FavoriteRepository
import com.admaja.myfirstsubmission.data.local.room.FavoriteDatabase
import com.admaja.myfirstsubmission.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteDatabase.getInstance(context)
        val dao = database.favoriteDao()
        val appExecutors = AppExecutors()
        return FavoriteRepository.getInstance(apiService, dao, appExecutors)
    }
}