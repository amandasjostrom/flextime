package com.lahtinen.apps.flextime;

import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationStorage {

    public static final String TIME = "time";

    private final SharedPreferences storage;

    public ApplicationStorage(Context context) {
        storage = context.getSharedPreferences("flextime", Context.MODE_PRIVATE);
    }

    public boolean saveTime(int value) {
        SharedPreferences.Editor editor = storage.edit();
        editor.putInt(TIME, value);
        return editor.commit();
    }

    public int loadTime() {
        return storage.getInt(TIME, 0);
    }
}
