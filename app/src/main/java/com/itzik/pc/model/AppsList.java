package com.itzik.pc.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by itzikalkotzer on 09/08/2017.
 */

public class AppsList
{
    @SerializedName("AppList")
    private final ArrayList<AppDetail> mApps = new ArrayList<>();

    public AppsList(){
    }

    public ArrayList<AppDetail> getApps()
    {
        return mApps;
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
}
