package com.admaja.myfirstsubmission.data


sealed class Result<out R> private constructor(){
    data class Succes<out T>(val data: T): Result<T>()
    data class Error(val error: String): Result<Nothing>()
    object Loading: Result<Nothing>()
}