package com.spendingtracker.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wilson on 5/5/14.
 */
public class SettingActivity extends FragmentActivity {

    private final int FEEDBACK = 1;
    private final int HELP = 2;
    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

        mListView = (ListView)findViewById(R.id.setting_list);
        setListView();
    }

    private void setListView(){
        List<Map<String, String>> settingList = new ArrayList<Map<String, String>>();
        Map<String,String> content = new HashMap<String, String>();
        content.put("title", "About");
        content.put("sub","Written by Wilson Burhan");
        settingList.add(content);
        content = new HashMap<String, String>();
        content.put("title","Feedback");
        settingList.add(content);
        content = new HashMap<String, String>();
        content.put("title","Help");
        settingList.add(content);
        SimpleAdapter adapter = new SimpleAdapter(this, settingList, R.layout.list_item,new String[]{ "title", "sub" }, new int[] { R.id.title, R.id.sub });
        mListView.setAdapter(adapter);
        MyClickListener myClickListener = new MyClickListener();
        mListView.setOnItemClickListener(myClickListener);
    }

    public class MyClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            switch (i){
                case FEEDBACK:
                    Intent email = new Intent(Intent.ACTION_SENDTO);
                    email.setType("text/plain");
                    email.setData(Uri.parse("mailto:wilson.burhan@gmail.com"));
                    email.putExtra(Intent.EXTRA_SUBJECT, "Spending Tracker Feedback");
                    try {
                        startActivity(Intent.createChooser(email, "Send Feedback:"));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(view.getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case HELP:
                    FragmentManager fm = getSupportFragmentManager();
                    InfoDialogFragment fragment = new InfoDialogFragment();
                    fragment.show(fm, "InfoFragment");
                    break;
            }
        }
    }
}
