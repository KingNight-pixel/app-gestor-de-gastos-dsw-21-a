package com.example.data

import kotlinx.coroutines.flow.Flow

class GastoRepository(private val gastoDao: GastoDao) {
    val allGastos: Flow<List<Gasto>> = gastoDao.getAllGastos()
    val totalGastado: Flow<Double?> = gastoDao.getTotalGastado()

    suspend fun insertGasto(gasto: Gasto): Long = gastoDao.insertGasto(gasto)

    suspend fun updateGasto(gasto: Gasto) = gastoDao.updateGasto(gasto)

    suspend fun deleteGasto(gasto: Gasto) = gastoDao.deleteGasto(gasto)

    suspend fun deleteGastoById(id: Long) = gastoDao.deleteGastoById(id)

    suspend fun deleteAllGastos() = gastoDao.deleteAllGastos()
}
