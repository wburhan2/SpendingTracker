package com.spendingtracker.app;

import java.util.Observable;

/**
 * Created by Wilson on 5/6/14.
 */
public class Tax extends Observable {
    private double mTax = 0d;

    public double getTax(){
        return mTax;
    }

    public void setTax (double tax) {
        mTax = tax;
        setChanged();
        notifyObservers(mTax);
    }
}
