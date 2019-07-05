package com.lahtinen.apps.flextime

import org.joda.time.Duration
import org.joda.time.Period

class TimeFormatter {
    fun format(minutes: Duration): String {
        val isNegative = minutes < Duration.ZERO
        val period = minutes.toPeriod()
        return "${if (isNegative && period.hours == 0) "-" else ""}${period.hours}h${Math.abs(period.minutes)}m"
    }
}
