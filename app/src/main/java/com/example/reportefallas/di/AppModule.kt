package com.example.reportefallas.di

import android.content.Context
import androidx.room.Room
import com.example.reportefallas.data.local.daos.FallaDao
import com.example.reportefallas.data.local.database.AppDatabase
import com.example.reportefallas.data.repositorios.FallaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo de inyección de dependencias
 */
@Module
// Componente singleton
@InstallIn(SingletonComponent::class)
object AppModule{
    /**
     * Proporciona una instancia de la base de datos
     * @param context El contexto de la aplicación
     * @return La instancia de la base de datos
     */
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase{
        // Construye la base de datos
        return Room.databaseBuilder(
            // Contexto de la aplicación
            context,
            // Clase de la base de datos
            AppDatabase::class.java,
            // Nombre de la base de datos
            "fallas_db"
        ).build()
    }

    /**
     * Proporciona una instancia del dao de fallas
     * @param db La instancia de la base de datos
     * @return La instancia del dao de fallas
     */
    @Provides
    fun provideFallaDao(db: AppDatabase) = db.fallaDao()

    /**
     * Proporciona una instancia del repositorio de fallas
     * @param dao La instancia del dao de fallas
     * @return La instancia del repositorio de fallas
     */
    @Provides
    @Singleton
    fun provideFallaRepository(dao: FallaDao) = FallaRepository(dao)
}
