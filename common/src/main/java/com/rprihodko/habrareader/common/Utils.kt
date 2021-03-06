package com.rprihodko.habrareader.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// TODO: hardcoded strings make localization impossible, and app resources are not available from static context
class Utils {
    companion object {
        fun formatTime(timeString: String): String {
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssxxxxx")
            val dateTime: LocalDateTime = LocalDateTime.parse(timeString, formatter)
            val currentDate = LocalDateTime.now()

            var datepart: String = dateTime.dayOfMonth.toString() + " " + dateTime.monthStringValue
            if(dateTime.year == currentDate.year) {
                if(dateTime.dayOfYear == currentDate.dayOfYear) {
                    datepart = "сегодня"
                } else if(dateTime.dayOfYear.plus(1) == currentDate.dayOfYear) {
                    datepart = "вчера"
                }
            } else {
                datepart += " " + dateTime.year
            }
            val minutes = dateTime.minute.run {
                if(this > 9) this.toString() else "0$this"
            }

            return listOf(datepart, "в", dateTime.hour.toString())
                .joinToString(" ")
                .plus(":")
                .plus(minutes)
        }

        val LocalDateTime.monthStringValue: String
            get() = when(this.monthValue) {
                1 -> "января"
                2 -> "февраля"
                3 -> "марта"
                4 -> "апреля"
                5 -> "мая"
                6 -> "июня"
                7 -> "июля"
                8 -> "августа"
                9 -> "сентября"
                10 -> "октября"
                11 -> "ноября"
                12 -> "декабря"
                else -> ""
            }

        val Int.toStringWithThousands: String
            get() = if(this < 1000) {
                this.toString()
            } else {
                "%.1f".format(this.toDouble()/1000) + "K"
            }

        val String.withHttpsPrefix: String
        get() = if(!this.startsWith("https:")) {
            "https:$this"
        } else {
            this
        }
    }

}