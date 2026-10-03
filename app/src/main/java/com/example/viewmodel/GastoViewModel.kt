package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.Gasto
import com.example.data.GastoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppScreen {
    HOME,
    ADD_GASTO,
    EDIT_GASTO,
    SETTINGS
}

class GastoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GastoRepository

    val allGastos: StateFlow<List<Gasto>>
    val totalGastado: StateFlow<Double>

    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _selectedGastoForEdit = MutableStateFlow<Gasto?>(null)
    val selectedGastoForEdit: StateFlow<Gasto?> = _selectedGastoForEdit.asStateFlow()

    private val _currencySymbol = MutableStateFlow("$")
    val currencySymbol: StateFlow<String> = _currencySymbol.asStateFlow()

    init {
        val database = AppDatabase.getDatabase(application)
        repository = GastoRepository(database.gastoDao())

        allGastos = repository.allGastos.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        totalGastado = repository.totalGastado
            .map { it ?: 0.0 }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = 0.0
            )
    }

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun selectGastoForEdit(gasto: Gasto) {
        _selectedGastoForEdit.value = gasto
        _currentScreen.value = AppScreen.EDIT_GASTO
    }

    fun addGasto(descripcion: String, monto: Double, categoria: String, fecha: String) {
        viewModelScope.launch {
            val nuevoGasto = Gasto(
                descripcion = descripcion.trim(),
                monto = monto,
                categoria = categoria.trim(),
                fecha = fecha.trim()
            )
            repository.insertGasto(nuevoGasto)
            _currentScreen.value = AppScreen.HOME
        }
    }

    fun updateGasto(gasto: Gasto) {
        viewModelScope.launch {
            repository.updateGasto(gasto)
            _selectedGastoForEdit.value = null
            _currentScreen.value = AppScreen.HOME
        }
    }

    fun deleteGasto(gasto: Gasto) {
        viewModelScope.launch {
            repository.deleteGasto(gasto)
            if (_selectedGastoForEdit.value?.id == gasto.id) {
                _selectedGastoForEdit.value = null
                _currentScreen.value = AppScreen.HOME
            }
        }
    }

    fun deleteAllGastos() {
        viewModelScope.launch {
            repository.deleteAllGastos()
            _selectedGastoForEdit.value = null
            _currentScreen.value = AppScreen.HOME
        }
    }

    fun setCurrency(symbol: String) {
        _currencySymbol.value = symbol
    }
}
