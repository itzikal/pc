package com.itzik.pc.interfaces;

import android.support.annotation.Nullable;

/**
 * SharedPreferences interface
 */

public interface ImSharedPreferences
{
    void removeValue(String key);

    void putObject(String key, Object value);
    void putString(String key, String value);
    void putInt(String key, int value);
    void putLong(String key, long value);
    void putBoolean(String key, boolean value);
    void putValue(String key, String value);


    @Nullable
    <T> T getObject(String key, Class<T> type);
    String getString(String key, String defaultValue);
    int getInt(String key, int defaultValue);
    long getLong(String key, long defaultValue);
    boolean getBoolean(String key, boolean defaultValue);

    void clear();
}
