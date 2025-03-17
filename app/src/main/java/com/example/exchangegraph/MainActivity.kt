package com.example.exchangegraph

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.exchangegraph.data.CurrencyRepository
import com.example.exchangegraph.ui.CurrencyConverterScreen
import com.example.exchangegraph.ui.theme.ExchangeGraphTheme
import com.example.exchangegraph.viewmodel.CurrencyViewModel
import com.example.exchangegraph.viewmodel.CurrencyViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = CurrencyRepository(contentResolver)
        val viewModel = ViewModelProvider(this, CurrencyViewModelFactory(repository))[CurrencyViewModel::class.java]

        // Define fechas de ejemplo (puedes cambiarlas según lo necesites)
        val initialStartDate = "2025-03-10"
        val initialEndDate = "2025-03-16"

        setContent {
            ExchangeGraphTheme {
                CurrencyConverterScreen(
                    viewModel = viewModel,
                    initialStartDate = initialStartDate,
                    initialEndDate = initialEndDate
                )
            }
        }
    }
}
