package com.example.consumapi.data.local

import androidx.room.Database

// BBDD Room que connecta les entitats amb els DAOs
@Database(entities = [WantedEntity::class], version = 1)

abstract class AppDatabase {
    abstract fun wantedDao(): WantedDao
}