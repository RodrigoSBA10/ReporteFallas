package com.example.reportefallas.data.repositorios

import com.example.reportefallas.data.local.daos.FallaDao
import com.example.reportefallas.data.local.entidades.Falla

/**
 * Repositorio de fallas
 * @param dao El dao de fallas
 */
class FallaRepository(private val dao: FallaDao) {
    /**
     * Guarda una falla en la base de datos
     * @param falla La falla a guardar
     */
    suspend fun guardar(falla: Falla) = dao.insertar(falla)

    /**
     * Obtiene todas las fallas de la base de datos
     * @return Un flujo de listas de fallas
     */
    fun obtenerFallas() = dao.getFallas()
}