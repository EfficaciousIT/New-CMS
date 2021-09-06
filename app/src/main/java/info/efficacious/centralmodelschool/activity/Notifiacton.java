package info.efficacious.centralmodelschool.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.Tab.AdminApproval_Tab;
import info.efficacious.centralmodelschool.fragment.DailyDiaryListFragment;
import info.efficacious.centralmodelschool.fragment.Event_list_fragment;
import info.efficacious.centralmodelschool.fragment.Gallery_fragment;
import info.efficacious.centralmodelschool.fragment.LeaveListFragment;

public class Notifiacton extends AppCompatActivity {
    String value;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        FragmentManager mfragment = getSupportFragmentManager();
        try
        {
            Intent intent = getIntent();
            value = intent.getStringExtra("pagename");
            if(value.contentEquals("Gallery"))
            {
                Gallery_fragment gallery_fragment = new Gallery_fragment();
                mfragment.beginTransaction().replace(R.id.content_main, gallery_fragment).commitAllowingStateLoss();
            }
            if(value.contentEquals("Event"))
            {
                Event_list_fragment event_list_fragment = new Event_list_fragment();
                mfragment.beginTransaction().replace(R.id.content_main, event_list_fragment).commitAllowingStateLoss();
            }
            if(value.contentEquals("LeaveApply"))
            {
                AdminApproval_Tab adminApproval_tab = new AdminApproval_Tab();
                mfragment.beginTransaction().replace(R.id.content_main, adminApproval_tab).commitAllowingStateLoss();
            }
            if(value.contentEquals("Leave Approval"))
            {
                LeaveListFragment leaveListFragment = new LeaveListFragment();
                mfragment.beginTransaction().replace(R.id.content_main, leaveListFragment).commitAllowingStateLoss();
            }
            if(value.contentEquals("DailyDiary"))
            {
                DailyDiaryListFragment dailyDiaryListFragment = new DailyDiaryListFragment();
                Bundle args = new Bundle();
                args.putString("value", "DailyDiary");
                dailyDiaryListFragment.setArguments(args);
                mfragment.beginTransaction().replace(R.id.content_main,dailyDiaryListFragment).commitAllowingStateLoss();
            }
            if(value.contentEquals("HomeWork"))
            {
                DailyDiaryListFragment dailyDiaryListFragment = new DailyDiaryListFragment();
                Bundle args = new Bundle();
                args.putString("value", "HomeWork");
                dailyDiaryListFragment.setArguments(args);
                mfragment.beginTransaction().replace(R.id.content_main,dailyDiaryListFragment).commitAllowingStateLoss();
            }
        }catch (Exception ex)
        {

        }

    }
}
