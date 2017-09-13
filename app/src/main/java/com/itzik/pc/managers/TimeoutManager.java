package com.itzik.pc.managers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.itzik.pc.activities.MainActivity;
import com.itzik.pc.utils.UStats;

import java.util.concurrent.TimeUnit;

/**
 * Created by itzikalkotzer on 16/08/2017.
 */

public class TimeoutManager extends BroadcastReceiver
{
    private static final String LOG_TAG = TimeoutManager.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent)
    {
//        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
//        wl.acquire();

        // Put here YOUR code.
        Toast.makeText(context, "Alarm !!!!!!!!!!", Toast.LENGTH_LONG).show(); // For example
        long l = calculateTimeLeft(context);
        Log.d(LOG_TAG, "onReceive(), time left: " +l);
//        if(l > 0)
//        {
            cancelAlarm(context);
//            setAlarm(context);

//        }
//        else
//        {
            context.startActivity(new Intent(context, MainActivity.class));
//        }
//        wl.release();
    }

    public void setAlarm(Context context)
    {
//        AppManager.getInstance().getAllowedApps()

        Log.d(LOG_TAG, "setAlarm(), ");
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, TimeoutManager.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ calculateTimeLeft(context), pi);
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, TimeoutManager.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    private long calculateTimeLeft(Context context){
        long spent = UStats.getTimeSpanetToday(context);
        Log.d(LOG_TAG, "calculateTimeLeft(), time spent: " + TimeUnit.MILLISECONDS.toSeconds(spent));
        long timeLeft = TimeUnit.SECONDS.toMillis(85) - spent;
        Log.d(LOG_TAG, "calculateTimeLeft(), " +timeLeft);
        return timeLeft;
    }

    public boolean hasFreeTime(Context context)
    {
        return calculateTimeLeft(context) > 0;

    }
}

