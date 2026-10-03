package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Gasto
import com.example.ui.theme.AppBackground
import com.example.ui.theme.TextLabel
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WineCardBg
import com.example.ui.theme.WineCardBorder
import com.example.ui.theme.WineLight
import com.example.ui.theme.WinePrimary
import java.util.Locale

@Composable
fun MainScreen(
    gastos: List<Gasto>,
    totalGastado: Double,
    currencySymbol: String,
    onAddClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onEditGasto: (Gasto) -> Unit,
    onDeleteGasto: (Gasto) -> Unit,
    modifier: Modifier = Modifier
) {
    var gastoToDelete by remember { mutableStateOf<Gasto?>(null) }

    val formattedTotal = String.format(Locale.US, "%.2f", totalGastado)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top Bar
            WineTopBar(
                title = "GestorGastos",
                onSettingsClick = onSettingsClick
            )

            // Central Area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (gastos.isEmpty()) {
                    // Empty State matching the reference image exactly
                    EmptyGastosPlaceholder()
                } else {
                    // List of registered expenses
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("gastos_list"),
                        contentPadding = PaddingValues(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 110.dp // Leave space for bottom total card and FAB
                        ),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(items = gastos, key = { it.id }) { gasto ->
                            GastoCardItem(
                                gasto = gasto,
                                currencySymbol = currencySymbol,
                                onClick = { onEditGasto(gasto) },
                                onDeleteClick = { gastoToDelete = gasto }
                            )
                        }
                    }
                }
            }
        }

        // Bottom Section: Total gastado card on the left, "+" button on the right
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Total Gastado Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = WineCardBg
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(82.dp)
                    .testTag("total_gastado_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Total gastado",
                        color = TextLabel,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.2.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "$currencySymbol$formattedTotal",
                        color = WinePrimary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.5).sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.testTag("total_amount_text")
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Circular Add Button
            FloatingActionButton(
                onClick = onAddClick,
                shape = CircleShape,
                containerColor = WinePrimary,
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 2.dp,
                    pressedElevation = 4.dp
                ),
                modifier = Modifier
                    .size(66.dp)
                    .testTag("add_expense_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar gasto",
                    tint = Color.White,
                    modifier = Modifier.size(34.dp)
                )
            }
        }
    }

    // Delete Confirmation Dialog
    if (gastoToDelete != null) {
        val gasto = gastoToDelete!!
        AlertDialog(
            onDismissRequest = { gastoToDelete = null },
            title = {
                Text(
                    text = "¿Eliminar gasto?",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "¿Deseas eliminar \"${gasto.descripcion}\" por $currencySymbol${String.format(Locale.US, "%.2f", gasto.monto)}?",
                    color = TextSecondary
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteGasto(gasto)
                        gastoToDelete = null
                    },
                    modifier = Modifier.testTag("confirm_delete_button")
                ) {
                    Text(text = "Eliminar", color = WinePrimary, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { gastoToDelete = null },
                    modifier = Modifier.testTag("cancel_delete_button")
                ) {
                    Text(text = "Cancelar", color = TextSecondary)
                }
            },
            shape = RoundedCornerShape(16.dp),
            containerColor = Color.White
        )
    }
}

@Composable
fun WineTopBar(
    title: String,
    onSettingsClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(WinePrimary)
            .statusBarsPadding()
            .height(64.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left wallet icon
            Icon(
                imageVector = Icons.Default.AccountBalanceWallet,
                contentDescription = "Logo billetera",
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .testTag("topbar_wallet_icon")
            )

            // Center Title
            Text(
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.testTag("app_title_text")
            )

            // Right settings icon
            IconButton(
                onClick = onSettingsClick,
                modifier = Modifier.testTag("settings_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Configuración",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}

@Composable
fun EmptyGastosPlaceholder() {
    Column(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .testTag("empty_state_container"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Centered wallet icon in light wine
        Icon(
            imageVector = Icons.Default.AccountBalanceWallet,
            contentDescription = "Sin gastos",
            tint = WineLight,
            modifier = Modifier
                .size(68.dp)
                .testTag("empty_wallet_icon")
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Aún no hay gastos.",
            color = TextPrimary,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            letterSpacing = (-0.2).sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Toca + para agregar uno.",
            color = TextSecondary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun GastoCardItem(
    gasto: Gasto,
    currencySymbol: String,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("gasto_item_${gasto.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category badge indicator
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(WineCardBg),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = gasto.categoria.take(1).uppercase(Locale.getDefault()),
                    color = WinePrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Description & metadata
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = gasto.descripcion,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${gasto.categoria} • ${gasto.fecha}",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Amount
            Text(
                text = "$currencySymbol${String.format(Locale.US, "%.2f", gasto.monto)}",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(4.dp))

            // Edit icon button
            IconButton(
                onClick = onClick,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar",
                    tint = TextSecondary,
                    modifier = Modifier.size(18.dp)
                )
            }

            // Delete icon button
            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = TextSecondary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
