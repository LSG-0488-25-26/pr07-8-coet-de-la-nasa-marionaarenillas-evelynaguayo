package com.example.consumapi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// Defineix les operacions que es poden fer sobre la BBDD
@Dao
interface WantedDao {

    @Query("SELECT * FROM captured ORDER BY capturedAt DESC")
    fun getCaptured(): Flow<List<WantedEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM captured WHERE uid = :uid)")
    suspend fun isCaptured(uid: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: WantedEntity)

    @Query("DELETE FROM captured WHERE uid = :uid")
    suspend fun delete(uid: String)
}
