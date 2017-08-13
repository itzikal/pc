package com.itzik.pc.utils;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by User on 3/2/15.
 */
public class UStats
{
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("M-d-yyyy HH:mm:ss");
    public static final String LOG_TAG = UStats.class.getSimpleName();

    @SuppressWarnings("ResourceType")
    public static void getStats(Context context)
    {
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
        int interval = UsageStatsManager.INTERVAL_DAILY;

        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        long startTime = calendar.getTimeInMillis();

        Log.d(LOG_TAG, "Range start:" + dateFormat.format(startTime));
        Log.d(LOG_TAG, "Range end:" + dateFormat.format(endTime));

        UsageEvents uEvents = usm.queryEvents(startTime, endTime);
        while (uEvents.hasNextEvent())
        {
            UsageEvents.Event e = new UsageEvents.Event();
            uEvents.getNextEvent(e);

            if (e != null)
            {
                Log.d(LOG_TAG, "Event: " + e.getPackageName() + "\t" + e.getTimeStamp());
            }
        }
    }

    public static List<UsageStats> getUsageStatsList(Context context)
    {
        UsageStatsManager usm = getUsageStatsManager(context);

        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long startTime = calendar.getTimeInMillis();

        Log.d(LOG_TAG, "Range start:" + dateFormat.format(startTime));
        Log.d(LOG_TAG, "Range end:" + dateFormat.format(endTime));

        List<UsageStats> usageStatsList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);
        ArrayList<UsageStats> onlyUsage = new ArrayList<>();
        long time = 0;
        for (UsageStats usageStats : usageStatsList)
        {
            if(usageStats.getTotalTimeInForeground() > 0 )
            {
                onlyUsage.add(usageStats);
                time+= usageStats.getTotalTimeInForeground();
            }
        }
        Log.d(LOG_TAG, "getUsageStatsList(), total time: " + time);
        Log.d(LOG_TAG, "Range start:" + dateFormat.format(time));
        return onlyUsage;
    }

    public static void printUsageStats(List<UsageStats> usageStatsList)
    {
        for (UsageStats u : usageStatsList)
        {
            Log.d(LOG_TAG, "Pkg: " + u.getPackageName() + "\t" + "ForegroundTime: " + u.getTotalTimeInForeground());
        }

    }

    public static void printCurrentUsageStatus(Context context)
    {
        printUsageStats(getUsageStatsList(context));
    }

    @SuppressWarnings("ResourceType")
    private static UsageStatsManager getUsageStatsManager(Context context)
    {
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
        return usm;
    }
}
