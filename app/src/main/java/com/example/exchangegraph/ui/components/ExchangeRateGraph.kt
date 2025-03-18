package com.example.exchangegraph.ui.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import com.example.exchangegraph.data.ExchangeRate
import kotlin.math.roundToInt

@Composable
fun ExchangeRateGraph(exchangeRates: List<ExchangeRate>) {
    Log.d("ExchangeRateGraph", "📉 Datos recibidos para la gráfica: $exchangeRates")

    if (exchangeRates.isEmpty()) {
        Log.e("ExchangeRateGraph", "⚠️ No hay datos para graficar")
        return
    }

    // 🔹 Ordenar los datos por fecha
    val sortedRates = exchangeRates.sortedBy { it.date }

    val minRate = sortedRates.minOf { it.rate }.toFloat()
    val maxRate = sortedRates.maxOf { it.rate }.toFloat()

    var selectedPoint by remember { mutableStateOf<Pair<Float, Float>?>(null) }
    var selectedRate by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .pointerInput(Unit) {
                detectTapGestures { tapOffset ->
                    val index = ((tapOffset.x / size.width) * (sortedRates.size - 1))
                        .roundToInt()
                        .coerceIn(0, sortedRates.size - 1) // 🔹 Evita desbordamientos

                    val rate = sortedRates[index].rate.toFloat()
                    val date = sortedRates[index].date

                    val heightScale = if (maxRate != minRate) size.height / (maxRate - minRate) else 1f
                    val y = size.height - ((rate - minRate) * heightScale)

                    selectedPoint = Pair(index.toFloat() * (size.width / sortedRates.size), y)
                    selectedRate = "💰 ${String.format("%.4f", rate)}\n📅 $date"

                    // 🔹 Log para verificar qué índice y valor está seleccionando
                    Log.d("ExchangeRateGraph", "📍 Índice seleccionado: $index, Valor: $rate, Fecha: $date")
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val widthStep = size.width / sortedRates.size.coerceAtLeast(1)
            val heightScale = if (maxRate != minRate) size.height / (maxRate - minRate) else 1f

            for (i in 1 until sortedRates.size) {
                val startX = (i - 1) * widthStep
                val startY = size.height - ((sortedRates[i - 1].rate.toFloat() - minRate) * heightScale)
                val endX = i * widthStep
                val endY = size.height - ((sortedRates[i].rate.toFloat() - minRate) * heightScale)

                drawLine(
                    color = Color.Blue,
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = 4f
                )
            }

            selectedPoint?.let { (x, y) ->
                drawCircle(
                    color = Color.Red,
                    radius = 10f,
                    center = Offset(x, y)
                )
            }
        }

        selectedPoint?.let { (x, y) ->
            selectedRate?.let { rate ->
                Popup(
                    alignment = Alignment.TopStart,
                    offset = IntOffset(x.roundToInt(), (y - 40).roundToInt())
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White,
                        shadowElevation = 4.dp
                    ) {
                        Text(
                            text = rate,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}
