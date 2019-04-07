package com.lahtinen.apps.flextime;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CalculatorTest {

    @Test
    public void stringToInt_positiveNumbers() throws Exception {
        assertThat(Calculator.Companion.stringToInt("00:00"), is(0));
        assertThat(Calculator.Companion.stringToInt("01:00"), is(60));
        assertThat(Calculator.Companion.stringToInt("00:15"), is(15));
        assertThat(Calculator.Companion.stringToInt("01:15"), is(75));
    }

    @Test
    public void stringToInt_negativeNumbers() throws Exception {
        assertThat(Calculator.Companion.stringToInt("00:00"), is(0));
        assertThat(Calculator.Companion.stringToInt("-01:00"), is(-60));
        assertThat(Calculator.Companion.stringToInt("-00:15"), is(-15));
        assertThat(Calculator.Companion.stringToInt("-01:15"), is(-75));
    }

    @Test
    public void buildPickerNumbers() throws Exception {
        //given
        String[] expectedTimes = new String[]{
                "04:45",
                "04:30",
                "04:15",
                "04:00",
                "03:45",
                "03:30",
                "03:15",
                "03:00",
                "02:45",
                "02:30",
                "02:15",
                "02:00",
                "01:45",
                "01:30",
                "01:15",
                "01:00",
                "00:45",
                "00:30",
                "00:15",
                "00:00"
        };

        //when
        String[] pickerTimes = Calculator.Companion.buildPickerTimes();

        //then
        assertThat(pickerTimes, equalTo(expectedTimes));
    }

}
