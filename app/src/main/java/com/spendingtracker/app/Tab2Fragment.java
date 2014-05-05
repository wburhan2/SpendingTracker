package com.spendingtracker.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Wilson on 4/23/14.
 */
public class Tab2Fragment extends Fragment {

    private Button mCalculate;
    private Button mReset;
    private EditText mOriginalPriceView;
    private EditText mDiscountView;
    private TextView mAfterDiscount;
    private TextView mSaved;

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
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

        final View v = inflater.inflate(R.layout.tab2_frag_layout, container, false);
        final MyTextWatcher myTextWatcher = new MyTextWatcher();

        mCalculate = (Button)v.findViewById(R.id.calculate_disc);
        mCalculate.setEnabled(false);
        mReset = (Button)v.findViewById(R.id.reset_disc);
        mOriginalPriceView = (EditText)v.findViewById(R.id.price_value);
        mDiscountView = (EditText)v.findViewById(R.id.discount_pct_value);
        mAfterDiscount = (TextView)v.findViewById(R.id.total_value);
        mSaved = (TextView)v.findViewById(R.id.saved_value);

        mOriginalPriceView.addTextChangedListener(myTextWatcher);
        mDiscountView.addTextChangedListener(myTextWatcher);

        mDiscountView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    mCalculate.performClick();/*
                    if (mDiscountView.getText().length() != 0) {
                        InputMethodManager inputManager = (InputMethodManager) inflater.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        return true;
                    }*/
                }
                return false;
            }
        });

        mCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOriginalPriceView.getText() == null || mDiscountView.getText() == null)
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
                double save = originalPrice * (discount / 100);
                double finalPrice = originalPrice * ((100-discount) / 100);
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
                Toast.makeText(view.getContext(), "Values has been reset", 3).show();
            }
        });

        return v;
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
