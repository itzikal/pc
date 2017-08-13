package com.itzik.pc.general;

import android.app.Application;

import com.itzik.pc.managers.AppManager;

/**
 * Created by itzikalkotzer on 09/08/2017.
 */

public class PCApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        AppManager.initInstance(getApplicationContext());
    }
}
