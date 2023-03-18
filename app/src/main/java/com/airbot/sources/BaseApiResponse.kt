package com.airbot.sources

import android.util.Log
import com.airbot.utils.NetworkResult
import com.google.gson.Gson
import retrofit2.Response

abstract class BaseApiResponse {

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error(errorMessage)


    suspend fun <T> safeApiCall(apicall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apicall()
            Log.i("Error response", response.toString())
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Succcess(body)
                }
            }
            val gson = Gson()

            //Comprobar que mensaje nos devuelve en caso de error.

            /*val type = object : TypeToken<ApiError>() {}.type
            val errorResponse: ApiError? = gson.fromJson(response.errorBody()?.charStream(), type)*/
            return error("Mensaje de error")

        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }
}