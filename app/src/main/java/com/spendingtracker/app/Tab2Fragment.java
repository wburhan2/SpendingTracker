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
public class Tab2Fragment extends Fragment {

    private Button mCalculate;
    private Button mReset;
    private EditText mOriginalPrice;
    private EditText mDiscount;
    private TextView mAfterDiscount;
    private TextView mSaved;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        mOriginalPrice = (EditText)v.findViewById(R.id.price_value);
        mDiscount = (EditText)v.findViewById(R.id.discount_pct_value);
        mAfterDiscount = (TextView)v.findViewById(R.id.total_value);
        mSaved = (TextView)v.findViewById(R.id.saved_value);

        mOriginalPrice.addTextChangedListener(myTextWatcher);
        mDiscount.addTextChangedListener(myTextWatcher);

        mCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOriginalPrice.getText() == null || mDiscount.getText() == null)
                    return;
                String originalPriceText = mOriginalPrice.getText().toString();
                String discountText = mDiscount.getText().toString();

                if (originalPriceText.isEmpty() || discountText.isEmpty())
                    return;

                double originalPrice = Double.parseDouble(mOriginalPrice.getText().toString());
                double discount = Double.parseDouble(mDiscount.getText().toString());
                double save = originalPrice * (discount / 100);
                double finalPrice = originalPrice * ((100-discount) / 100);
                mSaved.setText("$"+String.format("%.2f", save));
                mAfterDiscount.setText("$"+String.format("%.2f" ,finalPrice));
            }
        });

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOriginalPrice.setText("");
                mDiscount.setText("");
                mAfterDiscount.setText("");
                mSaved.setText("");
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
            if (mOriginalPrice.getText() == null || mOriginalPrice.getText().toString().isEmpty() ||
                    mDiscount.getText() == null || mDiscount.getText().toString().isEmpty())
                mCalculate.setEnabled(false);
            else
                mCalculate.setEnabled(true);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }
}
