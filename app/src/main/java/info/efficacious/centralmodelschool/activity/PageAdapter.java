package info.efficacious.centralmodelschool.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import info.efficacious.centralmodelschool.fragment.DraftFragment;
import info.efficacious.centralmodelschool.fragment.InprogressFragment;
import info.efficacious.centralmodelschool.fragment.SentFragment;

public class PageAdapter extends FragmentPagerAdapter {
    int mNoTabs;

    public PageAdapter(FragmentManager fm, int mNoTabs) {
        super(fm);
        this.mNoTabs = mNoTabs;
    }

    @Override
    public Fragment getItem(int position) {
      switch (position)
      {
          case 0:
              SentFragment sentFragment=new SentFragment();
              return sentFragment;
          case 1:
              InprogressFragment inprogressFragmen=new InprogressFragment();
              return inprogressFragmen;
          case 2:
              DraftFragment draftFragment=new DraftFragment();
              return draftFragment;
              default:
                  return null;


      }


    }

    @Override
    public int getCount() {
        return mNoTabs;
    }
}
