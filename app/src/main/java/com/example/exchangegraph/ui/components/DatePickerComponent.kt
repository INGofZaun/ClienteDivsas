package com.example.exchangegraph.ui.components

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun DatePickerComponent(label: String, selectedDate: String, onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    var date by remember { mutableStateOf(selectedDate) }

    Button(onClick = { showDatePicker(context) { newDate ->
        Log.d("DatePicker", "📅 Fecha seleccionada: $newDate")
        date = newDate
        onDateSelected(newDate)
    } }) {
        Text("$label: $date")
    }
}

fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val datePicker = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedDate = "$year-${month + 1}-$dayOfMonth"
            onDateSelected(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePicker.show()
}
