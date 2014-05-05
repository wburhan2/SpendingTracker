package com.spendingtracker.app;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import java.util.HashMap;

public class MainActivity extends FragmentActivity{

    private TabHost mTabHost;
    private HashMap mapTabInfo = new HashMap();
    private TabInfo mLastTab = null;
    private ImageView mInfo;
    private ImageView mSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseTabHost(savedInstanceState);
        initializeImageView();

        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); //set the tab as per the saved state
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("tab", mTabHost.getCurrentTabTag()); //save the tab selected
        super.onSaveInstanceState(outState);
    }

    private void initialiseTabHost(Bundle args) {
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();
        TabInfo tabInfo = null;
        MainActivity.addTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab1").setIndicator(getString(R.string.tab_1_display_name)), ( tabInfo = new TabInfo("Tab1", Tab1Fragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        MainActivity.addTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab2").setIndicator(getString(R.string.tab_2_display_name)), ( tabInfo = new TabInfo("Tab2", Tab2Fragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        // Default to first tab
        MyTabListener myTabListener = new MyTabListener();
        myTabListener.onTabChanged("Tab1");

        mTabHost.setOnTabChangedListener(myTabListener);
    }

    private void initializeImageView() {
        mInfo = (ImageView)findViewById(R.id.info);
        mSetting = (ImageView)findViewById(R.id.setting);
        mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getSupportFragmentManager();
                InfoDialogFragment fragment = new InfoDialogFragment();
                fragment.show(fm, "InfoFragment");
            }
        });

        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
            }
        });
    }

    private static void addTab(MainActivity activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
        // Attach a Tab view factory to the spec
        tabSpec.setContent(activity.new TabFactory(activity));
        String tag = tabSpec.getTag();

        // Check to see if we already have a fragment for this tab, probably
        // from a previously saved state.  If so, deactivate it, because our
        // initial state is that a tab isn't shown.
        tabInfo.fragment = activity.getSupportFragmentManager().findFragmentByTag(tag);
        if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.detach(tabInfo.fragment);
            ft.commit();
            activity.getSupportFragmentManager().executePendingTransactions();
        }

        tabHost.addTab(tabSpec);
    }

    private class TabInfo {
        private String tag;
        private Class clss;
        private Bundle args;
        private Fragment fragment;
        TabInfo(String tag, Class clss, Bundle args) {
            this.tag = tag;
            this.clss = clss;
            this.args = args;
        }
    }

    class TabFactory implements TabHost.TabContentFactory {

        private final Context mContext;

        public TabFactory(Context context) {
            mContext = context;
        }

        /** (non-Javadoc)
         * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
         */
        @Override
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
    }

    class MyTabListener implements TabHost.OnTabChangeListener {

        @Override
        public void onTabChanged(String s) {
            TabInfo newTab = (TabInfo)mapTabInfo.get(s);
            if (mLastTab != newTab) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if (mLastTab != null) {
                    if (mLastTab.fragment != null) {
                        ft.detach(mLastTab.fragment);
                    }
                }
                if (newTab != null) {
                    if (newTab.fragment == null) {
                        newTab.fragment = Fragment.instantiate(getBaseContext(), newTab.clss.getName(), newTab.args);
                        ft.add(R.id.realtabcontent, newTab.fragment, newTab.tag);
                    } else {
                        ft.attach(newTab.fragment);
                    }
                }

                mLastTab = newTab;
                ft.commit();
                getSupportFragmentManager().executePendingTransactions();
            }
        }
    }
}
