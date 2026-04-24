package com.example.reportefallas.ui.screens

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.reportefallas.viewmodel.FallaViewModel
import java.io.File

/**
 * Pantalla de registro de fallas
 * @param viewModel El viewModel de la aplicación
 * @param nav El controlador de navegación
 */
@Composable
fun RegistroScreen(viewModel: FallaViewModel = hiltViewModel(), nav: NavController) {
    // Contexto de la aplicación
    val context = LocalContext.current
    // Archivo de imagen
    val imageFile = remember {
        // Ruta de la imagen
        File(
            // Directorio de la aplicación
            context.cacheDir,
            // Nombre del archivo
            "foto_${System.currentTimeMillis()}.jpg"
        )
    }
    // Uri de la imagen
    val uri = remember {
        // Uri de la imagen
        FileProvider.getUriForFile(
            // Contexto de la aplicación
            context,
            // Autoridad del proveedor de archivos
            "${context.packageName}.provider",
            // Archivo de imagen
            imageFile
        )
    }

    // Launcher para la camarra
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { // Si la foto se tomó correctamente
        success ->
        if (success) {
            // Uri de la imagen
            viewModel.fotoUri.value = uri.toString()
        }
    }
    // Launcher para la galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        // Si la uri no es nula
        uri?.let {

            // Permisos de lectura
            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            // Uri de la imagen
            viewModel.fotoUri.value = it.toString()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 40.dp),
    ) {

        Text(
            text = "Registrar Falla",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            if (viewModel.fotoUri.value.isNotEmpty()) {
                AsyncImage(
                    model = viewModel.fotoUri.value,
                    contentDescription = "Vista previa",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text("Sin imagen", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            // Numero de inventario
            value = viewModel.numeroInventario.value,
            // Cuando el valor cambia
            onValueChange = { viewModel.numeroInventario.value = it
                            viewModel.errorInventario.value = null},
            // Etiqueta
            label = { Text("Número de inventario") },
            // Si hay error
            isError = viewModel.errorInventario.value != null,
            // Mensaje de error
            supportingText = {
                // Si hay error
                if (viewModel.errorInventario.value != null) {
                    // Mensaje de error
                    Text(viewModel.errorInventario.value!!)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = viewModel.ubicacion.value,
            onValueChange = { viewModel.ubicacion.value = it
                            viewModel.errorUbicacion.value = null },
            label = { Text("Ubicación") },
            isError = viewModel.errorUbicacion.value != null,
            supportingText = {
                if (viewModel.errorUbicacion.value != null) {
                    Text(viewModel.errorUbicacion.value!!)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = viewModel.descripcion.value,
            onValueChange = { viewModel.descripcion.value = it
                            viewModel.errorDescripcion.value = null },
            label = { Text("Descripción") },
            isError = viewModel.errorDescripcion.value != null,
            supportingText = {
                if (viewModel.errorDescripcion.value != null) {
                    Text(viewModel.errorDescripcion.value!!)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            maxLines = 4
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { cameraLauncher.launch(uri) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Cámara")
            }

            OutlinedButton(
                onClick = { galleryLauncher.launch(arrayOf("image/*")) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Galería")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val guardado = viewModel.guardar()
                if (guardado){
                    nav.navigate("lista")
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Guardar Falla")
        }
    }
}