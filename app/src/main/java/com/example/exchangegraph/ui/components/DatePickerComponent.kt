package com.example.exchangegraph.ui.components

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun DatePickerComponent(label: String, selectedDate: String, onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
        val formattedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
        onDateSelected(formattedDate)
    }, year, month, day)

    Button(onClick = { datePickerDialog.show() }, modifier = Modifier.fillMaxWidth()) {
        Text(text = "$label: $selectedDate")
    }
}
