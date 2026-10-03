package com.example.ui.screens

import android.app.DatePickerDialog
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Gasto
import com.example.ui.theme.AppBackground
import com.example.ui.theme.TextLabel
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WineCardBg
import com.example.ui.theme.WineCardBorder
import com.example.ui.theme.WinePrimary
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private val DEFAULT_CATEGORIES = listOf(
    "Comida",
    "Transporte",
    "Compras",
    "Servicios",
    "Hogar",
    "Entretenimiento",
    "Salud",
    "Educación",
    "Otros"
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddEditGastoScreen(
    gastoToEdit: Gasto?,
    currencySymbol: String,
    onSave: (descripcion: String, monto: Double, categoria: String, fecha: String) -> Unit,
    onUpdate: (Gasto) -> Unit,
    onDelete: (Gasto) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val context = LocalContext.current
    val isEditing = gastoToEdit != null

    var descripcion by remember { mutableStateOf(gastoToEdit?.descripcion ?: "") }
    var montoText by remember {
        mutableStateOf(
            if (gastoToEdit != null) {
                if (gastoToEdit.monto % 1.0 == 0.0) {
                    gastoToEdit.monto.toInt().toString()
                } else {
                    String.format(Locale.US, "%.2f", gastoToEdit.monto)
                }
            } else ""
        )
    }
    var categoria by remember { mutableStateOf(gastoToEdit?.categoria ?: "Comida") }

    val currentDateFormatted = remember {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        sdf.format(Date())
    }
    var fecha by remember { mutableStateOf(gastoToEdit?.fecha ?: currentDateFormatted) }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    // DatePicker Dialog helper
    val calendar = remember { Calendar.getInstance() }
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val formatted = String.format(Locale.US, "%02d/%02d/%04d", dayOfMonth, month + 1, year)
                fecha = formatted
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppBackground)
            .imePadding()
    ) {
        // Wine Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(WinePrimary)
                .statusBarsPadding()
                .height(64.dp)
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.testTag("back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Regresar",
                        tint = Color.White
                    )
                }

                Text(
                    text = if (isEditing) "Editar gasto" else "Agregar gasto",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.testTag("form_title_text")
                )

                if (isEditing) {
                    IconButton(
                        onClick = { onDelete(gastoToEdit) },
                        modifier = Modifier.testTag("delete_topbar_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar gasto",
                            tint = Color.White
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.size(48.dp))
                }
            }
        }

        // Form Body
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Descripción field
            Column {
                Text(
                    text = "Descripción",
                    color = TextLabel,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = {
                        descripcion = it
                        errorMessage = null
                    },
                    placeholder = { Text("Ej. Almuerzo, Supermercado, Transporte") },
                    singleLine = true,
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = WinePrimary,
                        unfocusedBorderColor = WineCardBorder,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("description_input")
                )
            }

            // Monto field
            Column {
                Text(
                    text = "Monto",
                    color = TextLabel,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = montoText,
                    onValueChange = { input ->
                        val filtered = input.filter { it.isDigit() || it == '.' }
                        if (filtered.count { it == '.' } <= 1) {
                            montoText = filtered
                            errorMessage = null
                        }
                    },
                    placeholder = { Text("0.00") },
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    prefix = {
                        Text(
                            text = "$currencySymbol ",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = WinePrimary,
                        unfocusedBorderColor = WineCardBorder,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("amount_input")
                )
            }

            // Categoría field
            Column {
                Text(
                    text = "Categoría",
                    color = TextLabel,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DEFAULT_CATEGORIES.forEach { cat ->
                        val isSelected = categoria.equals(cat, ignoreCase = true)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) WinePrimary else WineCardBg)
                                .clickable {
                                    categoria = cat
                                    errorMessage = null
                                }
                                .padding(horizontal = 14.dp, vertical = 9.dp)
                                .testTag("category_chip_$cat")
                        ) {
                            Text(
                                text = cat,
                                color = if (isSelected) Color.White else TextPrimary,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Fecha field
            Column {
                Text(
                    text = "Fecha",
                    color = TextLabel,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(WineCardBorder)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { datePickerDialog.show() }
                        .testTag("date_picker_button")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = fecha,
                            color = TextPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Seleccionar fecha",
                            tint = WinePrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Error Message
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = WinePrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.testTag("error_message_text")
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action Buttons
            Button(
                onClick = {
                    val parsedMonto = montoText.toDoubleOrNull()
                    when {
                        descripcion.isBlank() -> {
                            errorMessage = "Por favor ingresa una descripción."
                        }
                        parsedMonto == null || parsedMonto <= 0 -> {
                            errorMessage = "Por favor ingresa un monto válido mayor a 0."
                        }
                        categoria.isBlank() -> {
                            errorMessage = "Por favor selecciona una categoría."
                        }
                        else -> {
                            if (isEditing && gastoToEdit != null) {
                                onUpdate(
                                    gastoToEdit.copy(
                                        descripcion = descripcion.trim(),
                                        monto = parsedMonto,
                                        categoria = categoria.trim(),
                                        fecha = fecha.trim()
                                    )
                                )
                            } else {
                                onSave(
                                    descripcion.trim(),
                                    parsedMonto,
                                    categoria.trim(),
                                    fecha.trim()
                                )
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = WinePrimary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("save_gasto_button")
            ) {
                Text(
                    text = if (isEditing) "Actualizar gasto" else "Guardar gasto",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            OutlinedButton(
                onClick = onBack,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = TextSecondary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("cancel_button")
            ) {
                Text(text = "Cancelar", fontSize = 15.sp)
            }

            if (isEditing) {
                TextButton(
                    onClick = { onDelete(gastoToEdit) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("delete_bottom_button")
                ) {
                    Text(
                        text = "Eliminar este gasto",
                        color = WinePrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.navigationBarsPadding())
        }
    }
}
