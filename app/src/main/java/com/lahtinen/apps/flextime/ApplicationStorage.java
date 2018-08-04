package com.lahtinen.apps.flextime;

import android.content.Context;
import android.content.SharedPreferences;

import org.joda.time.DateTime;
import org.joda.time.Duration;

public class ApplicationStorage {

    private static final String TIME = "time";
    private static final String MODIFIED = "modified";

    private final SharedPreferences storage;

    ApplicationStorage(Context context) {
        storage = context.getSharedPreferences("flextime", Context.MODE_PRIVATE);
    }

    public void updateTime(Duration adjustment, boolean isNegative) {
        SharedPreferences.Editor editor = storage.edit();
        Duration newDuration = isNegative ? loadTime().minus(adjustment) : loadTime().plus(adjustment);
        editor.putString(TIME, newDuration.toString());
        updateUpdated();
        editor.apply();
    }

    public Duration loadTime() {
        return Duration.parse(storage.getString(TIME, Duration.ZERO.toString()));
    }

    private void updateUpdated() {
        SharedPreferences.Editor editor = storage.edit();
        editor.putString(MODIFIED, DateTime.now().toString("HH:mm dd/MM"));
        editor.apply();
    }

    public String loadModified() {
        return storage.getString(MODIFIED, "Never");
    }

    public void reset() {
        SharedPreferences.Editor editor = storage.edit();
        editor.clear();
        editor.apply();
    }
}
