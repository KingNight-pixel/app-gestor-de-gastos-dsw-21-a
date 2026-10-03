package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GastoDao {
    @Query("SELECT * FROM gastos ORDER BY timestamp DESC")
    fun getAllGastos(): Flow<List<Gasto>>

    @Query("SELECT SUM(monto) FROM gastos")
    fun getTotalGastado(): Flow<Double?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGasto(gasto: Gasto): Long

    @Update
    suspend fun updateGasto(gasto: Gasto)

    @Delete
    suspend fun deleteGasto(gasto: Gasto)

    @Query("DELETE FROM gastos WHERE id = :id")
    suspend fun deleteGastoById(id: Long)

    @Query("DELETE FROM gastos")
    suspend fun deleteAllGastos()
}
