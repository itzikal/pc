package com.itzik.pc.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.itzik.pc.interfaces.ImSharedPreferences;
import com.itzik.pc.utils.TextUtil;

/**
 * Preferences wrapper, save all as string and objects as json.
 */
public class PreferencesWrapper implements ImSharedPreferences
{
public static final String PREF_NAME = "com.itzik.pc.pref";
    private static final String LOG_TAG = PreferencesWrapper.class.getSimpleName();

    private final SharedPreferences mPreferences;

    public PreferencesWrapper(Context context)
    {
        mPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void removeValue(String key)
    {
        mPreferences.edit().remove(encrypt(key)).apply();
    }

    @Override
    public void putObject(String key, Object value)
    {
        Gson gson = new Gson();
        String json = gson.toJson(value);
        putString(key, json);
    }

    @Override
    @Nullable
    public <T> T getObject(String key, Class<T> type)
    {
        String jsonValue = getString(key);
        if (TextUtil.isEmpty(jsonValue))
        {
            return null;
        }

        Gson gson = new Gson();
        T o = null;
        try
        {
            o = gson.fromJson(jsonValue, type);
        }
        catch (Exception e)
        {
            
        }
        return o;
    }

    @Override
    public void putString(String key, String value)
    {
        putValue(key, value);
    }

    @Override
    public String getString(String key, String defaultValue)
    {
        String value = getString(key);
        if (TextUtil.isEmpty(value))
        {
            return defaultValue;
        }

        return value;
    }

    @Override
    public void putInt(String key, int value)
    {
        putValue(key, String.valueOf(value));
    }

    @Override
    public int getInt(String key, int defaultValue)
    {
        String value = getString(key);
        try
        {
            return Integer.parseInt(value);
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    @Override
    public void putLong(String key, long value)
    {
        putValue(key, String.valueOf(value));
    }

    @Override
    public long getLong(String key, long defaultValue)
    {
        String value = getString(key);
        try
        {
            return Long.parseLong(value);
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    @Override
    public void putBoolean(String key, boolean value)
    {
        putString(key, String.valueOf(value));
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue)
    {
        String booleanValue = getString(key);
        try
        {
            return Boolean.parseBoolean(booleanValue);
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    private String getString(String key)
    {
        String securedEncodedValue = mPreferences.getString(key, null);
        return decrypt(securedEncodedValue);
    }

    @Override
    public void clear()
    {
        mPreferences.edit().clear();
    }

    @Override
    public void putValue(String key, String value)
    {
        String secureValueEncoded = encrypt(value);

        mPreferences.edit().putString(encryptKey(key), encrypt(secureValueEncoded)).apply();
    }

    protected String encryptKey(String key)
    {
        return key;
    }

    protected String encrypt(String value)
    {
        return value;
    }

    protected String decrypt(String securedEncodedValue)
    {
        return securedEncodedValue;
    }

}
