package com.spendingtracker.app;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Wilson on 4/23/14.
 */
public class Tab2Fragment extends Fragment implements Observer{

    private Button mCalculate;
    private Button mReset;
    private EditText mOriginalPriceView;
    private EditText mDiscountView;
    private TextView mAfterDiscount;
    private TextView mSaved;
    private TextView mTaxPct;
    private TextView mTaxValue;
    BaseApp mBase;

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        final View v = inflater.inflate(R.layout.tab2_frag_layout, container, false);
        final MyTextWatcher myTextWatcher = new MyTextWatcher();

        mBase = (BaseApp)getActivity().getApplication();
        mBase.getObserver().addObserver(this);
        mCalculate = (Button)v.findViewById(R.id.calculate_disc);
        mCalculate.setEnabled(false);
        mReset = (Button)v.findViewById(R.id.reset_disc);
        mOriginalPriceView = (EditText)v.findViewById(R.id.price_value);
        mDiscountView = (EditText)v.findViewById(R.id.discount_pct_value);
        mAfterDiscount = (TextView)v.findViewById(R.id.total_value);
        mSaved = (TextView)v.findViewById(R.id.saved_value);
        mTaxPct = (TextView)v.findViewById(R.id.tax_pct);
        mTaxValue = (TextView)v.findViewById(R.id.tax_value);

        mOriginalPriceView.addTextChangedListener(myTextWatcher);
        mDiscountView.addTextChangedListener(myTextWatcher);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO && android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            mDiscountView.setTextColor(Color.BLACK);
            mOriginalPriceView.setTextColor(Color.BLACK);
        }

        mDiscountView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE)
                    mCalculate.performClick();
                return false;
            }
        });

        mCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOriginalPriceView.getText() == null || mDiscountView.getText() == null || mTaxPct.getText() == null)
                    return;
                String originalPriceText = mOriginalPriceView.getText().toString();
                String discountText = mDiscountView.getText().toString();

                if (originalPriceText.isEmpty() || discountText.isEmpty()) {
                    mSaved.setText("");
                    mAfterDiscount.setText("");
                    return;
                }

                double originalPrice = Double.parseDouble(mOriginalPriceView.getText().toString());
                double discount = Double.parseDouble(mDiscountView.getText().toString());
                double taxPct = Double.parseDouble(mTaxPct.getText().toString());
                double save = originalPrice * (discount / 100);
                double finalPrice = originalPrice * ((100-discount) / 100);
                double tax = finalPrice * (taxPct / 100);
                finalPrice += tax;
                mTaxValue.setText("$"+String.format("%.2f", tax));
                mSaved.setText("$"+String.format("%.2f", save));
                mAfterDiscount.setText("$"+String.format("%.2f" ,finalPrice));
            }
        });

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOriginalPriceView.setText("");
                mDiscountView.setText("");
                mAfterDiscount.setText("");
                mSaved.setText("");
                mTaxValue.setText("$0.00");
                Toast.makeText(view.getContext(), "Values has been reset", Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mTaxPct.setText(String.valueOf(mBase.getObserver().getTax()));
    }

    @Override
    public void update(Observable observable, Object o) {
        mTaxPct.setText(String.valueOf(mBase.getObserver().getTax()));
        mCalculate.performClick();
    }

    class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (mOriginalPriceView.getText() == null || mOriginalPriceView.getText().toString().isEmpty() ||
                    mDiscountView.getText() == null || mDiscountView.getText().toString().isEmpty())
                mCalculate.setEnabled(false);
            else
                mCalculate.setEnabled(true);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }
}
