package com.itzik.pc.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.itzik.pc.R;
import com.itzik.pc.managers.TimeoutManager;
import com.itzik.pc.services.MyAccessibilityService;

public class SetTimeLimitActivity extends AppCompatActivity
{
    private static final String LOG_TAG = SetTimeLimitActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time_limit);
        if (!isAccessibilitySettingsOn(getApplicationContext())) {
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        }
        //        if (UStats.getUsageStatsList(this).isEmpty()){
        //            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        //            startActivity(intent);
        //        }

        findViewById(R.id.time_limit_get_stats).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //                UStats.printCurrentUsageStatus(SetTimeLimitActivity.this);
                //                    UStats.getUsageStatsList()
                TimeoutManager t = new TimeoutManager();
                t.setAlarm(getApplicationContext());
            }
        });
    }

    private boolean isAccessibilitySettingsOn(Context mContext)
    {
        int accessibilityEnabled = 0;
        final String service = getPackageName() + "/" + MyAccessibilityService.class.getCanonicalName();
        try
        {
            accessibilityEnabled = Settings.Secure.getInt(mContext.getApplicationContext().getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v(LOG_TAG, "accessibilityEnabled = " + accessibilityEnabled);
        }
        catch (Settings.SettingNotFoundException e)
        {
            Log.e(LOG_TAG, "Error finding setting, default accessibility to not found: " + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1)
        {
            Log.v(LOG_TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(mContext.getApplicationContext().getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null)
            {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext())
                {
                    String accessibilityService = mStringColonSplitter.next();

                    Log.v(LOG_TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase(service))
                    {
                        Log.v(LOG_TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        }
        else
        {
            Log.v(LOG_TAG, "***ACCESSIBILITY IS DISABLED***");
        }

        return false;
    }
}
