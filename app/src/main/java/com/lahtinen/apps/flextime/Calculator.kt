package com.lahtinen.apps.flextime

import java.lang.Integer.parseInt

class Calculator {

    companion object {
        fun timeStringToMinutesInt(timeString: String): Int {
            val times = timeString.split(":")
            val hours = parseInt(times[0])
            val minutes = parseInt(times[1])
            return hours * 60 + if (timeString[0] == '-') -minutes else minutes
        }

        fun buildPickerTimes(): Array<String> {
            val hours = listOf("00", "01", "02", "03", "04")
            val minutes = listOf("00", "15", "30", "45")

            val times = buildTime(hours, minutes)
            return times.reversed().toTypedArray()
        }

        private fun buildTime(hourValues: List<String>, minuteValues: List<String>): List<String> {
            return hourValues.flatMap { hour -> minuteValues.map { minute -> "$hour:$minute" } }
        }
    }
}
