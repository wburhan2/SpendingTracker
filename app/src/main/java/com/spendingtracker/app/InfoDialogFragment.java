package com.spendingtracker.app;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Wilson on 5/5/14.
 */
public class InfoDialogFragment extends DialogFragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_dialog_fragment, container,false);
        TextView textView = (TextView)view.findViewById(R.id.dialog_text);
        textView.setMovementMethod(new ScrollingMovementMethod());
        View okButton = view.findViewById(R.id.ok);
        okButton.setOnClickListener(this);
        Dialog dialog = getDialog();
        dialog.setTitle("Help");

        return view;
        }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}

