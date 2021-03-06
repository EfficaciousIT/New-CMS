package info.efficacious.centralmodelschool.fragment;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import info.efficacious.centralmodelschool.Interface.DataService;
import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.adapters.NoticeBoardAdapter;
import info.efficacious.centralmodelschool.adapters.StudentListAdapter;
import info.efficacious.centralmodelschool.common.ConnectionDetector;
import info.efficacious.centralmodelschool.entity.DashboardDetail;
import info.efficacious.centralmodelschool.entity.DashboardDetailsPojo;
import info.efficacious.centralmodelschool.entity.Holiday;

import info.efficacious.centralmodelschool.webApi.RetrofitInstance;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class Admin_Dashboard extends Fragment {
    View myview;
    TextView TextView_student, primaryTxt, secondaryTxt;
    TextView TextView_staff;
    TextView TextView_teacher;
    TextView TextView_pendingfee;
    TextView TextView_receivedfee;
    TextView TextView_totalfee;
    String Totalfee;
    int dayscount;
    String Receivedfee;
    String Pendingfee, Schooli_id;
    RecyclerView studentlist;
    StudentListAdapter studadapter;
    String newDate, fesival;
    ArrayList<Holiday> holiday1 = new ArrayList<Holiday>();
    Context mContext;
    RecyclerView noticeboard;
    RecyclerView.Adapter madapter;
    String Admin_id, academic_id;
    FrameLayout calenderview;
    Date holidayDay;
    ArrayList<String> dates = new ArrayList<String>();
    CaldroidFragment mCaldroidFragment = new CaldroidFragment();
    String a, role_id;
    String status;
    ConnectionDetector cd;
    String date_selected;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    private CompositeDisposable mCompositeDisposable;
    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.activity_dashboard, null);
        cd = new ConnectionDetector(getActivity().getApplicationContext());
        mContext = getActivity();
        primaryTxt = myview.findViewById(R.id.primary_layout);
        secondaryTxt = myview.findViewById(R.id.secondary_layout);
        TextView_student = (TextView) myview.findViewById(R.id.tv_Student);
        TextView_staff = (TextView) myview.findViewById(R.id.tv_Staff);
        TextView_teacher = (TextView) myview.findViewById(R.id.tv_Teacher);
        TextView_pendingfee = (TextView) myview.findViewById(R.id.tv_pendingfee);
        TextView_receivedfee = (TextView) myview.findViewById(R.id.tv_receivedfee);
        TextView_totalfee = (TextView) myview.findViewById(R.id.tv_totalfee);
        noticeboard = (RecyclerView) myview.findViewById(R.id.dashnoticeboard_list);
        noticeboard.setNestedScrollingEnabled(false);
        studentlist = (RecyclerView) myview.findViewById(R.id.dashstud_list);
        studentlist.setNestedScrollingEnabled(false);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        Schooli_id = settings.getString("TAG_SCHOOL_ID", "");
        Admin_id = settings.getString("TAG_USERID", "");
        role_id = settings.getString("TAG_USERTYPEID", "");
        academic_id = settings.getString("TAG_ACADEMIC_ID", "");

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();

        Bundle args = new Bundle();
        args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.SUNDAY);
        calenderview = (FrameLayout) myview.findViewById(R.id.cal_container);
        mCaldroidFragment.setArguments(args);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        if (!cd.isConnectingToInternet()) {

            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setMessage("No Internet Connection");
            alert.setPositiveButton("OK", null);
            alert.show();

        } else {
            try {
                HolidayAsync();
                studentAsync();
                teacherAsync();
                staffAsync();
                StudentListAsync();
                NoticeboardAsync();
            } catch (Exception ex) {

            }

        }

        mCaldroidFragment.setCaldroidListener(new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                try {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.get(Calendar.YEAR);

                    int day1 = date.getDate();
                    int month1 = ((date.getMonth()) + 1);
                    NumberFormat f = new DecimalFormat("00");
                    date_selected = ((f.format(day1)) + "/" + (f.format(month1)) + "/" + String.valueOf(calendar.get(Calendar.YEAR)));
//                 date_selected= String.valueOf(day1)+("/")+ String.valueOf(month1)+("/")+ String.valueOf(calendar.get(Calendar.YEAR));

                    boolean status = dates.contains(date_selected);
                    if (status == true) {
                        // festivalnmae(date_selected);
                        try {
                            String date_selected1 = date_selected;
                            int sizee = holiday1.size();
                            for (int i = 0; i < holiday1.size(); i++) {
                                String holidaydate = holiday1.get(i).getFromDate();

                                if (date_selected1.contentEquals(holidaydate)) {
                                    String holidayname = holiday1.get(i).getHoliday_name();
                                    Toast.makeText(getActivity(), holidayname, Toast.LENGTH_SHORT).show();
                                    break;
                                }


                            }
                        } catch (Exception ex) {
                            ex.getMessage();
                        }
                    } else {
                        Toast.makeText(getActivity(), " " + date_selected, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {

                }

            }
        });
        return myview;
    }

    public void studentAsync() {
        try {

            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            mCompositeDisposable.add(service.getDashboardDetails("StudentCountByPrincipal", academic_id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(getObserverStudentCount()));
        } catch (Exception ex) {
        }
    }

    public DisposableObserver<DashboardDetailsPojo> getObserverStudentCount() {
        return new DisposableObserver<DashboardDetailsPojo>() {

            @Override
            public void onNext(@NonNull DashboardDetailsPojo dashboardDetailsPojo) {
                try {
                    generateStudentCount((ArrayList<DashboardDetail>) dashboardDetailsPojo.getDashboardDetails());
                } catch (Exception ex) {

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    public void generateStudentCount(ArrayList<DashboardDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                for (int i = 0; i <= taskListDataList.size(); i++) {
                    if (taskListDataList.get(i).getIntschoolId() == 1) {
                        try {
                            TextView_totalfee.setText("Student: " + taskListDataList.get(i).getPresent() + "/" + taskListDataList.get(i).getCount());
                        } catch (Exception ex) {
                            TextView_totalfee.setText("Student: 0/0");
                        }

                    } else {
                        try {
                            TextView_student.setText("Student: " + taskListDataList.get(i).getPresent() + "/" + taskListDataList.get(i).getCount());
                        } catch (Exception ex) {
                            TextView_student.setText("Student: 0/0");
                        }
                    }
                }
            } else {
                TextView_totalfee.setText("Student: 0/0");
                TextView_student.setText("Student: 0/0");
            }

        } catch (Exception ex) {
            Log.e(TAG, "generateStudentCount: Failed");
//            Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
        }
    }

    public void staffAsync() {

        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            mCompositeDisposable.add(service.getDashboardDetails("StaffCountByPrincipal", academic_id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(getObserverStaffCount()));
        } catch (Exception ex) {
        }
    }

    public DisposableObserver<DashboardDetailsPojo> getObserverStaffCount() {
        return new DisposableObserver<DashboardDetailsPojo>() {

            @Override
            public void onNext(@NonNull DashboardDetailsPojo dashboardDetailsPojo) {
                try {
                    generateStaffCount((ArrayList<DashboardDetail>) dashboardDetailsPojo.getDashboardDetails());
                } catch (Exception ex) {

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    public void generateStaffCount(ArrayList<DashboardDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                for (int i = 0; i <= taskListDataList.size(); i++) {
                    if (taskListDataList.get(i).getIntSchool_id() == 1) {
                        try {
                            TextView_pendingfee.setText("Staff: " + taskListDataList.get(i).getPresent() + "/" + taskListDataList.get(i).getCount());
                        } catch (Exception ex) {
                            TextView_pendingfee.setText("Staff: 0/0 ");
                        }
                    } else {
                        try {
                            TextView_staff.setText("Staff: " + taskListDataList.get(i).getPresent() + "/" + taskListDataList.get(i).getCount());
                        } catch (Exception ex) {
                            TextView_staff.setText("Staff: 0/0 ");
                        }
                    }
                }
            } else {
                TextView_pendingfee.setText("Staff: 0/0 ");
                TextView_staff.setText("Staff: 0/0 ");
            }

        } catch (Exception ex) {
            Log.e(TAG, "generateStaffCount: Failed");
//            Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
        }

    }

    public void teacherAsync() {
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            mCompositeDisposable.add(service.getDashboardDetails("TeacherCountBYPrincipal", academic_id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(getObserverTeacherCount()));
        } catch (Exception ex) {
        }
    }

    public DisposableObserver<DashboardDetailsPojo> getObserverTeacherCount() {
        return new DisposableObserver<DashboardDetailsPojo>() {

            @Override
            public void onNext(@NonNull DashboardDetailsPojo dashboardDetailsPojo) {
                try {
                    generateTeacherCount((ArrayList<DashboardDetail>) dashboardDetailsPojo.getDashboardDetails());
                } catch (Exception ex) {

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    public void generateTeacherCount(ArrayList<DashboardDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                Log.d("TAG","generateTeacherCount"+taskListDataList.toString());
                for (int i = 0; i <= taskListDataList.size(); i++) {
                    if (taskListDataList.get(i).getIntSchool_id() == 1) {
                        try {
                            TextView_receivedfee.setText("Teacher: " + taskListDataList.get(i).getPresent() + "/" + taskListDataList.get(i).getCount());
                            progressDialog.dismiss();
                        } catch (Exception ex) {
                            TextView_receivedfee.setText("Teacher: 0/0");
                        }
                    } else {
                        try {
                            TextView_teacher.setText("Teacher: " + taskListDataList.get(i).getPresent() + "/" + taskListDataList.get(i).getCount());
                            progressDialog.dismiss();
                        } catch (Exception ex) {
                            TextView_teacher.setText("Teacher: 0/0");
                        }
                    }
                }
            } else {
                TextView_receivedfee.setText("Teacher: 0/0");
                TextView_teacher.setText("Teacher: 0/0");
            }

        } catch (Exception ex) {
            Log.e(TAG, "generateTeacherCount: Failed" );
//            Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
        }
    }

    public void StudentListAsync() {

        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            mCompositeDisposable.add(service.getDashboardDetails("genderwiseStudentBYPrincipal", academic_id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(getObserver()));
        } catch (Exception ex) {
        }
    }

    public DisposableObserver<DashboardDetailsPojo> getObserver() {
        return new DisposableObserver<DashboardDetailsPojo>() {

            @Override
            public void onNext(@NonNull DashboardDetailsPojo dashboardDetailsPojo) {
                try {
                    generateStudentList((ArrayList<DashboardDetail>) dashboardDetailsPojo.getDashboardDetails());
                } catch (Exception ex) {

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }


    public void generateStudentList(ArrayList<DashboardDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                studadapter = new StudentListAdapter(taskListDataList, getActivity());

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

                studentlist.setLayoutManager(layoutManager);

                studentlist.setAdapter(studadapter);
            } else {
                Toast toast = Toast.makeText(getActivity(),
                        "No Data Available",
                        Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toastView.setBackgroundResource(R.drawable.no_data_available);
                toast.show();
                //   Toast.makeText(getActivity(), "No Data Available", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }


    public void NoticeboardAsync() {
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            if (role_id.contentEquals("6") || role_id.contentEquals("7")) {
                mCompositeDisposable.add(service.getDashboardDetails("NoticeBoardPrincipal", academic_id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(getObserverNotice()));
            } else {
                mCompositeDisposable.add(service.getDashboardDetails("NoticeBoard", academic_id, Schooli_id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(getObserverNotice()));
            }

        } catch (Exception ex) {
        }
    }

    public DisposableObserver<DashboardDetailsPojo> getObserverNotice() {
        return new DisposableObserver<DashboardDetailsPojo>() {

            @Override
            public void onNext(@NonNull DashboardDetailsPojo dashboardDetailsPojo) {
                try {
                    generateNoticeboardList((ArrayList<DashboardDetail>) dashboardDetailsPojo.getDashboardDetails());
                } catch (Exception ex) {

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
            }
        };
    }

    public void generateNoticeboardList(ArrayList<DashboardDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                noticeboard.setHasFixedSize(true);
                noticeboard.setLayoutManager(new LinearLayoutManager(getActivity()));
                madapter = new NoticeBoardAdapter(taskListDataList,getContext());
                noticeboard.setAdapter(madapter);
            } else {
                Toast toast = Toast.makeText(getActivity(),
                        "No Data Available",
                        Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toastView.setBackgroundResource(R.drawable.no_data_available);
                toast.show();
                //  Toast.makeText(getActivity(), "No Data Available", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }

    public void HolidayAsync() {
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<DashboardDetailsPojo> call = service.getDashboardDetails("HolidaysAndVacation", academic_id);
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<DashboardDetailsPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {

                }

                @Override
                public void onNext(DashboardDetailsPojo body) {
                    try {
                        generateHolidayList((ArrayList<DashboardDetail>) body.getDashboardDetails());
                    } catch (Exception ex) {

                        Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {

                    Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onComplete() {

                }
            });
        } catch (Exception ex) {

        }
    }


    public void generateHolidayList(ArrayList<DashboardDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                holiday1.clear();
                for (int j = 0; j < taskListDataList.size(); j++) {
                    Holiday hol = new Holiday();
                    a = taskListDataList.get(j).getDtFromDate();
                    dayscount = taskListDataList.get(j).getIntNoOfDay();
                    fesival = taskListDataList.get(j).getHoliday();
                    hol.setFromDate(a);
                    hol.setHoliday_name(fesival);
                    for (int i = 0; i < dayscount - 1; i++) {
                        Holiday vac1 = new Holiday();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar c = Calendar.getInstance();
                        try {
                            //Setting the date to the given date
                            if (i == 0) {
                                c.setTime(sdf.parse(a));
                            } else {
                                c.setTime(sdf.parse(newDate));
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        //Number of Days to add
                        c.add(Calendar.DAY_OF_MONTH, 1);
                        //Date after adding the days to the given date
                        newDate = sdf.format(c.getTime());
                        vac1.setFromDate(newDate);
                        vac1.setHoliday_name(fesival);
                        holiday1.add(vac1);
                        dates.add(newDate);

                    }
                    dates.add(a);
                    holiday1.add(hol);
                }
                holiday_list();
                try {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cal_container, mCaldroidFragment).commit();
                    status = "";
                } catch (Exception ex) {

                }
            } else {
                Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
        }

    }

    public void holiday_list() {
        int day = 0;

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        for (int i = 0; i < dates.size(); i++) {
            String inputString2 = dates.get(i);
            String inputString1 = myFormat.format(date);

            try {
                //Converting String format to date format
                Date date1 = null;
                try {
                    date1 = myFormat.parse(inputString1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date date2 = myFormat.parse(inputString2);
                //Calculating number of days from two dates
                long diff = date2.getTime() - date1.getTime();
                long datee = diff / (1000 * 60 * 60 * 24);
                //Converting long type to int type
                day = (int) datee;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cal = Calendar.getInstance();
            cal.add(Calendar.DATE, day);
            holidayDay = cal.getTime();
            ColorDrawable bgToday = new ColorDrawable(Color.RED);
            mCaldroidFragment.setBackgroundDrawableForDate(bgToday, holidayDay);
        }
    }

}