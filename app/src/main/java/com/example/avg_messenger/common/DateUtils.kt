package com.example.avg_messenger.common

import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class DateUtils {
    companion object {
        //TODO пофиксить ебаный формат даты(из-за него выкидывает из чата)
        fun formatTime(dateTimeString: String): String {
            if (dateTimeString.contains("T") && dateTimeString.endsWith("Z")) {
                val formatter = DateTimeFormatter.ofPattern("HH:mm")
                val zonedDateTime = ZonedDateTime.parse(dateTimeString)
                return zonedDateTime.format(formatter)
            }

            // Извлекаем часть строки с датой и временем
            val dateTimePart = dateTimeString.split(" +0000 UTC")[0] // Убираем лишнюю часть

            // Создаем форматтер для нужного формата
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSSSS")

            // Парсим строку в ZonedDateTime
            val dateTime = ZonedDateTime.parse(dateTimePart, formatter.withZone(ZoneOffset.UTC))

            // Форматируем дату в нужный формат HH:mm
            val outputFormatter = DateTimeFormatter.ofPattern("HH:mm")
            val formattedTime = dateTime.format(outputFormatter)

            return formattedTime


        }
    }
}