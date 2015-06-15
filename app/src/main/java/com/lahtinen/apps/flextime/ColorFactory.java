package com.lahtinen.apps.flextime;

/**
 * Handles the time -> color conversion.
 */
public class ColorFactory {

    public ColorFactory() {
    }

    //TODO: alot... colors should be dynamic, not hardcoded.
    public int getColor(int time) {
        if (time < -10) {
            return 0xFFFF0000;
        } else if (time < -5) {
            return 0xFF880000;
        } else if (time > -1 && time < 1) {
            return 0xFF00FF00;
        } else {
            return 0xFF0000FF;
        }
    }
}
