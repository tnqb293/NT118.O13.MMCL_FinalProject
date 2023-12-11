package com.uit.airsense.Model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;

public class LocaleHelper {
    private static final String PREF_KEY_LANGUAGE = "language";

    public static void setLocale(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = locale;

        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

        // Lưu ngôn ngữ đã chọn vào SharedPreferences
        SharedPreferences.Editor editor = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit();
        editor.putString(PREF_KEY_LANGUAGE, language);
        editor.apply();
    }

    public static String getLanguage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return prefs.getString(PREF_KEY_LANGUAGE, Locale.getDefault().getLanguage());
    }
}
