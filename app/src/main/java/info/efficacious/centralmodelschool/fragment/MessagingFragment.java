package info.efficacious.centralmodelschool.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.activity.MessagingActivity;
import info.efficacious.centralmodelschool.activity.NewmessegesActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagingFragment extends Fragment {
    private  SectionsPagerAdapter mSectionsPagerAdapter;
    ImageView imageView;
    View view;

    public MessagingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_messaging, container, false);
        SectionsPagerAdapter  sectionsPagerAdapter = new  SectionsPagerAdapter(getFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs =view. findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        FragmentManager mfragment = getFragmentManager();
        FloatingActionButton fab = view.findViewById(R.id.fab);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        imageView=view.findViewById(R.id.image_view1);
        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(view -> {
            Toast.makeText(getActivity(), "Click on add", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getContext(), NewmessegesActivity.class);
            startActivity(intent);
        });
        mSectionsPagerAdapter = new  SectionsPagerAdapter(getFragmentManager());



        return view;
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */

    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static MessagingActivity.PlaceholderFragment newInstance(int sectionNumber) {
            MessagingActivity.PlaceholderFragment fragment = new MessagingActivity.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new SentFragment();
                    break;
                case 1:
                    fragment = new InprogressFragment();
                    break;
                case 2:
                    fragment = new DraftFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Sent";
                case 1:
                    return "In progress";
                case 2:
                    return "Draft";
            }
            return null;
        }
    }
}
