package com.example.exchangegraph.data

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.util.Log

class CurrencyRepository(private val contentResolver: ContentResolver) {

    fun getExchangeRates(currency: String, startDate: String, endDate: String): List<ExchangeRate> {
        val exchangeRates = mutableListOf<ExchangeRate>()
        val uri = Uri.parse("content://com.example.nuevasdivisas.provider/exchange_rates")

        try {
            val cursor: Cursor? = contentResolver.query(
                uri,
                null,
                "currency = ? AND date BETWEEN ? AND ?",
                arrayOf(currency, startDate, endDate),
                null
            )

            cursor?.use {
                while (it.moveToNext()) {
                    val currencyIndex = it.getColumnIndex("currency")
                    val rateIndex = it.getColumnIndex("rate")
                    val dateIndex = it.getColumnIndex("date")

                    if (currencyIndex != -1 && rateIndex != -1 && dateIndex != -1) {
                        val currencyValue = it.getString(currencyIndex) ?: "N/A"
                        val rateValue = it.getDouble(rateIndex)
                        val dateValue = it.getString(dateIndex) ?: "N/A"

                        exchangeRates.add(ExchangeRate(currencyValue, rateValue, dateValue))
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("CurrencyRepository", "⚠️ Error al obtener tasas de cambio: ${e.message}")
        }

        Log.d("CurrencyRepository", "📊 Datos obtenidos: $exchangeRates")
        return exchangeRates
    }

    // ✅ Función para obtener todas las monedas disponibles en la base de datos
    fun getAllCurrencies(): List<String> {
        val currencyList = mutableListOf<String>()
        val uri = Uri.parse("content://com.example.nuevasdivisas.provider/exchange_rates")

        try {
            val cursor: Cursor? = contentResolver.query(
                uri,
                arrayOf("DISTINCT currency"), // ✅ Evita duplicados desde la consulta
                null,
                null,
                null
            )

            cursor?.use {
                while (it.moveToNext()) {
                    val currencyIndex = it.getColumnIndex("currency")
                    if (currencyIndex != -1) {
                        val currency = it.getString(currencyIndex) ?: "N/A"
                        currencyList.add(currency)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("CurrencyRepository", "⚠️ Error al obtener monedas: ${e.message}")
        }

        Log.d("CurrencyRepository", "💰 Lista de monedas obtenida: $currencyList")
        return currencyList
    }
}

