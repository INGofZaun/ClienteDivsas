package com.example.exchangegraph.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.exchangegraph.data.CurrencyRepository
import com.example.exchangegraph.data.ExchangeRate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CurrencyViewModel(private val repository: CurrencyRepository) : ViewModel() {

    private val _exchangeRates = MutableStateFlow<List<ExchangeRate>>(emptyList())
    val exchangeRates: StateFlow<List<ExchangeRate>> get() = _exchangeRates

    init {
        fetchExchangeRates()
    }

    private fun fetchExchangeRates() {
        viewModelScope.launch {
            _exchangeRates.value = repository.getExchangeRates()
        }
    }

    // ✅ Nueva función para obtener la lista de monedas únicas
    fun getAllCurrencies(): List<String> {
        return _exchangeRates.value.map { it.currency }.distinct()
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
