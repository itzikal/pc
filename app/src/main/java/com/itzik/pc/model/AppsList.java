package com.itzik.pc.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by itzikalkotzer on 09/08/2017.
 */

public class AppsList
{
    private static final String LOG_TAG = AppsList.class.getSimpleName();
    @SerializedName("AppList")
    private final ArrayList<AppDetail> mApps = new ArrayList<>();

    public AppsList(){
    }

    public ArrayList<AppDetail> getApps()
    {
        return new ArrayList<>(mApps);
    }

    public ArrayList<AppDetail> getPremittedApps()
    {
        ArrayList<AppDetail> clone = new ArrayList<>();
        for (AppDetail app : mApps)
        {
            if(app.isAllowed())
            {
                clone.add(app);
            }
        }


        return clone;
    }
    public boolean isContainsApp(AppDetail detail)
    {
        return mApps.contains(detail);
    }

    public AppDetail getApp(final AppDetail app)
    {
        if(mApps.contains(app))
        {
            return mApps.get(mApps.indexOf(app));
        }
        return null;
    }

    public void addApp(final AppDetail app)
    {
        mApps.add(app);
    }

    public void update(final AppsList apps)
    {
        if(apps == null)
        {
            return;
        }
        mApps.clear();
        mApps.addAll(apps.getApps());
    }

    public void removeApps(final ArrayList<AppDetail> appsToRemove)
    {
        for (AppDetail appDetail : appsToRemove)
        {
            Log.d(LOG_TAG, "removeApps(), app:" + appDetail.getAppName());
        }

        mApps.removeAll(appsToRemove);
    }
}
