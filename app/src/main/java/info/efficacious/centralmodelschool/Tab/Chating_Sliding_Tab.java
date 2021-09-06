package info.efficacious.centralmodelschool.Tab;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.fragment.Group_Chat_Fragment;
import info.efficacious.centralmodelschool.fragment.Management_Chat_Fragment;
import info.efficacious.centralmodelschool.fragment.Student_Chat_Fragment;
import info.efficacious.centralmodelschool.fragment.Teacher_Chat_Fragment;

import java.util.ArrayList;
import java.util.List;


public class Chating_Sliding_Tab extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String role_id;
    private static final String TAG = "Chating_Sliding_Tab";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_calender, container, false);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        role_id = settings.getString("TAG_USERTYPEID", "");
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        Log.e(TAG, "Chating_Sliding_Tab: "+role_id );

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity.getSupportActionBar() != null;
//        getActivity().getActionBar().setTitle("Chat");
//         activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.lightorange)));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewPager(viewPager);
        // after you set the adapter you have to check if view is laid out, i did a custom method for it
        if (ViewCompat.isLaidOut(tabLayout)) {
            setViewPagerListener();
        } else {
            tabLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    setViewPagerListener();
                    tabLayout.removeOnLayoutChangeListener(this);
                }
            });
        }
    }

    private void setViewPagerListener() {
        tabLayout.setupWithViewPager(viewPager);
        // use class TabLayout.ViewPagerOnTabSelectedListener
        // note that it's a class not an interface as OnTabSelectedListener, so you can't implement it in your activity/fragment
        // methods are optional, so if you don't use them, you can not override them (e.g. onTabUnselected)
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        try {
            if (role_id.contentEquals("1") || role_id.contentEquals("2") || role_id.contentEquals("3")) {
                viewPagerAdapter.addFragment(new Teacher_Chat_Fragment(), "Teacher");
                viewPagerAdapter.addFragment(new Student_Chat_Fragment(), "Student");
                viewPagerAdapter.addFragment(new Group_Chat_Fragment(), "Group");

            } else {
//                Toast.makeText(getContext(), "in_Admin_View", Toast.LENGTH_SHORT).show();
                viewPagerAdapter.addFragment(new Student_Chat_Fragment(), "Student");
                viewPagerAdapter.addFragment(new Teacher_Chat_Fragment(), "Teacher");
                viewPagerAdapter.addFragment(new Management_Chat_Fragment(), "Management");
            }
        } catch (Exception ex) {

        }


        viewPager.setAdapter(viewPagerAdapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> fragmentTitles = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }

        public void addFragment(Fragment fragment, String name) {
            fragmentList.add(fragment);
            fragmentTitles.add(name);
        }
    }
}