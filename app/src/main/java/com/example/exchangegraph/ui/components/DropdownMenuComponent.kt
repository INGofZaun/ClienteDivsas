package com.example.exchangegraph.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DropdownMenuComponent(
    currencyList: List<String>,
    selectedCurrency: String,
    updateSelectedCurrency: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = { expanded = true }) {
            Text(text = selectedCurrency)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            currencyList.forEach { currency ->
                DropdownMenuItem(text = { Text(currency) }, onClick = {
                    updateSelectedCurrency(currency)
                    expanded = false
                })
            }
        }
    }
}
