package com.itzik.pc.managers;

import android.content.Context;

/**
 * Created by itzikalkotzer on 09/08/2017.
 */

public class AppManager
{
    private static final String LOG_TAG = AppManager.class.getSimpleName();
    private static AppManager mInstance;
    private Context mContext;
    private PreferancesManager mPreferences;

    private AppManager(Context context)
    {
        mContext = context;
    }

    public static AppManager getInstance()
    {
        if(mInstance == null)
        {
            throw new RuntimeException("App manager was not initilized");
        }
        return mInstance;
    }

    public static AppManager initInstance(Context context)
    {
        if (mInstance == null)
        {
            mInstance = new AppManager(context);
        }
        return mInstance;
    }

    public PreferancesManager getPreferences()
    {
        if (mPreferences == null)
        {
            mPreferences = new PreferancesManager(mContext);
        }
        return mPreferences;
    }
}
