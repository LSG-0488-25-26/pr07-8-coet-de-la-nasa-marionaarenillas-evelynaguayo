package com.example.consumapi.data.repository

import com.example.consumapi.data.model.WantedResponse
import com.example.consumapi.data.network.RetrofitClient

class WantedRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun getWantedList(page: Int = 1): Result<WantedResponse> {
        return try {
            val response = apiService.getWantedList(page = page)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}