package com.example.reportefallas.ui.navegacion

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import com.example.reportefallas.ui.screens.ListaScreen
import com.example.reportefallas.ui.screens.RegistroScreen
import com.example.reportefallas.viewmodel.FallaViewModel

/**
 * Navegación de la aplicación
 * @param viewModel El viewModel de la aplicación
 */
@Composable
fun Nav() {
    // Controlador de navegación
    val navController = rememberNavController()
    /**
     * Estructura de navegación
     * @param navController El controlador de navegación
     * @param startDestination La pantalla de inicio
     */
    NavHost(navController, startDestination = "lista") {
        /**
         * Pantallas de la aplicación
         * @param route La ruta de la pantalla
         */
        composable("lista") {
            ListaScreen(nav = navController)
        }

        composable("registro") {
            RegistroScreen(nav = navController)
        }
    }
}