package com.example.consumapi.data.local

import android.content.Context
import androidx.room.Room
import com.example.consumapi.data.local.AppDatabase

// Crea i proporciona una única instància de la base de dades Room (Singleton)
object DatabaseProvider {
    @Volatile private var INSTANCE: AppDatabase? = null

    fun getDb(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val db = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "consumapi.db"
            ).build()
            INSTANCE = db
            db
        }
    }
}