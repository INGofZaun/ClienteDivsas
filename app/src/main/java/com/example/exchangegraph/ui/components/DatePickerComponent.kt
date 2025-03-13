package com.example.exchangegraph.ui.components

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DatePickerComponent(
    context: Context,
    onDateSelected: (String, String) -> Unit
) {
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    fun updateDateRange(start: String, end: String) {
        startDate = start
        endDate = end
        onDateSelected(start, end)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = {
            showDatePicker(context) { date ->
                updateDateRange(date, endDate)
            }
        }) {
            Text(text = "Seleccionar Fecha Inicial: $startDate")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            showDatePicker(context) { date ->
                updateDateRange(startDate, date)
            }
        }) {
            Text(text = "Seleccionar Fecha Final: $endDate")
        }
    }
}

fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(Date(year - 1900, month, dayOfMonth))
            onDateSelected(formattedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.show()
}
