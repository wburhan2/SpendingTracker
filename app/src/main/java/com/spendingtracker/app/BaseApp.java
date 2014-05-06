package com.spendingtracker.app;

import android.app.Application;

/**
 * Created by Wilson on 5/5/14.
 */
public class BaseApp extends Application {
    Tax mTax;

    @Override
    public void onCreate() {
        super.onCreate();
        mTax = new Tax();
    }

    public Tax getObserver() {
        return mTax;
    }
}
