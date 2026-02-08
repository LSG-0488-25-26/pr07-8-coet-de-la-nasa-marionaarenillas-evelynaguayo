package com.example.consumapi.data.repository

import com.example.consumapi.data.local.WantedDao
import com.example.consumapi.data.local.WantedEntity
import com.example.consumapi.data.model.WantedPerson
import com.example.consumapi.data.model.WantedResponse
import com.example.consumapi.data.network.RetrofitClient
import kotlinx.coroutines.flow.Flow

// Centralitza l'accés a dades remotes (API) i locals (ROOM)

class FBIRepository(
    private val dao: WantedDao
) {
    private val apiService = RetrofitClient.apiService

    // API (Retrofit)
    suspend fun getWantedList(page: Int = 1): Result<WantedResponse> {
        return try {
            val response = apiService.getWantedList(page = page)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ROOM (capturats)
    fun getCaptured(): Flow<List<WantedEntity>> = dao.getCaptured()

    suspend fun isCaptured(uid: String): Boolean = dao.isCaptured(uid)

    suspend fun capture(person: WantedPerson) {
        val uid = person.uid ?: return
        dao.insert(
            WantedEntity(
                uid = uid,
                title = person.title ?: "Sin nombre",
                imageUrl = person.images?.firstOrNull()?.thumb
                    ?: person.images?.firstOrNull()?.original,
                rewardText = person.rewardText
            )
        )
    }

    suspend fun uncapture(uid: String) {
        dao.delete(uid)
    }

    suspend fun toggleCaptured(person: WantedPerson) {
        val uid = person.uid ?: return
        if (dao.isCaptured(uid)) dao.delete(uid) else capture(person)
    }
}
