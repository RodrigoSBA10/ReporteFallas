package com.example.reportefallas.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.reportefallas.data.local.daos.FallaDao
import com.example.reportefallas.data.local.entidades.Falla

/**
 * Base de datos de la aplicación
 */
@Database(entities = [Falla::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Dao para operaciones con la tabla de fallas
     * @return El dao de fallas
     */
    abstract fun fallaDao(): FallaDao
}