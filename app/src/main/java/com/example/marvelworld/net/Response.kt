package com.example.marvelworld.net

import com.example.marvelworld.ext.empty

sealed class Response<out T : Any> {

    data class Success<out T : Any>(val data: T) : Response<T>()

    data class Error(val code: Int, val message: String) : Response<Nothing>() {
        override fun toString() = "$code: $message"
    }

    data class Exception(val exception: kotlin.Exception) : Response<Nothing>()

    data class Loading(val string: String = String.empty) : Response<Nothing>()
}

fun <T : Any, R> Response<T>.doOnSuccess(action: (T) -> R): Response<T> = when (this) {
    is Response.Success -> {
        action.invoke(data)
        this
    }
    else -> this
}

fun <T : Any> Response<T>.toResult() =
    if (this is Response.Success) Response.Success(data) else this