package com.example.exchangegraph.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.exchangegraph.viewmodel.CurrencyViewModel
import com.example.exchangegraph.ui.components.ExchangeRateGraph
import com.example.exchangegraph.ui.components.DropdownMenuComponent
import com.example.exchangegraph.ui.components.DatePickerComponent
import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.Text

@Composable
fun CurrencyConverterScreen(viewModel: CurrencyViewModel) {
    val exchangeRates by viewModel.filteredExchangeRates.collectAsState()
    val selectedCurrency by viewModel.selectedCurrency.collectAsState()
    val selectedStartDate by viewModel.selectedStartDate.collectAsState()
    val selectedEndDate by viewModel.selectedEndDate.collectAsState()

    Log.d("CurrencyScreen", "🟢 Divisa seleccionada: $selectedCurrency")
    Log.d("CurrencyScreen", "📆 Rango de Fechas: $selectedStartDate → $selectedEndDate")
    Log.d("CurrencyScreen", "🔄 Datos en pantalla: $exchangeRates")

    Column(modifier = Modifier.padding(16.dp)) {
        DropdownMenuComponent(
            currencyList = viewModel.getAllCurrencies(),
            selectedCurrency = selectedCurrency,
            updateSelectedCurrency = { newCurrency ->
                Log.d("CurrencyScreen", "🔄 Cambio de divisa a: $newCurrency")
                viewModel.setSelectedCurrency(newCurrency)
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        DatePickerComponent("Seleccionar Fecha Inicial", selectedStartDate) { date ->
            viewModel.setStartDate(date)
        }

        Spacer(modifier = Modifier.height(10.dp))

        DatePickerComponent("Seleccionar Fecha Final", selectedEndDate) { date ->
            viewModel.setEndDate(date)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            Log.d("CurrencyScreen", "🔄 Botón presionado: actualizar gráfica")
            viewModel.fetchExchangeRates()
        }) {
            Text("Actualizar Gráfica")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Gráfica de Tasas de Cambio")
        ExchangeRateGraph(exchangeRates)
    }
}
