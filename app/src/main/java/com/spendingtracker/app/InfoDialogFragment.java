package com.spendingtracker.app;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Wilson on 5/4/14.
 */
public class InfoDialogFragment extends DialogFragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.info_dialog_fragment, container,false);

        View okButton = view.findViewById(R.id.ok_button);
        okButton.setOnClickListener(this);

        Dialog dialog = getDialog();
        dialog.setTitle("About");

        return view;
    }



    @Override
    public void onClick(View view) {
        dismiss();
    }
}
