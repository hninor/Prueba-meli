package com.hninor.pruebameli.api

import com.hninor.pruebameli.api.entry.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MeliService {

    @GET("sites/MCO/search")
    suspend fun search(@Query("q") query: String): Response<SearchResult>
}
