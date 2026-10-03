package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.screens.AddEditGastoScreen
import com.example.ui.screens.MainScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.GestorGastosTheme
import com.example.viewmodel.AppScreen
import com.example.viewmodel.GastoViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: GastoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestorGastosTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GestorGastosApp(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun GestorGastosApp(viewModel: GastoViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val gastos by viewModel.allGastos.collectAsStateWithLifecycle()
    val totalGastado by viewModel.totalGastado.collectAsStateWithLifecycle()
    val currencySymbol by viewModel.currencySymbol.collectAsStateWithLifecycle()
    val selectedGastoForEdit by viewModel.selectedGastoForEdit.collectAsStateWithLifecycle()

    when (currentScreen) {
        AppScreen.HOME -> {
            MainScreen(
                gastos = gastos,
                totalGastado = totalGastado,
                currencySymbol = currencySymbol,
                onAddClick = { viewModel.navigateTo(AppScreen.ADD_GASTO) },
                onSettingsClick = { viewModel.navigateTo(AppScreen.SETTINGS) },
                onEditGasto = { gasto -> viewModel.selectGastoForEdit(gasto) },
                onDeleteGasto = { gasto -> viewModel.deleteGasto(gasto) }
            )
        }
        AppScreen.ADD_GASTO -> {
            AddEditGastoScreen(
                gastoToEdit = null,
                currencySymbol = currencySymbol,
                onSave = { desc, monto, cat, fecha ->
                    viewModel.addGasto(desc, monto, cat, fecha)
                },
                onUpdate = { /* Not applicable in add mode */ },
                onDelete = { /* Not applicable in add mode */ },
                onBack = { viewModel.navigateTo(AppScreen.HOME) }
            )
        }
        AppScreen.EDIT_GASTO -> {
            AddEditGastoScreen(
                gastoToEdit = selectedGastoForEdit,
                currencySymbol = currencySymbol,
                onSave = { _, _, _, _ -> },
                onUpdate = { gasto -> viewModel.updateGasto(gasto) },
                onDelete = { gasto -> viewModel.deleteGasto(gasto) },
                onBack = { viewModel.navigateTo(AppScreen.HOME) }
            )
        }
        AppScreen.SETTINGS -> {
            SettingsScreen(
                currentCurrency = currencySymbol,
                onCurrencyChange = { symbol -> viewModel.setCurrency(symbol) },
                onDeleteAllGastos = { viewModel.deleteAllGastos() },
                onBack = { viewModel.navigateTo(AppScreen.HOME) }
            )
        }
    }
}
