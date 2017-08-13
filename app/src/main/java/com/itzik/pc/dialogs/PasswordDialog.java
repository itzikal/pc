package com.itzik.pc.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.itzik.pc.R;

/**
 * Created on 5/3/2017.
 */

public class PasswordDialog
{
    public void show(Context context, final OnDismissed onDismissedListener)
    {

        View input = LayoutInflater.from(context).inflate(R.layout.password_field, null);
        final EditText password = (EditText) input.findViewById(R.id.password_field);
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setCancelable(true).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        }).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                onDismissedListener.onDialogDismissed(password.getText().toString());
            }
        }).setView(password).setTitle(R.string.password_required);
        builder.show();
    }

    public interface OnDismissed
    {
        void onDialogDismissed(String value);
    }
}
