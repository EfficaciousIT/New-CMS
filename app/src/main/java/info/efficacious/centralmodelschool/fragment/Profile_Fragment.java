package info.efficacious.centralmodelschool.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import info.efficacious.centralmodelschool.Interface.DataService;
import info.efficacious.centralmodelschool.MultiImages.activities.Single_image;
import info.efficacious.centralmodelschool.R;

import info.efficacious.centralmodelschool.common.ConnectionDetector;
import info.efficacious.centralmodelschool.database.Databasehelper;
import info.efficacious.centralmodelschool.entity.ProfileDetail;
import info.efficacious.centralmodelschool.entity.ProfileDetailsPojo;
import info.efficacious.centralmodelschool.webApi.RetrofitInstance;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;



public class Profile_Fragment extends Fragment {
    View myview;
    Databasehelper mydb;
    ConnectionDetector cd;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    CircleImageView ProfileImage,image_edit;
    TextView name,StdName,IdNo,MobileNotv,fathertv,Motherstv,fatherNametv,MotherNametv,addresstv,profile_college,txt_student_no;
    String Schooli_id,role_id,academic_id,UserId;
    private ProgressDialog progress;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.activity_profile, null);
        cd = new ConnectionDetector(getActivity().getApplicationContext());
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        Schooli_id= settings.getString("TAG_SCHOOL_ID", "");
        role_id = settings.getString("TAG_USERTYPEID", "");
        academic_id = settings.getString("TAG_ACADEMIC_ID", "");
        UserId = settings.getString("TAG_USERID", "");
        name=(TextView)myview.findViewById(R.id.name);
        StdName=(TextView)myview.findViewById(R.id.StdName);
        IdNo=(TextView)myview.findViewById(R.id.IdNo);
        MobileNotv=(TextView)myview.findViewById(R.id.MobileNotv);
        fathertv=(TextView)myview.findViewById(R.id.father);
        Motherstv=(TextView)myview.findViewById(R.id.Mothers);
        fatherNametv=(TextView)myview.findViewById(R.id.fatherName);
        MotherNametv=(TextView)myview.findViewById(R.id.MotherName);
        addresstv=(TextView)myview.findViewById(R.id.addresstv);
        ProfileImage=(CircleImageView)myview.findViewById(R.id.imageView1);
        image_edit=(CircleImageView)myview.findViewById(R.id.imageViewedit);
        txt_student_no = myview.findViewById(R.id.txt_student_no);
        profile_college = myview.findViewById(R.id.profile_college);
        image_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Single_image.class);
                startActivity(intent);
            }
        });
        progress = new ProgressDialog(getActivity());
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.setMessage("loading...");
        progress.show();
        ProfileAsync();
        return myview;
    }
    public void  ProfileAsync (){
        try {
            String command;
            if (role_id.contentEquals("3")) {
                command = "GetTeacherProfile";

            } else if (role_id.contentEquals("1") || role_id.contentEquals("2")) {
                command = "GetStudentProfile";
            } else if (role_id.contentEquals("4")) {
                command = "InsertSTaffProfile";
            } else if (role_id.contentEquals("6")) {
                command = "GetPrincipalProfile";
            } else if (role_id.contentEquals("7")) {
                command = "GetManagerProfile";
            } else {
                command = "GetAdminProfile";
            }
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<ProfileDetailsPojo> call = service.getProfiledetails(command,Schooli_id, academic_id, UserId);
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
                        Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    progress.dismiss();
                    Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onComplete() {
                    progress.dismiss();
                }
            });
        } catch (Exception ex) {
            progress.dismiss();
        }
    }

    public void generateProfileList(ArrayList<ProfileDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                name.setText(taskListDataList.get(0).getVchFirstName().toUpperCase().toString()+" "+taskListDataList.get(0).getLastName().toUpperCase().toString());
                settings.edit().putString("TAG_NAME", taskListDataList.get(0).getVchFirstName().toUpperCase().toString()+" "+taskListDataList.get(0).getLastName().toUpperCase().toString()).commit();
                MobileNotv.setText(taskListDataList.get(0).getIntMobileNo().toString());
                addresstv.setText(taskListDataList.get(0).getVchAddress().toString());
                if (role_id.contentEquals("1") || role_id.contentEquals("2")) {
                   StdName.setText(taskListDataList.get(0).getVchStandardname().toString());
                    IdNo.setText(taskListDataList.get(0).getIntRollNO().toString());
                    fatherNametv.setText(taskListDataList.get(0).getVchFatherNAme().toString());
                    MotherNametv.setText(taskListDataList.get(0).getVchMotherNAme().toString());
                    fathertv.setVisibility(View.VISIBLE);
                    IdNo.setVisibility(View.VISIBLE);
                    StdName.setVisibility(View.VISIBLE);
                    Motherstv.setVisibility(View.VISIBLE);
                    fatherNametv.setVisibility(View.VISIBLE);
                    MotherNametv.setVisibility(View.VISIBLE);
                    txt_student_no.setVisibility(View.VISIBLE);
                    txt_student_no.setText(taskListDataList.get(0).getIntStudentID_Number().toString());
                    profile_college.setText(taskListDataList.get(0).getIntRollNO().toString());
                }else
                {
                    MotherNametv.setVisibility(View.GONE);
                    fatherNametv.setVisibility(View.GONE);
                    Motherstv.setVisibility(View.GONE);
                    fathertv.setVisibility(View.GONE);
                    IdNo.setVisibility(View.GONE);
                    StdName.setVisibility(View.GONE);
                }
                String url = RetrofitInstance.Image_URL + taskListDataList.get(0).getVchImageURL().toString();
                Glide.with(getActivity())
                        .load(url) // image url
                        .error(R.mipmap.profile)
                        .into(ProfileImage);
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
            Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }

}
