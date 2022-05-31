package com.blinxio.technicalassignment.network

import retrofit2.Response

fun <T> Response<T>.unwrapResponse(): ApiResult<T> =
    if (isSuccessful) {
        val data = body()
        if (data != null) {
            ApiResult.Success(data)
        } else {
            ApiResult.Error(NullPointerException())
        }
    } else {
        ApiResult.Error(NullPointerException())
    }