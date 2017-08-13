package com.itzik.pc.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itzik.pc.R;

/**
 * Created by itzikalkotzer on 09/08/2017.
 */
class AppItemViewHolder extends RecyclerView.ViewHolder
{
    TextView name;
    ImageView icon;

    AppItemViewHolder(final View itemView)
    {
        super(itemView);
        name = itemView.findViewById(R.id.item_app_label);
        icon = itemView.findViewById(R.id.item_app_icon);
    }
}
