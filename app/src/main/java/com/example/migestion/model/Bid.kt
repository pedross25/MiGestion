package com.example.migestion.model

data class Bid(
    var id: String? = null,
    var product: String? = null,
    var user: String,
    var amount: Double
)