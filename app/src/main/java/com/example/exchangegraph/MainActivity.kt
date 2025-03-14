package com.example.exchangegraph

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.exchangegraph.data.CurrencyRepository
import com.example.exchangegraph.ui.CurrencyConverterScreen
import com.example.exchangegraph.ui.theme.ExchangeGraphTheme
import com.example.exchangegraph.viewmodel.CurrencyViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = CurrencyRepository(contentResolver)
        val viewModel = ViewModelProvider(this, CurrencyViewModel.Factory(repository))[CurrencyViewModel::class.java]

        setContent {
            ExchangeGraphTheme {
                CurrencyConverterScreen(viewModel = viewModel)
            }
        }
    }
}
