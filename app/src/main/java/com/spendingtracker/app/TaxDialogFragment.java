package com.spendingtracker.app;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Wilson on 5/4/14.
 */
public class TaxDialogFragment extends DialogFragment implements Observer{

    BaseApp mBase;
    EditText mTax;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tax_dialog_fragment, container,false);
        mBase = (BaseApp)getActivity().getApplication();
        mBase.getObserver().addObserver(this);

        Button okButton = (Button)view.findViewById(R.id.ok_button);
        mTax = (EditText)view.findViewById(R.id.federal_tax);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTax.getText() == null)
                    return;
                String taxText = mTax.getText().toString();

                if (taxText.isEmpty())
                    dismiss();
                mBase.getObserver().setTax(Double.parseDouble(taxText));
                dismiss();
            }
        });

        Button cancelButton = (Button)view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        Dialog dialog = getDialog();
        dialog.setTitle("Set Sales Tax");
        return view;
    }

    @Override
    public void update(Observable observable, Object o) {
        mTax.setText(String.valueOf(mBase.getObserver().getTax()));
    }
}
