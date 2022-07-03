package com.admaja.myfirstsubmission.ui

import androidx.lifecycle.ViewModel
import com.admaja.myfirstsubmission.data.local.FavoriteRepository
import com.admaja.myfirstsubmission.data.local.entity.FavoriteEntity

class DetailViewModel(private val favoriteRepository: FavoriteRepository):ViewModel() {
    fun getDetailUser(login: String) = favoriteRepository.getDetailUser(login)

    fun getFavoritedUser() = favoriteRepository.getFavoritedUsers()

    fun saveUser(favorite: FavoriteEntity) {
        favoriteRepository.setFavoritedUser(favorite, true)
    }

    fun deleteUser(favorite: FavoriteEntity) {
        favoriteRepository.setFavoritedUser(favorite, false)
    }
}