package com.example.exchangegraph.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp

@Composable
fun ExchangeRateGraph(rates: List<Float>) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text(text = "Gráfica de Conversión")

        Canvas(modifier = Modifier.fillMaxWidth().height(200.dp)) {
            drawGraph(this, rates)
        }
    }
}

// 🔥 Nueva función para dibujar la gráfica correctamente
fun drawGraph(drawScope: DrawScope, rates: List<Float>) {
    if (rates.size < 2) return // Necesitamos al menos dos puntos para dibujar

    val stepX = drawScope.size.width / (rates.size - 1)
    val maxY = rates.maxOrNull() ?: return
    val minY = rates.minOrNull() ?: return
    val scaleY = drawScope.size.height / (maxY - minY)

    for (i in 1 until rates.size) {
        val start = Offset((i - 1) * stepX, drawScope.size.height - (rates[i - 1] - minY) * scaleY)
        val end = Offset(i * stepX, drawScope.size.height - (rates[i] - minY) * scaleY)

        drawScope.drawLine(
            color = Color.Blue,
            start = start,
            end = end,
            strokeWidth = 5f
        )
    }
}
