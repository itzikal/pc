package com.itzik.pc.managers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.itzik.pc.model.AppDetail;
import com.itzik.pc.model.AppsList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by itzikalkotzer on 09/08/2017.
 */

public class AppManager
{
    private static final String LOG_TAG = AppManager.class.getSimpleName();
    private AppsList mApps = new AppsList();

    private static AppManager mInstance;
    private Context mContext;
    private PreferancesManager mPreferences;

    private AppManager(Context context)
    {
        mContext = context;
        updateApps();
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

    public void updateApps()
    {
        mApps.update(getPreferences().getApps());
        ArrayList<AppDetail> savedApps = mApps.getApps();
        final PackageManager manager = mContext.getPackageManager();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
        for (ResolveInfo ri : availableActivities)
        {

            AppDetail app = new AppDetail();
            app.setAppName(ri.loadLabel(manager).toString());
            app.setAppPackage(ri.activityInfo.packageName);
            if(!mApps.isContainsApp(app))
            {
                mApps.addApp(app);
            }
            savedApps.remove(app);
        }
        mApps.removeApps(savedApps);
        saveAppStates();
    }

    public ArrayList<AppDetail> getAllowedApps()
    {
        return mApps.getPremittedApps();
    }

    public ArrayList<AppDetail> getAllApps(){
        return mApps.getApps();
    }

    public void saveAppStates()
    {
        getPreferences().saveApps(mApps);
    }
}
