package com.itzik.pc.adapters;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itzik.pc.R;
import com.itzik.pc.interfaces.AppItemClickListener;
import com.itzik.pc.model.AppDetail;

import java.util.ArrayList;

/**
 * Created by itzikalkotzer on 09/08/2017.
 */
public class AppItemsAdapter extends RecyclerView.Adapter<AppItemViewHolder>
{
    private final ArrayList<AppDetail> mApps = new ArrayList<>();
    private Context mContext;
    private AppItemClickListener mOnClickListener;
    private boolean mIsMultipulSelection;

    public AppItemsAdapter(Context context, AppItemClickListener onClickListener)
    {

        mContext = context;
        mOnClickListener = onClickListener;
    }

    public void setIsMultipulSelection(boolean isMultipulSelection)
    {
        mIsMultipulSelection = isMultipulSelection;
    }

    @Override
    public AppItemViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        View contactView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app, parent, false);
        return new AppItemViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(final AppItemViewHolder holder, final int position)
    {
        final AppDetail app = mApps.get(position);

        final PackageManager packageManager = mContext.getPackageManager();
        try
        {
            ApplicationInfo ai = packageManager.getApplicationInfo(app.getAppPackage(), PackageManager.GET_META_DATA);
            holder.icon.setImageDrawable(ai.loadIcon(packageManager));
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }

        holder.name.setText(app.getAppName());
        if(mIsMultipulSelection)
        {
            holder.itemView.setBackgroundColor(app.isAllowed() ? Color.CYAN : Color.TRANSPARENT);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(final View view)
            {
                if(mIsMultipulSelection)
                {
                    app.setIsAllowed(!app.isAllowed());
                }
                holder.itemView.setBackgroundColor(app.isAllowed() ? Color.CYAN : Color.TRANSPARENT);
                if (mOnClickListener != null)
                {
                    mOnClickListener.onClick(view, app);
                }
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return mApps.size();
    }

    public void addApps(ArrayList<AppDetail> apps)
    {
        mApps.clear();
        mApps.addAll(apps);
        notifyDataSetChanged();
    }

    public ArrayList<AppDetail> getItems(){
        return mApps;
    }
}
