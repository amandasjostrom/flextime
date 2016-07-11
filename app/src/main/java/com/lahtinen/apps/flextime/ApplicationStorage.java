package com.lahtinen.apps.flextime;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicationStorage {

    public static final String TIME = "time";
    public static final String MODIFIED = "modified";

    private final SharedPreferences storage;

    public ApplicationStorage(Context context) {
        storage = context.getSharedPreferences("flextime", Context.MODE_PRIVATE);
    }

    public boolean saveTime(int value) {
        SharedPreferences.Editor editor = storage.edit();
        editor.putInt(TIME, value);
        updateUpdated();
        return editor.commit();
    }

    public int loadTime() {
        return storage.getInt(TIME, 0);
    }

    public boolean updateUpdated() {
        SharedPreferences.Editor editor = storage.edit();
        editor.putString(MODIFIED, new SimpleDateFormat("HH:mm dd/MM").format(new Date()));
        return editor.commit();
    }

    public String loadModified() {
        return storage.getString(MODIFIED, "Never");
    }
}
