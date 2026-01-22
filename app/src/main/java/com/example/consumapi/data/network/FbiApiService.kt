package com.example.consumapi.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface FbiApiService {

    @GET("wanted/v1/list")
    suspend fun getWantedList(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20
    ): WantedResponse
}