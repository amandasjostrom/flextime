package com.lahtinen.apps.flextime

import org.junit.Test

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat

class CalculatorTest {

    @Test
    @Throws(Exception::class)
    fun stringToInt_positiveNumbers() {
        assertThat(Calculator.timeStringToMinutesInt("00:00"), `is`(0))
        assertThat(Calculator.timeStringToMinutesInt("01:00"), `is`(60))
        assertThat(Calculator.timeStringToMinutesInt("00:15"), `is`(15))
        assertThat(Calculator.timeStringToMinutesInt("01:15"), `is`(75))
    }

    @Test
    @Throws(Exception::class)
    fun stringToInt_negativeNumbers() {
        assertThat(Calculator.timeStringToMinutesInt("00:00"), `is`(0))
        assertThat(Calculator.timeStringToMinutesInt("-01:00"), `is`(-60))
        assertThat(Calculator.timeStringToMinutesInt("-00:15"), `is`(-15))
        assertThat(Calculator.timeStringToMinutesInt("-01:15"), `is`(-75))
    }

    @Test
    @Throws(Exception::class)
    fun buildPickerNumbers() {
        //given
        val expectedTimes = arrayOf("04:45", "04:30", "04:15", "04:00", "03:45", "03:30", "03:15", "03:00", "02:45", "02:30", "02:15", "02:00", "01:45", "01:30", "01:15", "01:00", "00:45", "00:30", "00:15", "00:00")

        //when
        val pickerTimes = Calculator.buildPickerTimes()

        //then
        assertThat(pickerTimes, equalTo(expectedTimes))
    }

}
