package com.itzik.pc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.itzik.pc.R;
import com.itzik.pc.adapters.AppItemsAdapter;
import com.itzik.pc.dialogs.PasswordDialog;
import com.itzik.pc.interfaces.AppItemClickListener;
import com.itzik.pc.managers.AppManager;
import com.itzik.pc.managers.PreferencesWrapper;
import com.itzik.pc.managers.TimeoutManager;
import com.itzik.pc.model.AppDetail;
import com.itzik.pc.utils.TextUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private AppItemsAdapter mAdapter;
    private TimeoutManager mTimeoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

//        WindowManager manager = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE));
//        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
//        localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
//        localLayoutParams.gravity = Gravity.TOP;
//        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
//                // this is to enable the notification to recieve touch events
//                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
//                // Draws over status bar
//                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
//        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//        localLayoutParams.height = (int) (50 * getResources().getDisplayMetrics().scaledDensity);
//        localLayoutParams.format = PixelFormat.TRANSPARENT;
//        customViewGroup view = new customViewGroup(this);
//        manager.addView(view, localLayoutParams);
//
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mTimeoutManager = new TimeoutManager();

        findViewById(R.id.show_app_list).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View view)
            {
                final String password = new PreferencesWrapper(MainActivity.this).getString("Password", null);
                if (TextUtil.isEmpty(password))
                {
                    Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(i);
                }
                else
                {
                    PasswordDialog dialog = new PasswordDialog();
                    dialog.show(MainActivity.this, new PasswordDialog.OnDismissed()
                    {
                        @Override
                        public void onDialogDismissed(final String value)
                        {
                            if (password.equals(value))
                            {
                                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                                startActivity(i);
                            }

                            else
                            {
                                Toast.makeText(MainActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        final RecyclerView list = (RecyclerView) findViewById(R.id.luncher_apps_list);
        mAdapter = new AppItemsAdapter(MainActivity.this, new AppItemClickListener()
        {
            @Override
            public void onClick(final View view, AppDetail app)
            {

                if(mTimeoutManager.hasFreeTime(MainActivity.this))
                {
                    Intent i = getPackageManager().getLaunchIntentForPackage(app.getAppPackage());
                    startActivity(i);
                    mTimeoutManager.setAlarm(MainActivity.this);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No time left!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mAdapter.setIsMultipulSelection(false);
        list.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
        list.setAdapter(mAdapter);

    }

    @Override
    public void onResume()
    {
        super.onResume();
        ArrayList<AppDetail> allowedApps = AppManager.getInstance().getAllowedApps();
        mAdapter.addApps(allowedApps);
        mTimeoutManager.cancelAlarm(MainActivity.this);
    }

    @Override
    public void onBackPressed()
    {
        Toast.makeText(MainActivity.this, "You shell not pass!!", Toast.LENGTH_SHORT).show();

    }

}

