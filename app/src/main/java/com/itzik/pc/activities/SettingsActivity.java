package com.itzik.pc.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.itzik.pc.R;
import com.itzik.pc.dialogs.PasswordDialog;
import com.itzik.pc.managers.PreferencesWrapper;
import com.itzik.pc.utils.TextUtil;

public class SettingsActivity extends AppCompatActivity
{
    private static final String LOG_TAG = SettingsActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        findViewById(R.id.setting_select_apps).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View view)
            {
                Intent i = new Intent(SettingsActivity.this, AppsListActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.setting_set_time_limit).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View view)
            {
                Intent i = new Intent(SettingsActivity.this, SetTimeLimitActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.setting_set_password).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View view)
            {
               PasswordDialog dialog = new PasswordDialog();
                dialog.show(SettingsActivity.this, new PasswordDialog.OnDismissed() {
                    @Override
                    public void onDialogDismissed(final String value)
                    {
                        if(!TextUtil.isEmpty(value))
                        {
                            new PreferencesWrapper(SettingsActivity.this).putString("Password", value);
                        }
                    }
                });
            }
        });

        final Button lockDevice = (Button)findViewById(R.id.setting_set_as_default_launcher);
        lockDevice.setText(isDefualtLauncher() ? "Release device":"Lock device");
        lockDevice.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View view)
            {
                Log.d(LOG_TAG, "onClick(), is default:" + isDefualtLauncher());
                PackageManager p = getPackageManager();
                ComponentName cN = new ComponentName(SettingsActivity.this, LauncherActivity.class);
                if(isDefualtLauncher())
                {
                    p.setComponentEnabledSetting(cN, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                    p.setComponentEnabledSetting(cN, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                }
                else{
                    p.setComponentEnabledSetting(cN, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                    p.setComponentEnabledSetting(cN, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                }

                startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
            }
        });
    }

    private void option1()
    {
        Context context = getApplicationContext();
        PackageManager packageManager = context.getPackageManager();
        ComponentName componentName = new ComponentName(context, LauncherActivity.class);
        int componentEnabledSetting = packageManager.getComponentEnabledSetting(componentName);
        Log.d(LOG_TAG, "onClick(), componentEnabledSetting - "+componentEnabledSetting);
        if(!isDefualtLauncher())
        //componentEnabledSetting == PackageManager.COMPONENT_ENABLED_STATE_ENABLED)
        {
            Log.d(LOG_TAG, "onClick(), Not default luncher");
            Log.d(LOG_TAG, "onClick(), was enabled - try to enable ");
            if(componentEnabledSetting != PackageManager.COMPONENT_ENABLED_STATE_ENABLED)
            {
                Log.d(LOG_TAG, "onClick(), enabling");
                packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
            }
        }
       // if(componentEnabledSetting == PackageManager.COMPONENT_ENABLED_STATE_DISABLED || componentEnabledSetting == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT)
        else
        {
            Log.d(LOG_TAG, "onClick(), is default luncher");
            Log.d(LOG_TAG, "try to disable");
            if(componentEnabledSetting != PackageManager.COMPONENT_ENABLED_STATE_DISABLED)
            {
                Log.d(LOG_TAG, "disableing");
                packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            }

        }

        Intent selector = new Intent(Intent.ACTION_MAIN);

        selector.addCategory(Intent.CATEGORY_HOME);
        selector.addCategory(Intent.CATEGORY_DEFAULT);
        ComponentName[] components = new ComponentName[] {new ComponentName("com.android.launcher", "com.android.launcher.Launcher"), componentName};
        getPackageManager().clearPackagePreferredActivities("com.android.launcher");
        //                getPackageManager().addPreferredActivity(selector, IntentFilter.MATCH_CATEGORY_SCHEME, components, componentName);
        //addPreferredActivity(filter, IntentFilter.MATCH_CATEGORY_SCHEME, components, component);
        context.startActivity(selector);
    }

    private boolean isDefualtLauncher(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfo = getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        String currentHomePackage = resolveInfo.activityInfo.packageName;
        String packageName = getPackageName();
        Log.d(LOG_TAG, "onClick(), curent: " + currentHomePackage+", my: "+packageName);
        return (packageName.equals( currentHomePackage));

    }



}
