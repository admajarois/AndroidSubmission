package com.admaja.myfirstsubmission.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "favorite_user")
@Parcelize
data class FavoriteEntity (
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: Int?,

    @field:ColumnInfo(name = "login")
    val login: String,

    @field:ColumnInfo(name = "avatarUrl")
    val avatarUrl: String
): Parcelable