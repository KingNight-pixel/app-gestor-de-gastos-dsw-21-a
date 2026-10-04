package com.example.gestorgastos.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.gestorgastos.data.Gasto
import com.example.gestorgastos.ui.components.CATEGORIAS
import com.example.gestorgastos.viewmodel.GastoViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormGastoScreen(
    viewModel: GastoViewModel,
    gastoId: Int?,
    onVolver: () -> Unit
) {
    val formato = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply { isLenient = false } }

    var descripcion by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf(formato.format(Date())) }
    var menuAbierto by remember { mutableStateOf(false) }
    var intentoGuardar by remember { mutableStateOf(false) }

    // Si venimos a editar, cargamos el registro
    LaunchedEffect(gastoId) {
        if (gastoId != null) {
            viewModel.obtener(gastoId)?.let {
                descripcion = it.descripcion
                monto = it.monto.toString()
                categoria = it.categoria
                fecha = it.fecha
            }
        }
    }

    // ---- Validaciones ----
    val errorDescripcion = when {
        descripcion.isBlank() -> "La descripci\u00F3n es obligatoria"
        descripcion.trim().length < 3 -> "M\u00EDnimo 3 caracteres"
        else -> null
    }
    val montoNum = monto.toDoubleOrNull()
    val errorMonto = when {
        monto.isBlank() -> "El monto es obligatorio"
        montoNum == null -> "Ingresa un n\u00FAmero v\u00E1lido"
        montoNum <= 0 -> "El monto debe ser mayor que 0"
        else -> null
    }
    val errorCategoria = if (categoria.isBlank()) "Selecciona una categor\u00EDa" else null
    val errorFecha = try {
        formato.parse(fecha); null
    } catch (e: Exception) {
        "Formato v\u00E1lido: dd/MM/yyyy"
    }
    val hayErrores = listOf(errorDescripcion, errorMonto, errorCategoria, errorFecha).any { it != null }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (gastoId == null) "Nuevo gasto" else "Editar gasto",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripci\u00F3n") },
                isError = intentoGuardar && errorDescripcion != null,
                supportingText = { if (intentoGuardar) errorDescripcion?.let { Text(it) } },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = monto,
                onValueChange = { monto = it },
                label = { Text("Monto ($)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                isError = intentoGuardar && errorMonto != null,
                supportingText = { if (intentoGuardar) errorMonto?.let { Text(it) } },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            ExposedDropdownMenuBox(
                expanded = menuAbierto,
                onExpandedChange = { menuAbierto = !menuAbierto }
            ) {
                OutlinedTextField(
                    value = categoria,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categor\u00EDa") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuAbierto) },
                    isError = intentoGuardar && errorCategoria != null,
                    supportingText = { if (intentoGuardar) errorCategoria?.let { Text(it) } },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = menuAbierto, onDismissRequest = { menuAbierto = false }) {
                    CATEGORIAS.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                categoria = opcion
                                menuAbierto = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha (dd/MM/yyyy)") },
                isError = intentoGuardar && errorFecha != null,
                supportingText = { if (intentoGuardar) errorFecha?.let { Text(it) } },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(22.dp))

            Button(
                onClick = {
                    intentoGuardar = true
                    if (!hayErrores) {
                        viewModel.guardar(
                            Gasto(
                                id = gastoId ?: 0,
                                descripcion = descripcion.trim(),
                                monto = montoNum!!,
                                categoria = categoria,
                                fecha = fecha
                            )
                        )
                        onVolver()
                    }
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                Text(
                    if (gastoId == null) "Guardar gasto" else "Actualizar gasto",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
