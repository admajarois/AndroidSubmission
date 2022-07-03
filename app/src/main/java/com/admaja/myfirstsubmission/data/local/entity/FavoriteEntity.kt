package com.admaja.myfirstsubmission.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite_user")
data class FavoriteEntity (
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: Int?,

    @field:ColumnInfo(name = "login")
    val login: String?,

    @field:ColumnInfo(name = "name")
    val name: String?,

    @field:ColumnInfo(name = "avatarUrl")
    val avatarUrl: String?,

    @field:ColumnInfo(name = "repository")
    val repository: Int?,

    @field:ColumnInfo(name = "followers")
    val followers: Int?,

    @field:ColumnInfo(name = "following")
    val following: Int?,

    @field:ColumnInfo(name= "favorited")
    var isFavorited: Boolean?

    )