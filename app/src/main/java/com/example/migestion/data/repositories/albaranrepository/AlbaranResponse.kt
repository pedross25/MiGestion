package com.example.migestion.data.repositories.albaranrepository

import com.example.migestion.model.Invoice
import kotlinx.serialization.Serializable

@Serializable
data class AlbaranResponse(
    val data: List<Invoice>
)