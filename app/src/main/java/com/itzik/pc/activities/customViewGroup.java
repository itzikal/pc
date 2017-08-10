package com.itzik.pc.activities;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by itzikalkotzer on 09/08/2017.
 */

public class customViewGroup extends ViewGroup
{
    public customViewGroup(Context context)
    {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        Log.v("customViewGroup", "**********Intercepted");
        return true;
    }
}