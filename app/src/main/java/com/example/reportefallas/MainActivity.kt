package com.example.reportefallas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.reportefallas.data.local.database.AppDatabase
import com.example.reportefallas.data.repositorios.FallaRepository
import com.example.reportefallas.ui.navegacion.Nav
import com.example.reportefallas.viewmodel.FallaViewModel

/**
 * Actividad principal de la aplicación
 */
class MainActivity : ComponentActivity() {
    // Base de datos de la aplicación
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configuración de la base de datos
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "fallas_db"
        ).build()
        // Configuración del repositorio
        val repo = FallaRepository(db.fallaDao())
        // Configuración del ViewModel
        val viewModel = FallaViewModel(repo)
        // Configuración de la interfaz de usuario
        setContent {
            Nav(viewModel)
        }
    }
}