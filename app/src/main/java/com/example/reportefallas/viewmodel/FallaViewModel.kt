package com.example.reportefallas.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reportefallas.data.local.entidades.Falla
import com.example.reportefallas.data.repositorios.FallaRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*

/**
 * ViewModel de la aplicación
 * @param repo El repositorio de fallas
 */
class FallaViewModel(private val repo: FallaRepository) : ViewModel() {
    // Numero de inventario de la falla
    var numeroInventario = mutableStateOf("")
    // Ubicacion de la falla
    var ubicacion = mutableStateOf("")
    // Descripcion de la falla
    var descripcion = mutableStateOf("")
    // Uri de la foto de la falla
    var fotoUri = mutableStateOf("")
    // Lista de fallas
    val fallas = repo.obtenerFallas().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )
    // Guarda una falla en la base de datos
    fun guardar() {
        // Falla a guardar
        val falla = Falla(
            numeroInventario = numeroInventario.value,
            ubicacion = ubicacion.value,
            descripcion = descripcion.value,
            fotoUri = fotoUri.value
        )
        // Guarda la falla en la base de datos
        viewModelScope.launch {
            repo.guardar(falla)
        }
    }
}