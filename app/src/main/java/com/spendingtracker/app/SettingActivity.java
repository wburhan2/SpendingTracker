package com.spendingtracker.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Wilson on 5/5/14.
 */
public class SettingActivity extends FragmentActivity {

    private final int SALES_TAX = 0;
    private final int FEEDBACK = 1;
    private final int HELP = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

        ListView listView = (ListView)findViewById(R.id.setting_list);
        MyClickListener myClickListener = new MyClickListener();
        listView.setOnItemClickListener(myClickListener);

    }

    public class MyClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String a = (String)adapterView.getItemAtPosition(i);
            switch (i){
                case SALES_TAX:
                    Toast.makeText(view.getContext(),"You pressed index 0 " + a, 2).show();
                    break;
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
                case HELP:
                    break;
            }
        }
    }
}
