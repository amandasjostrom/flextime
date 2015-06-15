package com.lahtinen.apps.flextime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class Calculator {

    public static String intMinutesToString(int minutes) {
        if (minutes == 0) {
            return "0h";
        }
        long hourPart = TimeUnit.MINUTES.toHours(minutes);
        long minutePart = minutes - TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours(minutes));
        String result = (hourPart != 0 ? hourPart + "h" : "") + (minutePart != 0 ? minutePart + "m" : "");
        result = result.replace("-", "");
        return minutes < 0 ? "-" + result : result;
    }

    public static int stringToInt(String timeString) {
        String cleanTimeString = timeString;
        boolean isNegative = false;
        if (cleanTimeString.startsWith("-")) {
            cleanTimeString = cleanTimeString.substring(1);
            isNegative = true;
        }
        String[] times = cleanTimeString.split(":");
        int time = toInt(times[0]) * 60 + toInt(times[1]);
        return isNegative ? time * -1 : time;
    }

    private static int toInt(String time) {
        return Integer.parseInt(time);
    }

    public static String[] buildPickerNumbers() {
        final String[] hourValues = new String[]{"00", "01", "02", "03", "04"};
        final String[] minuteValues = new String[]{"00", "15", "30", "45"};

        final ArrayList<String> times = buildTime(hourValues, minuteValues, "");
        Collections.reverse(times);
        final ArrayList<String> negativeTimes = buildTime(hourValues, minuteValues, "-");
        negativeTimes.remove(0);
        times.addAll(negativeTimes);

        String[] timesArray = new String[times.size()];
        return times.toArray(timesArray);
    }

    private static ArrayList<String> buildTime(String[] hourValues, String[] minuteValues, String postFix) {
        final ArrayList<String> times = new ArrayList<>();
        for (String hour : hourValues) {
            for (String minute : minuteValues) {
                times.add(postFix + hour + ":" + minute);
            }
        }
        return times;
    }
}
