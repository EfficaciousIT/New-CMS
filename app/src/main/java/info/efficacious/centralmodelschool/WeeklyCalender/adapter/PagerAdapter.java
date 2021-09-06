package info.efficacious.centralmodelschool.WeeklyCalender.adapter;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import info.efficacious.centralmodelschool.WeeklyCalender.eventbus.BusProvider;
import info.efficacious.centralmodelschool.WeeklyCalender.eventbus.Event;
import info.efficacious.centralmodelschool.WeeklyCalender.fragment.WeekFragment;

import static info.efficacious.centralmodelschool.WeeklyCalender.fragment.WeekFragment.DATE_KEY;
import static info.efficacious.centralmodelschool.WeeklyCalender.view.WeekPager.NUM_OF_PAGES;
import static info.efficacious.centralmodelschool.WeeklyCalender.fragment.WeekFragment.DATE_KEY;
import static info.efficacious.centralmodelschool.WeeklyCalender.view.WeekPager.NUM_OF_PAGES;

/**
 * Created by nor on 12/4/2015.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "PagerAdapter";
    private int currentPage = NUM_OF_PAGES / 2;
    private DateTime date;

    public PagerAdapter(FragmentManager fm, DateTime date) {
        super(fm);
        this.date = date;
    }

    @Override
    public Fragment getItem(int position) {
        WeekFragment fragment = new WeekFragment();
        Bundle bundle = new Bundle();

        if (position < currentPage)
            bundle.putSerializable(DATE_KEY, getPerviousDate());
        else if (position > currentPage)
            bundle.putSerializable(DATE_KEY, getNextDate());
        else
            bundle.putSerializable(DATE_KEY, getTodaysDate());

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_OF_PAGES;
    }

    private DateTime getTodaysDate() {
        return date;
    }

    private DateTime getPerviousDate() {
        return date.plusDays(-7);
    }

    private DateTime getNextDate() {
        return date.plusDays(7);
    }

    @Override
    public int getItemPosition(Object object) {
        //Force rerendering so the week is drawn again when you return to the view after
        // back button press.
        return POSITION_NONE;
    }

    public void swipeBack() {
        date = date.plusDays(-7);
        currentPage--;
        currentPage = currentPage <= 1 ? NUM_OF_PAGES / 2 : currentPage;
        BusProvider.getInstance().post(
                new Event.OnWeekChange(date.withDayOfWeek(DateTimeConstants.MONDAY), false));
    }


    public void swipeForward() {
        date = date.plusDays(7);
        currentPage++;
        currentPage = currentPage >= NUM_OF_PAGES - 1 ? NUM_OF_PAGES / 2 : currentPage;
        BusProvider.getInstance().post(
                new Event.OnWeekChange(date.withDayOfWeek(DateTimeConstants.MONDAY), true));
    }

   /* public DateTime getDate() {
        return date;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }
*/

}
