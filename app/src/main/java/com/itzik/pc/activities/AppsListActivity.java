package com.itzik.pc.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.itzik.pc.R;
import com.itzik.pc.adapters.AppItemsAdapter;
import com.itzik.pc.managers.AppManager;
import com.itzik.pc.model.AppDetail;
import com.itzik.pc.model.AppsList;

import java.util.ArrayList;

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
        AppManager.getInstance().updateApps();
        ArrayList<AppDetail> allApps = AppManager.getInstance().getAllApps();
        mAdapter.addApps(allApps);
    }

    @Override
    public void onBackPressed()
    {
        AppManager.getInstance().saveAppStates();
        super.onBackPressed();
    }
}

