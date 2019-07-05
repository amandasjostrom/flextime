package com.lahtinen.apps.flextime

import org.hamcrest.Matchers
import org.joda.time.Duration
import org.junit.Assert.*
import org.junit.Test

class TimeFormatterTest{

    private val timeFormatter : TimeFormatter = TimeFormatter()

    @Test
    fun positiveTimeHoursAndMinutes() {
        val format = timeFormatter.format(Duration.standardMinutes(100))
        assertThat(format, Matchers.equalTo("1h40m"))
    }

    @Test
    fun negativeTimeHoursAndMinutes() {
        val format = timeFormatter.format(Duration.standardMinutes(-100))
        assertThat(format, Matchers.equalTo("-1h40m"))
    }

    @Test
    fun positiveTimeOnlyHours() {
        val format = timeFormatter.format(Duration.standardMinutes(60))
        assertThat(format, Matchers.equalTo("1h0m"))
    }

    @Test
    fun negativeTimeOnlyHours() {
        val format = timeFormatter.format(Duration.standardMinutes(-60))
        assertThat(format, Matchers.equalTo("-1h0m"))
    }
    @Test
    fun positiveTimeOnlyMinutes() {
        val format = timeFormatter.format(Duration.standardMinutes(30))
        assertThat(format, Matchers.equalTo("0h30m"))
    }

    @Test
    fun negativeTimeOnlyMinutes() {
        val format = timeFormatter.format(Duration.standardMinutes(-30))
        assertThat(format, Matchers.equalTo("-0h30m"))
    }
}