package com.admaja.myfirstsubmission.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Users(
    var name: String,
    var username: String,
    var location: String,
    var repository: String,
    var company: String,
    var followers: String,
    var following: String,
    var photo: Int
): Parcelable
