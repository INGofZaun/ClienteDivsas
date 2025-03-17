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

        val repository = CurrencyRepository(contentResolver) // ✅ Se pasa contentResolver
        val viewModel = ViewModelProvider(this, CurrencyViewModelFactory(repository))[CurrencyViewModel::class.java]

        val startDate = "2025-03-10"
        val endDate = "2025-03-13"

        setContent {
            ExchangeGraphTheme {
                CurrencyConverterScreen(viewModel = viewModel, startDate = startDate, endDate = endDate)
            }
        }
    }
}
