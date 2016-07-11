package com.lahtinen.apps.flextime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

public class Calculator {

    public static String intMinutesToString(int minutes) {
        if (minutes == 0) {
            return "0h";
        }
        final long hourPart = TimeUnit.MINUTES.toHours(minutes);
        final long minutePart = minutes - TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours(minutes));
        return (hourPart != 0 ? hourPart + "h" : "") + (minutePart != 0 ? minutePart + "m" : "");
    }

    public static int stringToInt(String timeString) {
        final String[] times = timeString.split(":");
        return parseInt(times[0]) * 60 + parseInt(times[1]);
    }

    public static String[] buildPickerNumbers() {
        final String[] hourValues = new String[]{"00", "01", "02", "03", "04"};
        final String[] minuteValues = new String[]{"00", "15", "30", "45"};

        final ArrayList<String> times = buildTime(hourValues, minuteValues);
        Collections.reverse(times);

        final String[] timesArray = new String[times.size()];
        return times.toArray(timesArray);
    }

    private static ArrayList<String> buildTime(String[] hourValues, String[] minuteValues) {
        final ArrayList<String> times = new ArrayList<>();
        for (String hour : hourValues) {
            for (String minute : minuteValues) {
                times.add(hour + ":" + minute);
            }
        }
        return times;
    }
}
