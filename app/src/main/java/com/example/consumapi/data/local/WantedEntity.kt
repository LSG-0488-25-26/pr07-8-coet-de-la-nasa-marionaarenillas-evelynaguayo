package com.example.consumapi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// "taula" de Room on guardem camps simples
@Entity(tableName = "captured")
data class WantedEntity(
    @PrimaryKey val uid: String,
    val title: String,
    val imageUrl: String?,
    val rewardText: String?,
    val capturedAt: Long = System.currentTimeMillis()
)