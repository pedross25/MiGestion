package com.example.migestion.core

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


fun getFormattedCurrentDate(): String {
    val currentDateTime = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("es"))
    return currentDateTime.format(formatter)
}
