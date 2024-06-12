package com.ccs.strangeroom;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceConfig {

    public static final String REFERENCE = "reference";
    public static final String WOOD = "wood";
    public static final String DAY = "dayInt";
    public static final String MEAT = "meat";
    public static final String FUR = "fur";
    public static final String CAP = "cap";
    public static final String JACKET = "jacket";
    public static final String HEALTH = "health";
    public static final String HUNGRY = "hungry";

    public static void registerPref(Context context, SharedPreferences.OnSharedPreferenceChangeListener listener) {
        SharedPreferences pref = context.getSharedPreferences(REFERENCE, Context.MODE_PRIVATE);
        pref.registerOnSharedPreferenceChangeListener(listener);
    }
    public static void setWood(Context context, int value) {
        SharedPreferences pref = context.getSharedPreferences(REFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(WOOD, value);
        editor.apply();
    }

    public static int getWood(Context context) {
        SharedPreferences pref = context.getSharedPreferences(REFERENCE, Context.MODE_PRIVATE);
        return pref.getInt(WOOD, 0);
    }
    public static void setDay(Context context, int value) {
        SharedPreferences pref = context.getSharedPreferences(REFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(DAY, value);
        editor.apply();
    }

    public static int getDay(Context context) {
        SharedPreferences pref = context.getSharedPreferences(REFERENCE, Context.MODE_PRIVATE);
        return pref.getInt(DAY, 0);
    }
    public static void setMeat(Context context, int value) {
        SharedPreferences pref = context.getSharedPreferences(REFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(MEAT, value);
        editor.apply();
    }

    public static int getMeat(Context context) {
        SharedPreferences pref = context.getSharedPreferences(REFERENCE, Context.MODE_PRIVATE);
        return pref.getInt(MEAT, 0);
    }
    public static void setFur(Context context, int value) {
        SharedPreferences pref = context.getSharedPreferences(REFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(FUR, value);
        editor.apply();
    }

    public static int getFur(Context context) {
        SharedPreferences pref = context.getSharedPreferences(REFERENCE, Context.MODE_PRIVATE);
        return pref.getInt(FUR, 0);
    }
    public static void setCap(Context context, int value) {
        SharedPreferences pref = context.getSharedPreferences(REFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(CAP, value);
        editor.apply();
    }

    public static int getCap(Context context) {
        SharedPreferences pref = context.getSharedPreferences(REFERENCE, Context.MODE_PRIVATE);
        return pref.getInt(CAP, 0);
    }
    public static void setJacket(Context context, int value) {
        SharedPreferences pref = context.getSharedPreferences(REFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(JACKET, value);
        editor.apply();
    }

    public static int getJacket(Context context) {
        SharedPreferences pref = context.getSharedPreferences(REFERENCE, Context.MODE_PRIVATE);
        return pref.getInt(JACKET, 0);
    }
    public static void setHealth(Context context, int value) {
        SharedPreferences pref = context.getSharedPreferences(REFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(HEALTH, value);
        editor.apply();
    }

    public static int getHealth(Context context) {
        SharedPreferences pref = context.getSharedPreferences(REFERENCE, Context.MODE_PRIVATE);
        return pref.getInt(HEALTH, 100);
    }
    public static void setHungry(Context context, int value) {
        SharedPreferences pref = context.getSharedPreferences(REFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(HUNGRY, value);
        editor.apply();
    }

    public static int getHungry(Context context) {
        SharedPreferences pref = context.getSharedPreferences(REFERENCE, Context.MODE_PRIVATE);
        return pref.getInt(HUNGRY, 10);
    }
}
