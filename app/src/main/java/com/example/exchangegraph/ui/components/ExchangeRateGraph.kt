package com.example.exchangegraph.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import android.util.Log
import com.example.exchangegraph.data.ExchangeRate

@Composable
fun ExchangeRateGraph(exchangeRates: List<ExchangeRate>) {
    Log.d("ExchangeRateGraph", "📉 Datos recibidos para la gráfica: $exchangeRates")

    if (exchangeRates.isEmpty()) {
        Log.e("ExchangeRateGraph", "⚠️ No hay datos para graficar")
        return
    }

    // Determinar el mínimo y máximo de tasas para escalar el gráfico
    val minRate = exchangeRates.minOf { it.rate }.toFloat()
    val maxRate = exchangeRates.maxOf { it.rate }.toFloat()

    Canvas(modifier = Modifier.fillMaxWidth().height(300.dp)) {
        // Dividir el ancho disponible entre el número de puntos
        val widthStep = size.width / exchangeRates.size.coerceAtLeast(1)
        // Escalar la altura en función de los valores
        val heightScale = if (maxRate != minRate) size.height / (maxRate - minRate) else 1f

        // Dibujar líneas entre cada punto
        for (i in 1 until exchangeRates.size) {
            val startX = (i - 1) * widthStep
            val startY = size.height - ((exchangeRates[i - 1].rate.toFloat() - minRate) * heightScale)
            val endX = i * widthStep
            val endY = size.height - ((exchangeRates[i].rate.toFloat() - minRate) * heightScale)

            drawLine(
                color = Color.Blue,
                start = Offset(x = startX, y = startY),
                end = Offset(x = endX, y = endY),
                strokeWidth = 4f
            )
        }
    }
}
