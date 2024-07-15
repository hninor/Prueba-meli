package com.hninor.pruebameli.data

import com.hninor.pruebameli.api.ApiResponseStatus
import com.hninor.pruebameli.api.entry.SearchResult
import com.hninor.pruebameli.api.reqApi
import java.net.UnknownHostException
import javax.inject.Inject

class MeliRemoteRepository @Inject constructor() {


    suspend fun fetchResults(query: String): ApiResponseStatus<SearchResult> {
        return try {
            val response = reqApi.search(query)

            if (response.isSuccessful) {
                if (response.body() == null) {
                    ApiResponseStatus.Error("Error no esperado: ${response.errorBody()}")
                } else {
                    ApiResponseStatus.Success(response.body()!!)
                }
            } else {
                ApiResponseStatus.Error("Http code: $response.code()")
            }
        } catch (e: Exception) {
            if (e is UnknownHostException) {
                ApiResponseStatus.Error("Revise su conexión a internet")
            } else {
                ApiResponseStatus.Error(e.message ?: "")
            }
        }

    }
}
