package com.example.consumapi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

// BBDD Room que connecta les entitats amb els DAOs
@Database(entities = [WantedEntity::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun wantedDao(): WantedDao
}