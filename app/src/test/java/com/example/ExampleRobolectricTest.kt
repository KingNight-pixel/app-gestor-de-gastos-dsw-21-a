package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppDatabase
import com.example.data.Gasto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    private lateinit var database: AppDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = AppDatabase.getDatabase(context)
    }

    @After
    fun tearDown() = runBlocking {
        database.gastoDao().deleteAllGastos()
    }

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("GestorGastos", appName)
    }

    @Test
    fun `insert and calculate total gasto`() = runBlocking {
        val dao = database.gastoDao()
        dao.insertGasto(
            Gasto(
                descripcion = "Supermercado",
                monto = 50.0,
                categoria = "Compras",
                fecha = "01/10/2026"
            )
        )
        dao.insertGasto(
            Gasto(
                descripcion = "Almuerzo",
                monto = 25.5,
                categoria = "Comida",
                fecha = "01/10/2026"
            )
        )

        val total = dao.getTotalGastado().first() ?: 0.0
        assertEquals(75.5, total, 0.01)

        val all = dao.getAllGastos().first()
        assertEquals(2, all.size)
    }
}
