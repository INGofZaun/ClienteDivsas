package com.example.exchangegraph.ui.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.exchangegraph.data.ExchangeRate

@Composable
fun ExchangeRateGraph(exchangeRates: List<ExchangeRate>) {
    Log.d("ExchangeRateGraph", "📉 Datos recibidos para la gráfica: $exchangeRates")

    if (exchangeRates.isEmpty()) {
        Log.e("ExchangeRateGraph", "⚠️ No hay datos para graficar")
        return
    }

    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .background(Color.White)
    ) {
        Canvas(modifier = Modifier.width(800.dp).height(350.dp)) {
            for (i in 1 until exchangeRates.size) {
                val startX = i * 50f
                val startY = 300f - exchangeRates[i - 1].rate.toFloat() * 10
                val endX = (i + 1) * 50f
                val endY = 300f - exchangeRates[i].rate.toFloat() * 10

                drawLine(
                    color = Color.Blue,
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = 5f
                )

                Log.d("ExchangeRateGraph", "📍 Punto en x: $endX, y: $endY, valor: ${exchangeRates[i].rate}")
            }
        }
    }
}
