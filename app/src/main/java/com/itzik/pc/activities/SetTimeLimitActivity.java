package com.itzik.pc.activities;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.itzik.pc.R;
import com.itzik.pc.managers.AppManager;
import com.itzik.pc.services.MyAccessibilityService;

public class SetTimeLimitActivity extends AppCompatActivity
{
    private static final String LOG_TAG = SetTimeLimitActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time_limit);
        if (!isAccessibilitySettingsOn(getApplicationContext()))
        {
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        }
        if (!isUsageAccess(getApplicationContext()))
        {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }
        final TextView timeLimitValue = (TextView) findViewById(R.id.time_limit_value);
        final SeekBar seekBar = (SeekBar) findViewById(R.id.time_limit_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(final SeekBar seekBar, final int i, final boolean b)
            {
                Log.d(LOG_TAG, "onProgressChanged(), to : "+ i);
                timeLimitValue.setText(i+ " min");

            }

            @Override
            public void onStartTrackingTouch(final SeekBar seekBar)
            {
                Log.d(LOG_TAG, "onStartTrackingTouch(), ");
            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar)
            {
                Log.d(LOG_TAG, "onStopTrackingTouch(), ");
            }
        });
        
        findViewById(R.id.time_limit_set_limit).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int progress = seekBar.getProgress();
                Log.d(LOG_TAG, "Set time limit to  " + progress);
                AppManager.getInstance().setAllowTime(progress);
            }
        });

        findViewById(R.id.time_limit_clear_limit).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AppManager.getInstance().setAllowTime(-1);
            }
        });
    }

    private boolean isUsageAccess(Context context)
    {
        boolean granted = false;
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.getPackageName());

        if (mode == AppOpsManager.MODE_DEFAULT)
        {
            granted = (context.checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        }
        else
        {
            granted = (mode == AppOpsManager.MODE_ALLOWED);
        }

        return granted;
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
