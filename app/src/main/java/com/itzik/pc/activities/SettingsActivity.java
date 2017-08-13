package com.itzik.pc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.itzik.pc.dialogs.PasswordDialog;
import com.itzik.pc.managers.PreferencesWrapper;
import com.itzik.pc.R;
import com.itzik.pc.utils.TextUtil;

public class SettingsActivity extends AppCompatActivity
{

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
    }
}
