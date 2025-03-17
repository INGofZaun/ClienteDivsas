package com.example.exchangegraph.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.exchangegraph.viewmodel.CurrencyViewModel
import com.example.exchangegraph.ui.components.ExchangeRateGraph
import com.example.exchangegraph.ui.components.DropdownMenuComponent
import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.Text

@Composable
fun CurrencyConverterScreen(viewModel: CurrencyViewModel, startDate: String, endDate: String) {
    val exchangeRates by viewModel.filteredExchangeRates.collectAsState()
    val selectedCurrency by viewModel.selectedCurrency.collectAsState()
    val currencyList = remember { viewModel.getAllCurrencies() } // ✅ Cargar lista de divisas

    Log.d("CurrencyScreen", "🟢 Divisa seleccionada: $selectedCurrency")
    Log.d("CurrencyScreen", "🔄 Datos en pantalla: $exchangeRates")

    Column(modifier = Modifier.padding(16.dp)) {
        DropdownMenuComponent(
            currencyList = currencyList, // ✅ Se pasa correctamente la lista de divisas
            selectedCurrency = selectedCurrency,
            updateSelectedCurrency = { newCurrency ->
                Log.d("CurrencyScreen", "🔄 Cambio de divisa a: $newCurrency")
                viewModel.setSelectedCurrency(newCurrency, startDate, endDate)
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("Gráfica de Tasas de Cambio")
        ExchangeRateGraph(exchangeRates)
    }
}
