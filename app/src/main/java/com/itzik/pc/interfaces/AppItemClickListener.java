package com.itzik.pc.interfaces;

import android.view.View;

import com.itzik.pc.model.AppDetail;

/**
 * Created by itzikalkotzer on 09/08/2017.
 */

public interface AppItemClickListener
{
    void onClick(final View view, AppDetail app);
}
