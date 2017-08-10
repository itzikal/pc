package com.itzik.pc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.itzik.pc.R;
import com.itzik.pc.utils.UStats;

public class SetTimeLimitActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time_limit);
        if (UStats.getUsageStatsList(this).isEmpty()){
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }

        findViewById(R.id.time_limit_get_stats).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UStats.printCurrentUsageStatus(SetTimeLimitActivity.this);
//                    UStats.getUsageStatsList()
            }
        });
    }
}
