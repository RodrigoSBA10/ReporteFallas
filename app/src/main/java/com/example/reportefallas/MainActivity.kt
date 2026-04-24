package com.example.reportefallas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.reportefallas.data.local.database.AppDatabase
import com.example.reportefallas.data.repositorios.FallaRepository
import com.example.reportefallas.ui.navegacion.Nav
import com.example.reportefallas.viewmodel.FallaViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Actividad principal de la aplicación
 * @constructor Crea una nueva instancia de la actividad
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configuración de la interfaz de usuario
        setContent {
            Nav()
        }
    }
}