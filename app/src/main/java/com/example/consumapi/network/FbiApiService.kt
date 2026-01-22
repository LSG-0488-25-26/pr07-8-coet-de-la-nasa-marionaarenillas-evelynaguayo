package com.example.consumapi.network

import com.example.consumapi.data.model.WantedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FbiApiService {

    @GET("wanted/v1/list")
    suspend fun getWantedList(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20
    ): WantedResponse
}