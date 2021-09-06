package info.efficacious.centralmodelschool.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.infideap.drawerbehavior.Advance3DDrawerLayout;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.Tab.AdminApproval_Tab;
import info.efficacious.centralmodelschool.Tab.Attendence_sliding_tab;
import info.efficacious.centralmodelschool.Tab.DailyDiary_Tab;
import info.efficacious.centralmodelschool.Tab.Event_Tab;
import info.efficacious.centralmodelschool.Tab.LibTab_layout;
import info.efficacious.centralmodelschool.Tab.StudentAttendanceActivity;
import info.efficacious.centralmodelschool.Tab.TimetableActivity_student;
import info.efficacious.centralmodelschool.Tab.TimetableActivity_teacher;
import info.efficacious.centralmodelschool.Tab.Timetable_sliding_tab;
import info.efficacious.centralmodelschool.common.ConnectionDetector;
import info.efficacious.centralmodelschool.dialogbox.BottomLayoutSheetDialog;
import info.efficacious.centralmodelschool.fragment.Admin_Dashboard;
import info.efficacious.centralmodelschool.fragment.Admission_Fragment;
import info.efficacious.centralmodelschool.fragment.All_Standard_Book;
import info.efficacious.centralmodelschool.fragment.DailyDiaryListFragment;
import info.efficacious.centralmodelschool.fragment.Event_list_fragment;
import info.efficacious.centralmodelschool.fragment.Gallery_fragment;
import info.efficacious.centralmodelschool.fragment.HolidayFragment;
import info.efficacious.centralmodelschool.fragment.LeaveListFragment;
import info.efficacious.centralmodelschool.fragment.MessageCenter;
import info.efficacious.centralmodelschool.fragment.MessagingFragment;
import info.efficacious.centralmodelschool.fragment.Noticeboard;
import info.efficacious.centralmodelschool.fragment.OnlineClassDetail;
import info.efficacious.centralmodelschool.fragment.OnlineClassTimetable;
import info.efficacious.centralmodelschool.fragment.OnlineTimeDetailStandard;
import info.efficacious.centralmodelschool.fragment.OnlineTimeTableStandard;
import info.efficacious.centralmodelschool.fragment.ParentDashboard;
import info.efficacious.centralmodelschool.fragment.Profile;
import info.efficacious.centralmodelschool.fragment.Profile_Fragment;
import info.efficacious.centralmodelschool.Tab.Chating_Sliding_Tab;
import info.efficacious.centralmodelschool.fragment.ResultFragment;
import info.efficacious.centralmodelschool.fragment.Sms_Fragment;
import info.efficacious.centralmodelschool.fragment.StandardWise_Book;
import info.efficacious.centralmodelschool.fragment.StudentChangePassword;
import info.efficacious.centralmodelschool.fragment.StudentExamFragment;
import info.efficacious.centralmodelschool.fragment.StudentSyllabusFragment;
import info.efficacious.centralmodelschool.fragment.Student_Std_Fragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomLayoutSheetDialog.BottomSheetListener {
    int k, FabmenuStatus = 0;
    String title = "";
    ConnectionDetector cd;
    ImageView chating_imgbtn;
    MenuItem result,admission;
    public static CircleImageView profile_img;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String role_id, name1, user_id, academic_id, school_id, stud_id, stand_id;
    public static FragmentManager fragmentManager;
    private Advance3DDrawerLayout drawer;
    NavigationView navigationView;
    private static final String TAG = "MainActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            settings = getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
            role_id = settings.getString("TAG_USERTYPEID", "");
            user_id = settings.getString("TAG_USERID", "");
            academic_id = settings.getString("TAG_ACADEMIC_ID", "");
            school_id = settings.getString("TAG_SCHOOL_ID", "");
            Log.e("TAG", "Role_id" + role_id);

        } catch (Exception ex) {

        }
        try {
            chating_imgbtn = (ImageView) findViewById(R.id.chating_imgbtn);
            fragmentManager = getSupportFragmentManager();
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            cd = new ConnectionDetector(getApplicationContext());
            profile_img = (CircleImageView) findViewById(R.id.profile_img);
            drawer = (Advance3DDrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            FragmentManager mfragment = getSupportFragmentManager();
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.setItemIconTintList(null);
            drawer.setViewScale(Gravity.START, 0.96f);
            drawer.setRadius(Gravity.START, 20);
            drawer.setViewElevation(Gravity.START, 8);
            drawer.setViewRotation(Gravity.START, 15);
        } catch (Exception Ex) {

        }

        try {
            Menu menu = navigationView.getMenu();
            result = menu.findItem(R.id.nav_Result);
            admission = menu.findItem(R.id.nav_admission);
            result.setVisible(false);
            admission.setVisible(false);

            MenuItem online_timetable = menu.findItem(R.id.nav_online_timetable);
            online_timetable.setVisible(true);
            MenuItem online_classes = menu.findItem(R.id.nav_online_classes);
            online_classes.setVisible(true);

            if (role_id.contentEquals("1") || role_id.contentEquals("2")) {
                // Student parent Login
                MenuItem message = menu.findItem(R.id.nav_message);
                message.setVisible(false);
                MenuItem target = menu.findItem(R.id.nav_dashboard);
                target.setVisible(false);
                MenuItem teacher = menu.findItem(R.id.nav_teacherAttendence);
                teacher.setVisible(false);
                MenuItem student = menu.findItem(R.id.nav_studentAttendence);
                student.setTitle("Self Attendence");
                MenuItem diary = menu.findItem(R.id.nav_Diary);
                diary.setVisible(false);
                MenuItem dashboard = menu.findItem(R.id.nav_dashboard);
                dashboard.setVisible(true);
                MenuItem aboutus = menu.findItem(R.id.nav_about_us);
                aboutus.setVisible(true);

                result.setVisible(true);
                admission.setVisible(true);



                if (!cd.isConnectingToInternet()) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setMessage("No Internet Connection");
                    alert.setPositiveButton("OK", null);
                    alert.show();
                } else {
                    try {
                        fragmentManager.beginTransaction().replace(R.id.content_main, new Profile()).commitAllowingStateLoss();
//                        fragmentManager.beginTransaction().replace(R.id.content_main, new ParentDashboard()).commitAllowingStateLoss();
                        getSupportActionBar().setTitle("Profile");
                    } catch (Exception ex) {

                    }

                }
            } else if (role_id.contentEquals("3")) {
                // Teacher Login
                MenuItem target = menu.findItem(R.id.nav_dashboard);
                target.setVisible(true);
                MenuItem message = menu.findItem(R.id.nav_message);
                message.setVisible(false);
                MenuItem student = menu.findItem(R.id.nav_teacherAttendence);
                student.setTitle("Self Attendence");
                MenuItem payment = menu.findItem(R.id.nav_payment);
                payment.setVisible(false);
                MenuItem aboutus = menu.findItem(R.id.nav_about_us);
                aboutus.setVisible(true);



                if (!cd.isConnectingToInternet()) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setMessage("No Internet Connection");
                    alert.setPositiveButton("OK", null);
                    alert.show();
                } else {
                    try {
                        fragmentManager.beginTransaction().replace(R.id.content_main, new Profile()).commitAllowingStateLoss();
                        getSupportActionBar().setTitle("Profile");
                    } catch (Exception ex) {

                    }

                }
            } else {
                // Manager Principal admin Login
                if (!cd.isConnectingToInternet()) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setMessage("No Internet Connection");
                    alert.setPositiveButton("OK", null);
                    alert.show();
                } else {
                    try {
                        if(role_id.isEmpty()) {
                            startActivity(new Intent(MainActivity.this,Login_activity.class));
                        }else {
                            MenuItem aboutus = menu.findItem(R.id.nav_about_us);
                            aboutus.setVisible(true);
                            MenuItem pay = menu.findItem(R.id.nav_payment);
                            pay.setVisible(false);
                            MenuItem abousUs = menu.findItem(R.id.nav_about_us);
                            abousUs.setVisible(true);
                            fragmentManager.beginTransaction().replace(R.id.content_main, new Admin_Dashboard()).commitAllowingStateLoss();
                            getSupportActionBar().setTitle("Dashboard");
                        }
                    } catch (Exception ex) {

                    }
                }
            }
        } catch (Exception ex) {

        }
        profile_img.setOnClickListener(v -> {
            try {
                if (!cd.isConnectingToInternet()) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setMessage("No Internet Connection");
                    alert.setPositiveButton("OK", null);
                    alert.show();
                } else {

//                    fragmentManager.beginTransaction().replace(R.id.content_main, new Profile_Fragment()).commitAllowingStateLoss();
                    fragmentManager.beginTransaction().replace(R.id.content_main, new Profile()).commitAllowingStateLoss();
                    getSupportActionBar().setTitle("Profile");

                }
            } catch (Exception ex) {


            }
        });
        chating_imgbtn.setOnClickListener(v -> {
            if (!cd.isConnectingToInternet()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setMessage("No Internet Connection");
                alert.setPositiveButton("OK", null);
                alert.show();
            } else {
                try {
                    getSupportActionBar().setTitle("Chat");
                    fragmentManager.beginTransaction().replace(R.id.content_main, new Chating_Sliding_Tab()).commitAllowingStateLoss();
                } catch (Exception ex) {

                }

            }
        });

    }

    /*    @Override
        public void onBackPressed() {
            k++;
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    k = 0;
                }
            }, 1000);
            if (k == 1) {
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure want to Exit?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }

        }*/
    Fragment getCurrentFragment() {
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.contain_main);
        return currentFragment;
    }

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    @Override
    public void onBackPressed() {
//        Toast.makeText(this, "onBackPressed", Toast.LENGTH_SHORT).show();
        Log.d("key", "getCurrentFragment" + getCurrentFragment());
        Log.e("TAG", "returnfragment" + String.valueOf(getVisibleFragment()));
        String fragname = String.valueOf(getVisibleFragment());
        if (fragname.contains("Parent_Dashboard")) {
            OnbackforDrawer();
        } else {
            if (role_id.equals("1") || role_id.equals("2"))
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new ParentDashboard()).commitAllowingStateLoss();
            else {
                OnbackforDrawer();
            }
        }

    }

    public void OnbackforDrawer() {
        if (fragmentManager.getBackStackEntryCount() > 0)
            fragmentManager.popBackStackImmediate();
        else {
            k++;
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    k = 0;
                }
            }, 1000);
            if (k == 1) {
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure want to Exit?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        }

    }


    DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                finish();
                break;
            case DialogInterface.BUTTON_NEGATIVE:

                break;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ChangePassword) {
            try {
                getSupportActionBar().setTitle("Change Password");
                fragmentManager.beginTransaction().replace(R.id.content_main, new StudentChangePassword()).commitAllowingStateLoss();
            } catch (Exception ex) {

            }
            return true;
        }
        if (id == R.id.action_Logout) {
            try {
//                SharedPreferences.Editor editor_delete = settings.edit();
//                editor_delete.clear().commit();
//                this.deleteDatabase("Notifications");
                callLogout();
            } catch (Exception ex) {

            }
            return true;
        }
//        if (id == R.id.action_about_school) {
//            try {
//
//                getSupportActionBar().setTitle("About Us");
//                Intent intent = new Intent(MainActivity.this, Aboutus.class);
//                startActivity(intent);
//
//            } catch (Exception ex) {
//
//            }
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void callLogout() {
        SharedPreferences.Editor editor_delete = settings.edit();
        editor_delete.clear().commit();
//                this.deleteDatabase("Notifications");
        Intent intent = new Intent(MainActivity.this, Login_activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager mfragment = getSupportFragmentManager();
        cd = new ConnectionDetector(getApplicationContext());
        drawer.closeDrawer(GravityCompat.START);
        try {
            if (!cd.isConnectingToInternet()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setMessage("No Internet Connection");
                alert.setPositiveButton("OK", null);
                alert.show();
            } else {
                //Admin Manager Principal menu option  Admin roleid=5,Principal roleid=6 ,Manager roleid=7
                if (role_id.equalsIgnoreCase("5") || role_id.equalsIgnoreCase("6") || role_id.equalsIgnoreCase("7")) {

                    if (id == R.id.nav_dashboard) {
                        try {
                            title = "Dashboard";
                            mfragment.beginTransaction().replace(R.id.content_main, new Admin_Dashboard()).commitNow();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_studentAttendence) {
                        try {
                            title = "Attendance";
                            Student_Std_Fragment student_std_activity = new Student_Std_Fragment();
                            Bundle args = new Bundle();
                            args.putString("pagename", "Std");
                            student_std_activity.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_about_us) {
                        getSupportActionBar().setTitle("About Us");
                        Intent intent = new Intent(MainActivity.this, Aboutus.class);
                        startActivity(intent);
                    } else if (id == R.id.nav_teacherAttendence) {
                        try {
                            title = "Attendance";
                            StudentAttendanceActivity studentAttendanceActivity = new StudentAttendanceActivity();
                            Bundle args = new Bundle();
                            args.putString("selected_layout", "Teacher_linearlayout");
                            studentAttendanceActivity.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, studentAttendanceActivity).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_syllabus) {
                        try {
                            title = "Syllabus";
                            Student_Std_Fragment student_std_activity = new Student_Std_Fragment();
                            Bundle args = new Bundle();
                            args.putString("pagename", "Syllabus");
                            student_std_activity.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();


                        } catch (Exception ex) {

                        }
                    } else if (id == R.id.nav_timetable) {
                        try {
                            title = "TimeTable";
                            mfragment.beginTransaction().replace(R.id.content_main, new Timetable_sliding_tab()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_examination) {
                        try {
                            title = "Examination";
                            Student_Std_Fragment student_std_activity = new Student_Std_Fragment();
                            Bundle args = new Bundle();
                            args.putString("pagename", "Exam");
                            student_std_activity.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();


                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_payment) {
                        try {
                          /*  title = "Fees Details";
                            mfragment.beginTransaction().replace(R.id.content_main, new AdminPaymentDetailsFragment()).commitAllowingStateLoss();
*/
                            new AlertDialog.Builder(this)
                                    .setMessage("Coming Soon...")
                                    .setNegativeButton("ok", null)
                                    .show();
                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_library) {
                        try {
                            title = "Library";
                            //startActivity(new Intent(MainActivity.this,LibraryActivity.class));
                            mfragment.beginTransaction().replace(R.id.content_main, new LibTab_layout()).commitAllowingStateLoss();
//                            mfragment.beginTransaction().replace(R.id.content_main, new All_Standard_Book()).commitAllowingStateLoss();

                        } catch (Exception ex) {
                             Log.e("eeee",""+ex);
                        }

                    } else if (id == R.id.nav_Diary) {
                        try {
                            title = "Daily Diary";
                            DailyDiaryListFragment dailyDiaryListFragment = new DailyDiaryListFragment();
                            Bundle args = new Bundle();
                            args.putString("value", "DailyDiary");
                            dailyDiaryListFragment.setArguments(args);
                            mfragment.beginTransaction().replace(R.id.content_main, dailyDiaryListFragment).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_Homework) {
                        try {
                            title = "Home Work";
                            DailyDiary_Tab dailyDiary_tab = new DailyDiary_Tab();
                            Bundle args = new Bundle();
                            args.putString("value", "HomeWork");
                            dailyDiary_tab.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, dailyDiary_tab).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_calender) {
                        try {
                            title = "Calendar";
                            mfragment.beginTransaction().replace(R.id.content_main, new HolidayFragment()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_leave) {
                        try {
                            title = "Leave";
                            mfragment.beginTransaction().replace(R.id.content_main, new AdminApproval_Tab()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_Event) {
                        try {
                            title = "Event";
                            mfragment.beginTransaction().replace(R.id.content_main, new Event_list_fragment()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_noticeboard) {
                        try {
                            title = "Noticeboard";
                            mfragment.beginTransaction().replace(R.id.content_main, new Noticeboard()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }
                    }else if (id == R.id.nav_admission) {
                        try {
                            new AlertDialog.Builder(this)
                                    .setMessage("Coming Soon...")
                                    .setNegativeButton("ok", null)
                                    .show();
                            title = "Admission";
                            mfragment.beginTransaction().replace(R.id.content_main, new Admission_Fragment()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                              Toast.makeText(this,""+ex,Toast.LENGTH_LONG).show();

                        }
                    }
                    else if (id == R.id.nav_message) {
                        try {
                            title = "Messaging";
//                            mfragment.beginTransaction().replace(R.id.content_main, new MessagingFragment()).commitAllowingStateLoss();
                            mfragment.beginTransaction().replace(R.id.content_main, new Sms_Fragment()).commitAllowingStateLoss();
                        } catch (Exception ex) {

                        }
                    } else if (id == R.id.nav_messageCenter) {
                        try {
                            title = "Notification";
                            mfragment.beginTransaction().replace(R.id.content_main, new MessageCenter()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }
                    } else if (id == R.id.nav_Gallery) {
                        try {
                            title = "Gallery";
                            mfragment.beginTransaction().replace(R.id.content_main, new Gallery_fragment()).commitAllowingStateLoss();
                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_about_us) {

                    } else if (id == R.id.nav_about_effi) {
                        try {
                            openWebPage("http://efficacious.co.in/Index.aspx");
                        } catch (Exception ex) {

                        }

                    }else if (id == R.id.nav_online_timetable) {
                        try {
//                            title = "Online Timetable";
//                            mfragment.beginTransaction().replace(R.id.content_main, new OnlineClassTimetable()).commitAllowingStateLoss();
                            title = "Online class Timetable";
                            OnlineTimeTableStandard studentAttendanceActivity = new OnlineTimeTableStandard();
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, studentAttendanceActivity).commitAllowingStateLoss();

                        } catch (Exception ex) {
                        }
                    }
                    else if (id == R.id.nav_online_classes) {
                        try {
                            title = "Online Class Detail";
                            //mfragment.beginTransaction().replace(R.id.content_main, new OnlineClassDetail()).commitAllowingStateLoss();
                            OnlineTimeDetailStandard studentAttendanceActivity = new OnlineTimeDetailStandard();
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, studentAttendanceActivity).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    }

                }
                /// roleId=3 Teacher Login
                else if (role_id.contentEquals("3")) {

                    if (id == R.id.nav_syllabus) {
                        try {
                            title = "Syllabus";
                            Student_Std_Fragment student_std_activity = new Student_Std_Fragment();
                            Bundle args = new Bundle();
                            args.putString("pagename", "Syllabus");
                            student_std_activity.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_dashboard) {
                        try {

                            title = "Dashboard";
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new ParentDashboard()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }
                    } else if (id == R.id.nav_about_us) {
                        getSupportActionBar().setTitle("About Us");
                        Intent intent = new Intent(MainActivity.this, Aboutus.class);
                        startActivity(intent);
                    } else if (id == R.id.nav_timetable) {
                        try {
                            title = "TimeTable";
                            mfragment.beginTransaction().replace(R.id.content_main, new TimetableActivity_teacher()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_examination) {
                        try {
                            title = "Examination";
                            Student_Std_Fragment student_std_activity = new Student_Std_Fragment();
                            Bundle args = new Bundle();
                            args.putString("pagename", "Exam");
                            student_std_activity.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_calender) {
                        try {
                            title = "Calendar";
                            mfragment.beginTransaction().replace(R.id.content_main, new HolidayFragment()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_leave) {
                        try {
                            title = "Leave";
                            mfragment.beginTransaction().replace(R.id.content_main, new LeaveListFragment()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_Event) {
                        try {
                            title = "Event";
                            mfragment.beginTransaction().replace(R.id.content_main, new Event_Tab()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_Gallery) {
                        try {
                            title = "Gallery";
                            mfragment.beginTransaction().replace(R.id.content_main, new Gallery_fragment()).commitAllowingStateLoss();
                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_Homework) {
                        try {
                            title = "Home Work";
                            DailyDiaryListFragment dailyDiaryListFragment = new DailyDiaryListFragment();
                            Bundle args = new Bundle();
                            args.putString("value", "HomeWork");
                            dailyDiaryListFragment.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, dailyDiaryListFragment).commitAllowingStateLoss();
                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_Diary) {
                        try {
                            title = "Daily Diary";
                            DailyDiaryListFragment dailyDiaryListFragment = new DailyDiaryListFragment();
                            Bundle args = new Bundle();
                            args.putString("value", "DailyDiary");
                            dailyDiaryListFragment.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, dailyDiaryListFragment).commitAllowingStateLoss();
                        } catch (Exception ex) {

                        }

                    }
//                    else if (id == R.id.nav_Result)
//                    {
//                        try
//                        {
//                            title = "Result";
//                            Student_Std_Fragment student_std_activity = new Student_Std_Fragment();
//                            Bundle args = new Bundle();
//                            args.putString("pagename", "Standarad_Result");
//                            student_std_activity.setArguments(args);
//                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();
//
//                        }catch (Exception ex)
//                        {
//
//                        }
//
//                    }
                    else if (id == R.id.nav_studentAttendence) {
                        try {
                            title = "Attendance";
                            Student_Std_Fragment student_std_activity = new Student_Std_Fragment();
                            Bundle args = new Bundle();
                            args.putString("pagename", "Std");
                            student_std_activity.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_teacherAttendence) {
                        try {
                            title = "Attendance";
                            Attendence_sliding_tab attendence_sliding_tab = new Attendence_sliding_tab();
                            Bundle args = new Bundle();
                            args.putString("attendence", "teacher_self_attendence");
                            args.putString("designation", "Teacher");
                            attendence_sliding_tab.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, attendence_sliding_tab).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_noticeboard) {
                        try {
                            title = "Noticeboard";
                            mfragment.beginTransaction().replace(R.id.content_main, new Noticeboard()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_messageCenter) {
                        try {
                            title = "Notification";
                            mfragment.beginTransaction().replace(R.id.content_main, new MessageCenter()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_library) {
                        try {
                            title = "Library";
                            mfragment.beginTransaction().replace(R.id.content_main, new LibTab_layout()).commitAllowingStateLoss();
//                            Student_Std_Fragment student_std_activity = new Student_Std_Fragment();
//                            Bundle args = new Bundle();
//                            args.putString("pagename", "LibraryTeacher");
//                            student_std_activity.setArguments(args);
//                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();
                            //startActivity(new Intent(MainActivity.this,LibraryActivity.class));
                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_about_effi) {
                        try {
                            openWebPage("http://efficacious.co.in/Index.aspx");
                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_online_timetable) {
                        try {
//                            title = "Online Timetable";
//                            mfragment.beginTransaction().replace(R.id.content_main, new OnlineClassTimetable()).commitAllowingStateLoss();
                            title = "Online class Timetable";
                            OnlineTimeTableStandard studentAttendanceActivity = new OnlineTimeTableStandard();
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, studentAttendanceActivity).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    }
                    else if (id == R.id.nav_online_classes) {
                        try {
                            title = "Online Class Detail";
                           // mfragment.beginTransaction().replace(R.id.content_main, new OnlineClassDetail()).commitAllowingStateLoss();
                            OnlineTimeDetailStandard studentAttendanceActivity = new OnlineTimeDetailStandard();
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, studentAttendanceActivity).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    }

                }
                //Student Parent Login
                else if (role_id.contentEquals("1")) {

                    if (id == R.id.nav_syllabus) {
                        try {
                            title = "Syllabus";
                            stand_id = settings.getString("TAG_STANDERDID", "");
                            StudentSyllabusFragment subjectFragment = new StudentSyllabusFragment();
                            Bundle args = new Bundle();
                            args.putString("std_id", stand_id);
                            subjectFragment.setArguments(args);
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, subjectFragment).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_about_us) {
                        getSupportActionBar().setTitle("About Us");
                        Intent intent = new Intent(MainActivity.this, Aboutus.class);
                        startActivity(intent);
                    } else if (id == R.id.nav_timetable) {
                        try {
                            title = "TimeTable";
                            stand_id = settings.getString("TAG_STANDERDID", "");
                            TimetableActivity_student timetableActivity_student = new TimetableActivity_student();
                            Bundle args = new Bundle();
                            args.putString("std_id", stand_id);
                            timetableActivity_student.setArguments(args);
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, timetableActivity_student).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_dashboard) {
                        try {

                            title = "Dashboard";
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new ParentDashboard()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }
                    } else if (id == R.id.nav_examination) {
                        try {
                            title = "Examination";
                            stand_id = settings.getString("TAG_STANDERDID", "");
                            StudentExamFragment studentExamActivity = new StudentExamFragment();
                            Bundle args = new Bundle();
                            args.putString("std_id", stand_id);
                            studentExamActivity.setArguments(args);
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, studentExamActivity).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_calender) {
                        try {
                            title = "Calendar";
                            mfragment.beginTransaction().replace(R.id.content_main, new HolidayFragment()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_leave) {
                        try {
                            title = "Leave";
                            mfragment.beginTransaction().replace(R.id.content_main, new LeaveListFragment()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }
                    } else if (id == R.id.nav_Event) {
                        try {
                            title = "Event";
                            mfragment.beginTransaction().replace(R.id.content_main, new Event_Tab()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_Gallery) {
                        try {
                            title = "Gallery";
                            mfragment.beginTransaction().replace(R.id.content_main, new Gallery_fragment()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_payment) {
                        try {
                            new AlertDialog.Builder(this)
                                    .setMessage("Coming Soon...")
                                    .setNegativeButton("ok", null)
                                    .show();
                        } catch (Exception ex) {
                            Toast.makeText(this,""+ex,Toast.LENGTH_LONG).show();

                        }

                    }else if (id == R.id.nav_admission) {
                        try {
                            title = "Re-Admission Form";
                            mfragment.beginTransaction().replace(R.id.content_main, new Admission_Fragment()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                            Toast.makeText(this,""+ex,Toast.LENGTH_LONG).show();

                        }

                    }

                    else if (id == R.id.nav_Homework) {
                        try {
                            title = "Home Work";
                            DailyDiaryListFragment dailyDiaryListFragment = new DailyDiaryListFragment();
                            Bundle args = new Bundle();
                            args.putString("value", "HomeWork");
                            dailyDiaryListFragment.setArguments(args);
                            mfragment.beginTransaction().replace(R.id.content_main, dailyDiaryListFragment).commitAllowingStateLoss();
                            // mfragment.beginTransaction().replace(R.id.content_main, new StudentHomeworkFragment()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_Result) {
                        try {

                            title = "Result";
                            mfragment.beginTransaction().replace(R.id.content_main, new ResultFragment()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    }
//                    else if (id == R.id.nav_Result)
//                    {
//                        try
//                        {
//                            title = "Result";
//                            mfragment.beginTransaction().replace(R.id.content_main, new StudentResultFragment()).commitAllowingStateLoss();
//
//                        }catch (Exception ex)
//                        {
//
//                        }
//
//                    }
                    else if (id == R.id.nav_studentAttendence) {
                        try {
                            title = "Attendance";
                            stud_id = settings.getString("TAG_STUDENTID", "");
                            Attendence_sliding_tab attendence_sliding_tab = new Attendence_sliding_tab();
                            Bundle args = new Bundle();
                            args.putString("Stud_name", name1);
                            args.putString("stud_id12", stud_id);
                            args.putString("attendence", "student_self_attendence");
                            attendence_sliding_tab.setArguments(args);
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, attendence_sliding_tab).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_teacherAttendence) {
                        try {
                            title = "Attendance";
                            StudentAttendanceActivity studentAttendanceActivity = new StudentAttendanceActivity();
                            Bundle args = new Bundle();
                            args.putString("selected_layout", "Teacher_linearlayout");
                            studentAttendanceActivity.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, studentAttendanceActivity).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_noticeboard) {
                        try {
                            title = "Noticeboard";
                            mfragment.beginTransaction().replace(R.id.content_main, new Noticeboard()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_messageCenter) {
                        try {
                            title = "Notification";
                            mfragment.beginTransaction().replace(R.id.content_main, new MessageCenter()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_library) {
                        try {
                            title = "Library";
                            mfragment.beginTransaction().replace(R.id.content_main, new LibTab_layout()).commitAllowingStateLoss();
                            //startActivity(new Intent(MainActivity.this,LibraryActivity.class));
//                            mfragment.beginTransaction().replace(R.id.content_main, new StandardWise_Book()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_about_effi) {
                        try {
                            openWebPage("http://efficacious.co.in/Index.aspx");
                        } catch (Exception ex) {

                        }

                    }else if (id == R.id.nav_online_timetable) {
                        try {
                            title = "Online class Timetable";
                            mfragment.beginTransaction().replace(R.id.content_main, new OnlineClassTimetable()).commitAllowingStateLoss();

                        } catch (Exception ex) {
                        }
                    }
                    else if (id == R.id.nav_online_classes) {
                        try {
                            title = "Online Class Detail";
                            mfragment.beginTransaction().replace(R.id.content_main, new OnlineClassDetail()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    }

                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                getSupportActionBar().setTitle(title);
            }
        } catch (Exception ex) {

        }


        return true;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onButtonClicked(String text) {
        Log.e(TAG, "onButtonClicked: "+text );
    }
}

