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

    private val _selectedStartDate = MutableStateFlow("2025-03-12")
    val selectedStartDate: StateFlow<String> = _selectedStartDate

    private val _selectedEndDate = MutableStateFlow("2025-03-13")
    val selectedEndDate: StateFlow<String> = _selectedEndDate

    init {
        fetchExchangeRates()
    }

    fun fetchExchangeRates() {
        val currency = _selectedCurrency.value
        val startDate = _selectedStartDate.value
        val endDate = _selectedEndDate.value

        Log.d("CurrencyViewModel", "📡 Obteniendo datos para: $currency ($startDate → $endDate)")
        val rates = repository.getExchangeRates(currency, startDate, endDate)
        Log.d("CurrencyViewModel", "📊 Datos filtrados: $rates")
        _filteredExchangeRates.value = rates
    }

    fun getAllCurrencies(): List<String> {
        return repository.getAllCurrencies()
    }

    fun setSelectedCurrency(currency: String) {
        _selectedCurrency.value = currency
        fetchExchangeRates()
    }

    fun setStartDate(date: String) {
        _selectedStartDate.value = date
    }

    fun setEndDate(date: String) {
        _selectedEndDate.value = date
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
