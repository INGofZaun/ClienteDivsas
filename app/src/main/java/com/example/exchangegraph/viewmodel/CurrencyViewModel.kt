package com.example.exchangegraph.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.exchangegraph.data.CurrencyRepository
import com.example.exchangegraph.data.ExchangeRate
import android.util.Log

class CurrencyViewModel(private val repository: CurrencyRepository) : ViewModel() {
    private val _filteredExchangeRates = MutableStateFlow<List<ExchangeRate>>(emptyList())
    val filteredExchangeRates: StateFlow<List<ExchangeRate>> = _filteredExchangeRates

    private val _selectedCurrency = MutableStateFlow("USD")
    val selectedCurrency: StateFlow<String> = _selectedCurrency

    init {
        // Valores de fecha por defecto
        fetchExchangeRates("2025-03-10", "2025-03-16")
    }

    fun fetchExchangeRates(startDate: String, endDate: String) {
        val currency = _selectedCurrency.value
        Log.d("CurrencyViewModel", "🔄 Solicitando datos para $currency entre $startDate y $endDate")

        viewModelScope.launch {
            val allRates = repository.getExchangeRates(currency, startDate, endDate)

            val filteredRates = allRates.filter { it.currency == currency }

            if (filteredRates.isEmpty()) {
                Log.w("CurrencyViewModel", "⚠️ No se encontraron datos para $currency en el rango $startDate - $endDate")
            } else {
                Log.d("CurrencyViewModel", "🌍 Datos obtenidos: $filteredRates")
            }

            _filteredExchangeRates.value = filteredRates
            Log.d("CurrencyViewModel", "📊 Datos filtrados para $currency: ${_filteredExchangeRates.value}")
        }
    }

    fun getAllCurrencies(): List<String> {
        return repository.getAllCurrencies()
    }

    fun setSelectedCurrency(currency: String, startDate: String, endDate: String) {
        Log.d("CurrencyViewModel", "🔄 Cambio de divisa a: $currency, Fechas: $startDate a $endDate")
        _selectedCurrency.value = currency
        fetchExchangeRates(startDate, endDate)
    }

    class Factory(private val repository: CurrencyRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CurrencyViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
