package com.itzik.pc.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.itzik.pc.R;
import com.itzik.pc.adapters.AppItemsAdapter;
import com.itzik.pc.managers.AppManager;
import com.itzik.pc.model.AppDetail;
import com.itzik.pc.model.AppsList;

import java.util.List;

/**
 * Created by itzikalkotzer on 08/08/2017.
 */

public class AppsListActivity extends Activity
{
    private AppItemsAdapter mAdapter;
    private AppsList premitedApps;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        final RecyclerView list = (RecyclerView) findViewById(R.id.select_apps_apps_list);
        mAdapter = new AppItemsAdapter(AppsListActivity.this, null);
        mAdapter.setIsMultipulSelection(true);
        list.setLayoutManager(new GridLayoutManager(AppsListActivity.this, 4));
        list.setAdapter(mAdapter);
        loadApps();

    }

    private void loadApps()
    {
        AppsList appsList = AppManager.getInstance().getPreferences().getPremitedApps();
        this.premitedApps = appsList == null ? new AppsList() : appsList;
        final PackageManager manager = getPackageManager();



        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
        for (ResolveInfo ri : availableActivities)
        {

            AppDetail app = new AppDetail();
            app.setAppName(ri.loadLabel(manager).toString());
            app.setAppPackage(ri.activityInfo.packageName);
            if(!this.premitedApps.isContainsApp(app))
            {
                this.premitedApps.addApp(app);
            }
        }
        mAdapter.addApps(this.premitedApps.getApps());

    }

    @Override
    public void onBackPressed()
    {
        AppManager.getInstance().getPreferences().savePremitedApps(premitedApps);
        super.onBackPressed();
    }
}

