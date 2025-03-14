package com.example.exchangegraph.data

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.util.Log

class CurrencyRepository(private val contentResolver: ContentResolver) {

    fun getExchangeRates(currency: String, startDate: String, endDate: String): List<ExchangeRate> {
        val exchangeRates = mutableListOf<ExchangeRate>()
        val uri = Uri.parse("content://com.example.nuevasdivisas.provider/exchange_rates")

        val cursor: Cursor? = contentResolver.query(
            uri,
            null,
            "currency = ? AND date BETWEEN ? AND ?",
            arrayOf(currency, startDate, endDate),
            null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val currency = it.getString(it.getColumnIndexOrThrow("currency"))
                val rate = it.getDouble(it.getColumnIndexOrThrow("rate"))
                val date = it.getString(it.getColumnIndexOrThrow("date"))

                exchangeRates.add(ExchangeRate(currency, rate, date))
            }
        }

        Log.d("CurrencyRepository", "📊 Datos obtenidos: $exchangeRates")
        return exchangeRates
    }

    // ✅ Función para obtener todas las monedas disponibles en la base de datos
    fun getAllCurrencies(): List<String> {
        val currencyList = mutableListOf<String>()
        val uri = Uri.parse("content://com.example.nuevasdivisas.provider/exchange_rates")

        val cursor: Cursor? = contentResolver.query(uri, arrayOf("currency"), null, null, null)

        cursor?.use {
            while (it.moveToNext()) {
                val currency = it.getString(it.getColumnIndexOrThrow("currency"))
                if (!currencyList.contains(currency)) {
                    currencyList.add(currency)
                }
            }
        }

        Log.d("CurrencyRepository", "💰 Lista de monedas obtenida: $currencyList")
        return currencyList
    }
}
