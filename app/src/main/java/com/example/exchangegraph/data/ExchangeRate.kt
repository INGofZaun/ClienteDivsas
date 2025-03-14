package com.example.exchangegraph.data

data class ExchangeRate(
    val currency: String,   // ✅ Asegurar tipo String
    val rate: Double,       // ✅ Asegurar tipo Double
    val date: String        // ✅ Asegurar tipo String
)
