package com.admaja.myfirstsubmission.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
class UsersEntity (
    @field:ColumnInfo(name="id")
    @field:PrimaryKey
    val id: Int,

    @field:ColumnInfo(name="login")
    val login: String,

    @field:ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    @field:ColumnInfo(name = "favorited")
    var isFavorited: Boolean
)