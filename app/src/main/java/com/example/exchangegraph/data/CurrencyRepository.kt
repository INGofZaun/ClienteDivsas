package com.example.exchangegraph.data

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class CurrencyRepository(context: Context) {

    private val contentResolver: ContentResolver = context.contentResolver

    fun getAllCurrencies(): List<String> {
        return listOf("USD", "EUR", "JPY", "MXN", "GBP", "CAD") // 🔥 MODIFICA ESTO CON LAS MONEDAS REALES
    }

    fun getExchangeRatesFlow(): Flow<List<ExchangeRate>> = flow {
        val rates = getExchangeRates()
        emit(rates)
    }

    suspend fun getExchangeRates(): List<ExchangeRate> = withContext(Dispatchers.IO) {
        val exchangeRates = mutableListOf<ExchangeRate>()
        val uri: Uri = Uri.parse("content://com.example.nuevasdivisas.provider/exchange_rates")

        Log.d("CurrencyRepository", "📡 Consultando URI: $uri")

        val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)

        cursor?.use {
            Log.d("CurrencyRepository", "📄 Cursor obtenido con ${it.count} registros")
            if (it.moveToFirst()) {
                val currencyIndex = it.getColumnIndex("currency")
                val rateIndex = it.getColumnIndex("rate")
                val dateIndex = it.getColumnIndex("date")

                while (!it.isAfterLast) {
                    val currency = it.getString(currencyIndex)
                    val rate = it.getDouble(rateIndex)
                    val date = it.getString(dateIndex)

                    exchangeRates.add(ExchangeRate(currency, rate, date))
                    Log.d("CurrencyRepository", "✅ Moneda: $currency, Tasa: $rate, Fecha: $date")

                    it.moveToNext()
                }
            } else {
                Log.w("CurrencyRepository", "⚠️ Cursor vacío: No se obtuvieron datos")
            }
        } ?: Log.e("CurrencyRepository", "❌ Cursor nulo: No se obtuvieron datos")

        return@withContext exchangeRates
    }
}
