package com.example.gestorgastos.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/** Categorias disponibles en toda la app */
val CATEGORIAS = listOf("Comida", "Transporte", "Servicios", "Ocio", "Salud", "Otros")

/** Icono y color distintivo de cada categoria */
data class CategoriaVisual(val icono: ImageVector, val color: Color)

fun visualDeCategoria(categoria: String): CategoriaVisual = when (categoria) {
    "Comida" -> CategoriaVisual(Icons.Default.Restaurant, Color(0xFFEF6C00))
    "Transporte" -> CategoriaVisual(Icons.Default.DirectionsCar, Color(0xFF1565C0))
    "Servicios" -> CategoriaVisual(Icons.Default.Bolt, Color(0xFF6A1B9A))
    "Ocio" -> CategoriaVisual(Icons.Default.SportsEsports, Color(0xFF00838F))
    "Salud" -> CategoriaVisual(Icons.Default.Favorite, Color(0xFFC62828))
    else -> CategoriaVisual(Icons.Default.Category, Color(0xFF546E7A))
}
