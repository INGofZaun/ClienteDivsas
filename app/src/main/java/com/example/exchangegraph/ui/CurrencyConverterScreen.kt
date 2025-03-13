package com.example.exchangegraph.ui

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.exchangegraph.ui.components.*
import com.example.exchangegraph.viewmodel.CurrencyViewModel

@Composable
fun CurrencyConverterScreen(viewModel: CurrencyViewModel, context: Context) {
    val exchangeRates by viewModel.exchangeRates.collectAsState(initial = emptyList())

    // 🔥 Corregido: Obtener todas las monedas desde el ViewModel
    val currencyList = exchangeRates.map { it.currency }.distinct()

    var selectedCurrency by remember { mutableStateOf(currencyList.firstOrNull() ?: "USD") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Tasas de Cambio", style = MaterialTheme.typography.headlineMedium)

        DropdownMenuComponent(
            currencyList = currencyList,
            selectedCurrency = selectedCurrency,
            updateSelectedCurrency = { selectedCurrency = it }
        )

        DatePickerComponent(
            context = context,
            onDateSelected = { start, end ->
                startDate = start
                endDate = end
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(exchangeRates.filter { it.currency == selectedCurrency }) { rate ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Moneda: ${rate.currency}")
                        Text(text = "Tasa: ${rate.rate}")
                        Text(text = "Fecha: ${rate.date}")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ExchangeRateGraph(rates = exchangeRates.map { it.rate.toFloat() })
    }
}
