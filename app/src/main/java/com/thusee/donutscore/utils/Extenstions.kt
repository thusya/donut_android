package com.thusee.donutscore.utils

import android.content.Context
import com.thusee.donutscore.R
import retrofit2.HttpException
import java.net.UnknownHostException

fun Context.handleError(e: Throwable?): String {
    if (e is HttpException) {
        val body = e.response()?.errorBody()
        val errorMessage = body?.string()

        if (errorMessage!!.isNotEmpty()) {
            // Need to throw the error msg according to the API Response then,
            // For Sample added 401 code else pass the errorMessage from the API
            return when (e.code()) {
                401 -> {
                    getString(R.string.unauthorized_ex)
                }
                else -> {
                    errorMessage
                }
            }
        }
        return getString(R.string.failed_error)
    } else if (e is UnknownHostException) {
        return getString(R.string.network_not_available)
    }

    return getString(R.string.failed_error)
}