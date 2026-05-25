package com.example.reportefallas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reportefallas.data.local.entidades.Falla
import com.example.reportefallas.data.repositorios.FallaRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FallaViewModel(private val repo: FallaRepository) : ViewModel() {

    // Estado privado mutable
    private val _numeroInventario = MutableStateFlow("")
    val numeroInventario: StateFlow<String> = _numeroInventario.asStateFlow()

    private val _ubicacion = MutableStateFlow("")
    val ubicacion: StateFlow<String> = _ubicacion.asStateFlow()

    private val _descripcion = MutableStateFlow("")
    val descripcion: StateFlow<String> = _descripcion.asStateFlow()

    private val _fotoUri = MutableStateFlow("")
    val fotoUri: StateFlow<String> = _fotoUri.asStateFlow()

    // Errores
    private val _errorInventario = MutableStateFlow<String?>(null)
    val errorInventario: StateFlow<String?> = _errorInventario.asStateFlow()

    private val _errorUbicacion = MutableStateFlow<String?>(null)
    val errorUbicacion: StateFlow<String?> = _errorUbicacion.asStateFlow()

    private val _errorDescripcion = MutableStateFlow<String?>(null)
    val errorDescripcion: StateFlow<String?> = _errorDescripcion.asStateFlow()

    // Lista de fallas
    val fallas = repo.obtenerFallas().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    /**
     * Actualiza el número de inventario
     * @param valor El nuevo número de inventario
     */
    fun actualizarNumeroInventario(valor: String) {
        _numeroInventario.value = valor
        if (valor.isNotBlank()) _errorInventario.value = null
    }

    /**
     * Actualiza la ubicación
     * @param valor La nueva ubicación
     */
    fun actualizarUbicacion(valor: String) {
        _ubicacion.value = valor
        if (valor.isNotBlank()) _errorUbicacion.value = null
    }

    /**
     * Actualiza la descripción
     * @param valor La nueva descripción
     */
    fun actualizarDescripcion(valor: String) {
        _descripcion.value = valor
        if (valor.isNotBlank()) _errorDescripcion.value = null
    }

    /**
     * Actualiza la uri de la foto
     * @param uri La nueva uri de la foto
     */
    fun actualizarFotoUri(uri: String) {
        _fotoUri.value = uri
    }

    /**
     * Guarda una falla en la base de datos
     * @return True si se guardó correctamente, false en caso contrario
     */
    fun guardar(): Boolean {
        if (!validar()) return false

        val falla = Falla(
            numeroInventario = _numeroInventario.value,
            ubicacion = _ubicacion.value,
            descripcion = _descripcion.value,
            fotoUri = _fotoUri.value
        )

        viewModelScope.launch {
            repo.guardar(falla)
            limpiarCampos()
        }
        return true
    }

    /**
     * Valida los campos de la pantalla
     * @return True si son válidos, false en caso contrario
     */
    private fun validar(): Boolean {
        var esValido = true

        if (_numeroInventario.value.isBlank()) {
            _errorInventario.value = "Ingresa el número de inventario"
            esValido = false
        }

        if (_ubicacion.value.isBlank()) {
            _errorUbicacion.value = "Ingresa la ubicación"
            esValido = false
        }

        if (_descripcion.value.isBlank()) {
            _errorDescripcion.value = "Ingresa la descripción de la falla"
            esValido = false
        }

        return esValido
    }

    /**
     * Limpia los campos de la pantalla
     */
    private fun limpiarCampos() {
        _numeroInventario.value = ""
        _ubicacion.value = ""
        _descripcion.value = ""
        _fotoUri.value = ""
        _errorInventario.value = null
        _errorUbicacion.value = null
        _errorDescripcion.value = null
    }
}