package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gastos")
data class Gasto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val descripcion: String,
    val monto: Double,
    val categoria: String,
    val fecha: String,
    val timestamp: Long = System.currentTimeMillis()
)
