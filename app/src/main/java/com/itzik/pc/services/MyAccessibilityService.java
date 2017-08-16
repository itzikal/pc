package com.itzik.pc.services;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by itzikalkotzer on 16/08/2017.
 */

public class MyAccessibilityService extends AccessibilityService
{
    private static final String LOG_TAG = MyAccessibilityService.class.getSimpleName();
    @Override
    public void onAccessibilityEvent(final AccessibilityEvent e)
    {
        Log.d(LOG_TAG, "onAccessibilityEvent(), event: " + e.getEventType()+", string:" +e.toString()+", content:"+e.getContentDescription());
        Log.d(LOG_TAG, "onAccessibilityEvent(), package: "+e.getPackageName() +", "+e.getEventType());
    }

    @Override
    public void onInterrupt()
    {

    }
}
