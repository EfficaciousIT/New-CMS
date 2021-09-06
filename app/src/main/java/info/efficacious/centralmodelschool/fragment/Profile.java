package info.efficacious.centralmodelschool.fragment;


import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import info.efficacious.centralmodelschool.Interface.DataService;
import info.efficacious.centralmodelschool.MultiImages.activities.Single_image;
import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.activity.Login_activity;
import info.efficacious.centralmodelschool.activity.MainActivity;
import info.efficacious.centralmodelschool.common.ConnectionDetector;
import info.efficacious.centralmodelschool.database.Databasehelper;
import info.efficacious.centralmodelschool.entity.LoginDetail;
import info.efficacious.centralmodelschool.entity.ProfileDetail;
import info.efficacious.centralmodelschool.entity.ProfileDetailsPojo;
import info.efficacious.centralmodelschool.webApi.RetrofitInstance;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {
    private static final String PREFRENCES_NAME = "myprefrences";
    TextView name, StdName,
            fatherNametv, MotherNametv, addresstv, tv_number, txt_state,txt_annualtotalfees, outstanding_tv, annualToatal_tv,
            transport_total_fees_tv, transport_paidfess_tv, transport_osFees_tv,
            monthly_total_tv, monthly_paid_tv, monthly_os_tv, photo_os_tv, exam_os_tv, tution_os_tv, transport_os_tv,
            total_osfees_tv,profile_college,txt_student_no,txt_std_teacher;

    String Schooli_id, role_id, academic_id, UserId,teachername;
    private ProgressDialog progress, progress1;
    String refreshedToken, Teacher_statndard, Teacher_division;
    public String student_name;
    SwipeRefreshLayout swipeRefreshLayout;
    String command, User_id, stdid, divid;
    Button change_pass, logout;
    public static FragmentManager fragmentManager;
    CardView cardView, feedetails_lay;
    public String PaidtuitionFee, mnthcount, CurrentReceivableMonthfee, outstabding;
    String Annuloutstanding, Photooutstanding, Examoutstanding, Tuitionoutstanding, Transportoutstanding;
    View myview;
    ConnectionDetector cd;
    Databasehelper mydb;
    SharedPreferences settings;
    CircleImageView ProfileImage, image_edit;
    public Profile() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myview=  inflater.inflate(R.layout.fragment_profile, container, false);
        getValues();
        initiateComponent();
        callProfile();

        logout.setOnClickListener(view -> {
            SharedPreferences.Editor editor_delete = settings.edit();
            editor_delete.clear().commit();
            getActivity().deleteDatabase("Notifications");
            Intent intent = new Intent(getContext(), Login_activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();

        });
        ((MainActivity) getActivity()).setActionBarTitle("Profile");
        return myview;
    }

    private void initiateComponent() {
        tv_number = myview.findViewById(R.id.mobile_number_tv);
        name = myview.findViewById(R.id.name);
        StdName = myview.findViewById(R.id.profile_StdName);
        change_pass = myview.findViewById(R.id.reset_pass);
        change_pass.setOnClickListener(view -> MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, new StudentChangePassword()).commitAllowingStateLoss());
        logout = myview.findViewById(R.id.logout);
        cardView = myview.findViewById(R.id.education_cv);
        fatherNametv = myview.findViewById(R.id.profile_fatherName);
        MotherNametv = myview.findViewById(R.id.MotherName);
        addresstv = myview.findViewById(R.id.profile_city);
        ProfileImage = myview.findViewById(R.id.profile_image_view);
        image_edit = myview.findViewById(R.id.imageViewedit);
        txt_state = myview.findViewById(R.id.profile_state);

        txt_student_no = myview.findViewById(R.id.txt_student_no);
        profile_college = myview.findViewById(R.id.profile_college);
        txt_std_teacher = myview.findViewById(R.id.txt_std_teacher);


        image_edit.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Single_image.class);
            startActivity(intent);
        });
    }

    private void getValues() {
        cd = new ConnectionDetector(getActivity().getApplicationContext());
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        Schooli_id = settings.getString("TAG_SCHOOL_ID", "");
        role_id = settings.getString("TAG_USERTYPEID", "");
        academic_id = settings.getString("TAG_ACADEMIC_ID", "");
        UserId = settings.getString("TAG_USERID", "");
        stdid = settings.getString("TAG_STANDERDID", "");
        divid = settings.getString("TAG_DIVISIONID", "");

        if (role_id.contentEquals("3")) {
            Teacher_statndard = settings.getString("TAG_STANDERDID", "");
            Teacher_division = settings.getString("TAG_DIVISIONID", "");

        }
    }
    public void callProfile() {
        progress = new ProgressDialog(getActivity());
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.setMessage("loading...");
        progress.show();
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        ProfileAsync();
    }
    public void ProfileAsync() {
        try {
            String command;
            if (role_id.contentEquals("3")) {
                if (Teacher_statndard.contentEquals("0") || Teacher_division.contentEquals("0")||Teacher_statndard.contentEquals("")) {
                    command = "GetTeacherProfile";
                } else {
                    command = "GetTeacherProfilewithStandard";
                }
            } else if (role_id.contentEquals("1") || role_id.contentEquals("2")) {
                command = "GetStudentProfile";
            } else if (role_id.contentEquals("4")) {
                command = "GetStaffProfile";
            } else if (role_id.contentEquals("6")) {
                command = "GetPrincipalProfile";
            } else if (role_id.contentEquals("7")) {
                command = "GetManagerProfile";
            } else {
                command = "GetAdminProfile";
            }
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<ProfileDetailsPojo> call = service.getProfiledetails(command, Schooli_id, academic_id, UserId);
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ProfileDetailsPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }
                @Override
                public void onNext(ProfileDetailsPojo body) {
                    try {

                        generateProfileList((ArrayList<ProfileDetail>) body.getProfileDetails());
                    } catch (Exception ex) {
                        progress.dismiss();
//                        Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    progress.dismiss();
//                    Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onComplete() {
                    progress.dismiss();
                    sendTokenToServer(refreshedToken);
                }
            });
        } catch (Exception ex) {
            progress.dismiss();
        }
    }

    public void generateProfileList(ArrayList<ProfileDetail> taskListDataList) {


        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                Log.e("object", "taskListDataList" + taskListDataList.toString());
//                Toast.makeText(getContext(), "" + taskListDataList.get(0).getVchAddress() + "" + taskListDataList.get(0).getVchFatherNAme() + "" + taskListDataList.get(0).getVchMotherNAme() + "" + taskListDataList.get(0).getVchStandardname(), Toast.LENGTH_LONG).show();

                student_name = taskListDataList.get(0).getVchFirstName();
                String image_url = taskListDataList.get(0).getVchImageURL();
                settings.edit().putString("TAG_STUDENT_NAME", student_name).apply();
                settings.edit().putString("TAG_PROFILE_IMAGE_PRO", image_url).apply();

                Log.e("Image", "TAG_IMAGE" + image_url);
                name.setText(student_name);
                tv_number.setText(taskListDataList.get(0).getIntMobileNo());
//                MobileNotv.setText(taskListDataList.get(0).getIntMobileNo());
                addresstv.setText(taskListDataList.get(0).getVchAddress());
//                fatherNametv.setText(student_name);
                fatherNametv.setText(taskListDataList.get(0).getVchFatherNAme());
                MotherNametv.setText(taskListDataList.get(0).getVchMotherNAme());
//                name.setText(taskListDataList.get(0).getVchFirstName().toUpperCase().toString() + " " + taskListDataList.get(0).getLastName().toUpperCase().toString());


                if (role_id.contentEquals("1") || role_id.contentEquals("2")) {
                    StdName.setText(taskListDataList.get(0).getVchStandardname() + "/" + taskListDataList.get(0).getVchDivisionNAme());

                    fatherNametv.setText(taskListDataList.get(0).getVchFatherNAme());
                    MotherNametv.setText(taskListDataList.get(0).getVchMotherNAme());
                    tv_number.setText(taskListDataList.get(0).getIntMobileNo());
                    txt_state.setText(taskListDataList.get(0).getVchState());

                    StdName.setVisibility(View.VISIBLE);

                    fatherNametv.setVisibility(View.VISIBLE);
                    MotherNametv.setVisibility(View.VISIBLE);
                    txt_student_no.setVisibility(View.VISIBLE);
                    txt_std_teacher.setText("Roll No:");
                    txt_student_no.setText("Student ID: "+taskListDataList.get(0).getIntStudentID_Number().toString());
                    profile_college.setText(taskListDataList.get(0).getIntRollNO().toString());


                } else {
                    MotherNametv.setVisibility(View.GONE);
                    fatherNametv.setVisibility(View.GONE);
                    txt_std_teacher.setText("School/College:");
                    StdName.setVisibility(View.GONE);

                }

                String url = RetrofitInstance.Image_URL + image_url;

                Glide.with(getActivity())
                        .load(url) // image url
                        .error(R.mipmap.profile)
                        .into(ProfileImage);
                Log.e("pro_image", url);
            } else {
                Toast toast = Toast.makeText(getActivity(),
                        "No Data Available",
                        Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toastView.setBackgroundResource(R.drawable.no_data_available);
                toast.show();
            }

        } catch (Exception ex) {
            progress.dismiss();
            Log.e("Exception", "Profile Exception" + ex.toString());
        }
    }


    public void sendTokenToServer(final String strToken) {
        String email = settings.getString("TAG_USEREMAILID", "");
        if (role_id.contentEquals("1") || role_id.contentEquals("2")) {
            User_id = settings.getString("TAG_STUDENTID", "");
        } else {
            User_id = settings.getString("TAG_USERID", "");
        }
        switch (Integer.parseInt(role_id)) {
            case 1:
                command = "FcmTokenStudent";
                break;
            case 2:
                command = "FcmTokenStudent";
                break;
            case 3:
                command = "FcmTokenTeacher";
                break;
            case 4:
                command = "FcmTokenStaff";
                break;
            case 5:
                command = "FcmTokenAdmin";
                break;
            case 6:
                command = "FcmTokenPrincipal";
                break;
            case 7:
                command = "FcmTokenManager";
                break;

        }

        settings.edit().putString("TAG_USERFIREBASETOKEN", strToken).apply();
        try {
            FCMTOKENASYNC(command, strToken, email);
        } catch (Exception ex) {
        }
    }

    public void FCMTOKENASYNC(String command, String Firebasetoken, String EMail) {
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            LoginDetail loginDetail = new LoginDetail(Firebasetoken, EMail, Integer.parseInt(User_id), Integer.parseInt(Schooli_id), Integer.parseInt(academic_id));
            Observable<ResponseBody> call = service.FCMTokenUpdate(command, loginDetail);
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
                @Override
                public void onSubscribe(Disposable disposable) {

                }

                @Override
                public void onNext(ResponseBody body) {
                    try {

                    } catch (Exception ex) {


                    }
                }

                @Override
                public void onError(Throwable t) {
                }

                @Override
                public void onComplete() {
                }
            });
        } catch (Exception ex) {

        }
    }
}
