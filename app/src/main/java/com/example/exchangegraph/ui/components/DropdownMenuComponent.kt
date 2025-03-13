package com.example.exchangegraph.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DropdownMenuComponent(
    currencyList: List<String>,
    selectedCurrency: String,
    updateSelectedCurrency: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(selectedCurrency) }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth().clickable { expanded = true },
            label = { Text("Seleccionar Moneda") }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            currencyList.forEach { currency ->
                DropdownMenuItem(
                    text = { Text(currency) },
                    onClick = {
                        selectedOption = currency
                        updateSelectedCurrency(currency)
                        expanded = false
                    }
                )
            }
        }
    }
}
