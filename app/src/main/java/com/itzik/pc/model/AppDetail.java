package com.itzik.pc.model;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itzikalkotzer on 09/08/2017.
 */
public class AppDetail
{

    @SerializedName("isAllowed")
    private boolean mAllowed;

    @SerializedName("label")
    private String mAppName;

    @SerializedName("name")
    private String mAppPackage;

    public AppDetail()
    {
    }

    public AppDetail(final String appPackage)
    {
        mAppPackage = appPackage;
    }

    //    @SerializedName("icon")
    private transient Drawable mIcon;

    public String getAppName()
    {
        return mAppName;
    }

    public void setAppName(final String appName)
    {
        mAppName = appName;
    }

    public String getAppPackage()
    {
        return mAppPackage;
    }

    public void setAppPackage(final String appPackage)
    {
        mAppPackage = appPackage;
    }

    public Drawable getIcon()
    {
        return mIcon;
    }

    public void setIcon(final Drawable icon)
    {
        mIcon = icon;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final AppDetail appDetail = (AppDetail) o;

        return mAppPackage != null ? mAppPackage.equals(appDetail.mAppPackage) : appDetail.mAppPackage == null;

    }

    @Override
    public int hashCode()
    {
        return mAppPackage != null ? mAppPackage.hashCode() : 0;
    }

    public boolean isAllowed()
    {
        return mAllowed;
    }
    public void setIsAllowed(boolean isAllowed)
    {
        mAllowed = isAllowed;
    }
}
