package com.itzik.pc.managers;

import android.content.Context;

import com.itzik.pc.model.AppsList;

/**
 * Created by itzikalkotzer on 09/08/2017.
 */

public class PreferancesManager
{
    private static final String SAVE_PREMITED_APPS = "SAVE_PREMITED_APPS";
    private static final String ALLOWED_TIME = "ALLOWED_TIME";
    private final PreferencesWrapper mPreferencesWrapper;

    public PreferancesManager(Context context)
    {
        mPreferencesWrapper = new PreferencesWrapper(context);
    }

    public void saveApps(AppsList list)
    {
        mPreferencesWrapper.putObject(SAVE_PREMITED_APPS, list);
    }

    public AppsList getApps()
    {
       return mPreferencesWrapper.getObject(SAVE_PREMITED_APPS, AppsList.class);
    }

    public int getAllowedTime()
    {
        return mPreferencesWrapper.getInt(ALLOWED_TIME, -1);
    }

    public void saveAllowTime(final int allowTime)
    {
        mPreferencesWrapper.putInt(ALLOWED_TIME, allowTime);
    }
}
