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
    // Errores de validacion
    var errorInventario = mutableStateOf<String?>(null)
    var errorUbicacion = mutableStateOf<String?>(null)
    var errorDescripcion = mutableStateOf<String?>(null)
    // Lista de fallas
    val fallas = repo.obtenerFallas().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    /**
     * Valida los campos de la pantalla
     * @return True si los campos son validos, false en caso contrario
     */
    fun validar(): Boolean {
        // Valida los campos
        var esValido = true
        // Valida el numero de inventario
        if (numeroInventario.value.isBlank()) {
            errorInventario.value = "Ingresa el numero de inventario"
            esValido = false
        } else {
            errorInventario.value = null
        }
        // Valida la ubicacion
        if (ubicacion.value.isBlank()) {
            errorUbicacion.value = "Ingresa la ubicacion"
            esValido = false
        } else {
            errorUbicacion.value = null
        }
        // Valida la descripcion
        if (descripcion.value.isBlank()) {
            errorDescripcion.value = "Ingresa la descripcion de la falla"
            esValido = false
        } else {
            errorDescripcion.value = null
        }
        // Valida la foto
        return esValido
    }
    /**
     * Guarda una falla en la base de datos
     * @return True si la falla se guardo correctamente, false en caso contrario
     */
    fun guardar() : Boolean{
        // Valida los campos
        if (!validar()) return false
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
        // Limpia los campos
        limpiarCampo()
        return true
    }

    /**
     * Limpia los campos de la pantalla
     */
    fun limpiarCampo(){
        numeroInventario.value = ""
        ubicacion.value = ""
        descripcion.value = ""
        fotoUri.value = ""
        errorInventario.value = null
        errorUbicacion.value = null
        errorDescripcion.value = null
    }


}