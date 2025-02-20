package com.example.eventlee.model

import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateTime {
    @JvmStatic
    fun getFormattedDate(
        date: LocalDate = LocalDate.now(),
        outputFormat: String = "EEEE dd, MMMM"
    ): String {
        val formatter = DateTimeFormatter.ofPattern(outputFormat)
        val textD = date.format(formatter)

        try {
            return textD
        } catch (e: Exception) {
            // Handle parsing error
            e.printStackTrace()
        }

        // If parsing fails, return the original date
        return date.toString()
    }

    @JvmStatic
    fun getFormattedDate(
        date: Timestamp,
        outputFormat: String = "EEEE dd, MMMM"
    ): String {
        val formatter = DateTimeFormatter.ofPattern(outputFormat)
        val textD = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)

        try {
            return textD
        } catch (e: Exception) {
            // Handle parsing error
            e.printStackTrace()
        }

        // If parsing fails, return the original date
        return date.toString()
    }
}