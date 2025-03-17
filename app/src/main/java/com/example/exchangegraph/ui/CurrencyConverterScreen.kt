package com.example.exchangegraph.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.exchangegraph.viewmodel.CurrencyViewModel
import com.example.exchangegraph.ui.components.ExchangeRateGraph
import com.example.exchangegraph.ui.components.DropdownMenuComponent
import com.example.exchangegraph.ui.components.DatePickerComponent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import android.util.Log

@Composable
fun CurrencyConverterScreen(viewModel: CurrencyViewModel, initialStartDate: String, initialEndDate: String) {
    val exchangeRates by viewModel.filteredExchangeRates.collectAsState()
    val selectedCurrency by viewModel.selectedCurrency.collectAsState()
    val currencyList = remember { viewModel.getAllCurrencies() }

    var startDate by remember { mutableStateOf(initialStartDate) }
    var endDate by remember { mutableStateOf(initialEndDate) }

    Log.d("CurrencyScreen", "🟢 Divisa seleccionada: $selectedCurrency")
    Log.d("CurrencyScreen", "📅 Fecha inicio: $startDate, Fecha fin: $endDate")
    Log.d("CurrencyScreen", "🔄 Datos en pantalla: $exchangeRates")

    Column(modifier = Modifier.padding(16.dp)) {
        DropdownMenuComponent(
            currencyList = currencyList,
            selectedCurrency = selectedCurrency,
            updateSelectedCurrency = { newCurrency ->
                Log.d("CurrencyScreen", "🔄 Cambio de divisa a: $newCurrency")
                viewModel.setSelectedCurrency(newCurrency, startDate, endDate)
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            DatePickerComponent(label = "Fecha inicio", selectedDate = startDate, onDateSelected = { newDate ->
                Log.d("CurrencyScreen", "📅 Nueva fecha inicio: $newDate")
                startDate = newDate
                viewModel.fetchExchangeRates(startDate, endDate)
            })

            DatePickerComponent(label = "Fecha fin", selectedDate = endDate, onDateSelected = { newDate ->
                Log.d("CurrencyScreen", "📅 Nueva fecha fin: $newDate")
                endDate = newDate
                viewModel.fetchExchangeRates(startDate, endDate)
            })

        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            Log.d("CurrencyScreen", "🔄 Botón presionado: actualizar gráfica")
            viewModel.fetchExchangeRates(startDate, endDate)
        }) {
            Text("Actualizar Gráfica")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Gráfica de Tasas de Cambio")
        ExchangeRateGraph(exchangeRates)
    }
}
