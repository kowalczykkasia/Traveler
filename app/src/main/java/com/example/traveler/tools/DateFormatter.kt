package com.example.traveler.tools

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DateFormatter {

    private val defaultPattern = "dd/MM/yyyy"
    private fun getBase() = DateTimeFormatter.ofPattern(defaultPattern)

    fun getToday(): String = LocalDateTime.now().format(getBase())

    @SuppressLint("SimpleDateFormat")
    fun getFormatDate(date: Date): String = SimpleDateFormat(defaultPattern).format(date)
}