package com.lahtinen.apps.flextime;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CalculatorTest {

    @Test
    public void stringToInt_positiveNumbers() throws Exception {
        assertThat(Calculator.stringToInt("00:00"), is(0));
        assertThat(Calculator.stringToInt("01:00"), is(60));
        assertThat(Calculator.stringToInt("00:15"), is(15));
        assertThat(Calculator.stringToInt("01:15"), is(75));
    }

    @Test
    public void stringToInt_negativeNumbers() throws Exception {
        assertThat(Calculator.stringToInt("00:00"), is(0));
        assertThat(Calculator.stringToInt("-01:00"), is(-60));
        assertThat(Calculator.stringToInt("-00:15"), is(-15));
        assertThat(Calculator.stringToInt("-01:15"), is(-75));
    }

    @Test
    public void intMinutesToString_positiveMinutes() throws Exception {
        assertThat(Calculator.intMinutesToString(0), is("0h"));
        assertThat(Calculator.intMinutesToString(15), is("15m"));
        assertThat(Calculator.intMinutesToString(60), is("1h"));
        assertThat(Calculator.intMinutesToString(75), is("1h15m"));
    }

    @Test
    public void intMinutesToString_negativeMinutes() throws Exception {
        assertThat(Calculator.intMinutesToString(0), is("0h"));
        assertThat(Calculator.intMinutesToString(-15), is("-15m"));
        assertThat(Calculator.intMinutesToString(-60), is("-1h"));
        assertThat(Calculator.intMinutesToString(-75), is("-1h15m"));
    }

    @Test
    public void buildPickerNumbers() throws Exception {
        System.out.println(Calculator.buildPickerNumbers().length);
    }

}