package com.example.reportefallas.data.local.entidades
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad de falla
 */
@Entity(tableName = "fallas")
data class Falla(
    // Clave primaria autoincremental
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val numeroInventario: String,
    val ubicacion: String,
    val descripcion: String,
    val fotoUri: String
)
