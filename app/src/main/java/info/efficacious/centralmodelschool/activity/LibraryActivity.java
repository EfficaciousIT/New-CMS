package info.efficacious.centralmodelschool.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.fragment.AssignBookFragment;
import info.efficacious.centralmodelschool.fragment.ReturnFragment;

public class LibraryActivity extends AppCompatActivity {
    private  SectionsPagerAdapter mSectionsPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        try {
//            SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//            ViewPager viewPager = findViewById(R.id.libs_view_pager);
//            viewPager.setAdapter(sectionsPagerAdapter);
//            TabLayout tabs = findViewById(R.id.libs_tabs);
//            tabs.setupWithViewPager(viewPager);
//            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        }catch (Exception er){
            Log.e("CMS","ERROR : LibraryActivity:"+er);
        }
    }
    public static class LibraryPlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public LibraryPlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static  LibraryPlaceholderFragment newInstance(int sectionNumber) {
            LibraryPlaceholderFragment fragment = new  LibraryPlaceholderFragment();
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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }



        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
//                        fragment=new BookListFragment();
//                    fragment = new SentFragment();
                    break;
                case 1:
                    fragment=new AssignBookFragment();
//                    fragment = new InprogressFragment();
                    break;
                case 2:
                    fragment=new ReturnFragment();
//                    fragment = new DraftFragment();
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
                    return "BOOK LIST";
                case 1:
                    return "ASSIGN BOOK";
                case 2:
                    return "RETURN";
            }
            return null;
        }
    }
}
