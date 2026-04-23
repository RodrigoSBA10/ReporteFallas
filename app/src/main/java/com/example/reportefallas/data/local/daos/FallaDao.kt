package com.example.reportefallas.data.local.daos

import androidx.room.*
import com.example.reportefallas.data.local.entidades.Falla
import kotlinx.coroutines.flow.Flow

/**
 * Dao para operaciones con la tabla de fallas
 */
@Dao
interface FallaDao {
    /**
     * Guarda una falla en la base de datos
     * @param falla La falla a guardar
     */
    @Insert
    suspend fun insertar(falla: Falla)

    /**
     * Obtiene todas las fallas de la base de datos
     * @return Un flujo de listas de fallas
     */
    @Query("SELECT * FROM fallas")
    fun getFallas(): Flow<List<Falla>>
}