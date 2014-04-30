package com.spendingtracker.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Wilson on 4/23/14.
 */
public class Tab1Fragment extends Fragment {

    private Button mCalculate;
    private Button mReset;
    private EditText mCost;
    private EditText mTip;
    private TextView mTipTextView;
    private TextView mTotalTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }

        final View v = inflater.inflate(R.layout.tab1_frag_layout, container, false);
        final MyTextWatcher myTextWatcher = new MyTextWatcher();

        mCalculate = (Button)v.findViewById(R.id.calculate_tip);
        mReset = (Button)v.findViewById(R.id.reset_tip);
        mTipTextView = (TextView)v.findViewById(R.id.tip_value);
        mTotalTextView = (TextView)v.findViewById(R.id.total_value);
        mCalculate.setEnabled(false);
        mCost = (EditText)v.findViewById(R.id.cost_value);
        mTip = (EditText)v.findViewById(R.id.tip_pct_value);

        mCost.addTextChangedListener(myTextWatcher);
        mTip.addTextChangedListener(myTextWatcher);

        mCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mCost.getText() == null || mTip.getText() == null)
                    return;
                String costText = mCost.getText().toString();
                String tipText = mTip.getText().toString();

                if (costText.isEmpty() || tipText.isEmpty())
                    return;

                double costValue = Double.parseDouble(mCost.getText().toString());
                double tipValue = Double.parseDouble(mTip.getText().toString());

                double tipMoney = costValue * (tipValue/100);

                double totalCost = costValue + tipMoney;

                mTipTextView.setText("$"+String.format("%.2f" ,tipMoney));
                mTotalTextView.setText("$"+String.format("%.2f" ,totalCost));
            }
        });

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCost.setText("");
                mTip.setText("");
                mTipTextView.setText("");
                mTotalTextView.setText("");
            }
        });

        return v;
    }

    class MyTextWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (mCost.getText() == null || mCost.getText().toString().isEmpty() ||
                mTip.getText() == null || mTip.getText().toString().isEmpty())
                mCalculate.setEnabled(false);
            else
                mCalculate.setEnabled(true);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }
}
