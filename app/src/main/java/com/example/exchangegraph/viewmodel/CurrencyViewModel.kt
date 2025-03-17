package com.example.exchangegraph.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.exchangegraph.data.CurrencyRepository
import com.example.exchangegraph.data.ExchangeRate
import android.util.Log

class CurrencyViewModel(private val repository: CurrencyRepository) : ViewModel() {
    private val _filteredExchangeRates = MutableStateFlow<List<ExchangeRate>>(emptyList())
    val filteredExchangeRates: StateFlow<List<ExchangeRate>> = _filteredExchangeRates

    private val _selectedCurrency = MutableStateFlow("USD")
    val selectedCurrency: StateFlow<String> = _selectedCurrency

    init {
        fetchExchangeRates("2025-03-10", "2025-03-13")  // ✅ Valores por defecto
    }

    fun fetchExchangeRates(startDate: String, endDate: String) {
        val currency = _selectedCurrency.value

        Log.d("CurrencyViewModel", "🔄 Solicitando datos para $currency entre $startDate y $endDate")

        val allRates = repository.getExchangeRates(currency, startDate, endDate)

        Log.d("CurrencyViewModel", "🌍 TODOS los datos en el repositorio: $allRates")

        val rates = allRates.filter { it.currency == currency }

        Log.d("CurrencyViewModel", "📊 Datos filtrados para $currency: $rates")

        _filteredExchangeRates.value = rates
    }

    fun getAllCurrencies(): List<String> {
        return repository.getAllCurrencies()  // ✅ Se asegura de que esta función exista
    }

    fun setSelectedCurrency(currency: String, startDate: String, endDate: String) {
        Log.d("CurrencyViewModel", "🔄 Cambio de divisa a: $currency, Fecha Inicio: $startDate, Fecha Fin: $endDate")

        _selectedCurrency.value = currency
        fetchExchangeRates(startDate, endDate)
    }
}
