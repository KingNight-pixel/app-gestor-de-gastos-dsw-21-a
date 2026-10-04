package com.example.gestorgastos

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.gestorgastos.navigation.AppNavigation
import com.example.gestorgastos.ui.theme.GestorGastosTheme
import com.example.gestorgastos.viewmodel.GastoViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: GastoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestorGastosTheme {
                GestorGastosApp(viewModel)
            }
        }
    }
}

// ===== Bienvenida (solo la primera vez) -> sistema =====
@Composable
fun GestorGastosApp(viewModel: GastoViewModel) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("gastos_prefs", Context.MODE_PRIVATE) }
    var bienvenidaVista by remember { mutableStateOf(prefs.getBoolean("bienvenida_vista", false)) }

    if (!bienvenidaVista) {
        PantallaBienvenida(
            onEntrar = {
                prefs.edit().putBoolean("bienvenida_vista", true).apply()
                bienvenidaVista = true
            }
        )
    } else {
        AppNavigation(viewModel)
    }
}
