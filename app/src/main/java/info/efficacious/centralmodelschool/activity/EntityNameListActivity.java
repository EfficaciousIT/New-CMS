package info.efficacious.centralmodelschool.activity;

import androidx.annotation.NonNull;

import android.app.FragmentManager;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.efficacious.centralmodelschool.Interface.DataService;
import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.adapters.CustomAdapter;
import info.efficacious.centralmodelschool.adapters.StandardGridAdapter;
import info.efficacious.centralmodelschool.entity.DashboardDetail;
import info.efficacious.centralmodelschool.entity.DashboardDetailsPojo;
import info.efficacious.centralmodelschool.entity.StudentStandardwiseDetail;
import info.efficacious.centralmodelschool.entity.StudentStandardwiseDetailPojo;
import info.efficacious.centralmodelschool.entity.TeacherDetail;
import info.efficacious.centralmodelschool.entity.TeacherDetailPojo;
import info.efficacious.centralmodelschool.webApi.RetrofitInstance;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
//http://122.170.4.112/CMSWebApi/api/Message/?command=ListOfAllStudents&intStandard_id=0&intSchool_id=1&intAcademic_id=4 (All Standard)
//All Standard
//
//http://122.170.4.112/CMSWebApi/api/Message/?command=ListOfStudentsStandardWise&intStandard_id=6&intSchool_id=1&intAcademic_id=4


//
//>All Division
//        >
//        >http://122.170.4.112/CMSWebApi/api/Message/?command=ListOfStudentsStandardWise&intStandard_id=6&intSchool_id=1&intAcademic_id=4


public class EntityNameListActivity extends ListActivity implements StandardGridAdapter.OnStandardListener {

    ArrayList<String> sms_phone_no_array = new ArrayList<String>();
    String academic_id,Schooli_id;
    SharedPreferences settings;
    private static final String PREFRENCES_NAME = "myprefrences";
    List<TeacherDetail> teacherDetails=new ArrayList<>();
    List<DashboardDetail> staffDetail=new ArrayList<>();

    List<StudentStandardwiseDetail> studentDetail=new ArrayList<>();

    private static final String TAG = "EntityNameList";
    private CompositeDisposable mCompositeDisposable;
    ListView listView;
    Button btn_cancel,btn_done;
    ArrayList<TeacherDetail> mTeacherDetails=new ArrayList<>();
    int preSelectedIndex = -1;
    String args,mdivId,standard,arguments,studentstandard ;
    int division;
    SendMessage SM;
    public ArrayList<String> mobile=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_name_list);
        settings = this.getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        academic_id = settings.getString("TAG_ACADEMIC_ID", "");
        Schooli_id = settings.getString("TAG_SCHOOL_ID", "");

        btn_cancel=findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_done=findViewById(R.id.btn_doneselectedTeacher);
        FragmentManager manager=getFragmentManager();
        Bundle newmessagebundle=getIntent().getExtras();
        args=newmessagebundle.getString("args");
        arguments=newmessagebundle.getString ("std");
        division=newmessagebundle.getInt ("div");

        if (args!=null)
        {
            if ( args.equals("fromStaff"))
            {
                usertype_staff("AllStaffByPrincipal");
            }else if (args.equals("fromTeacher")){
                usertype_selected("select");
            }
        }
        if (division!=0&&arguments!=null)
        {
            mdivId= String.valueOf(division);
            standard=arguments;
            callStudent(standard,mdivId);
            Log.d(TAG, "onCreate_callstudent: "+standard+"__"+mdivId);
        }

//        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("TeacherName");
        btn_done.setOnClickListener(view -> {

            Bundle bundle=new Bundle();
            bundle.putStringArrayList("mobile",mobile);
            bundle.putString("key","fromTeacherNameList");

//                SM.sendData(mobile);
            Intent intent1 =new Intent(EntityNameListActivity.this,NewmessegesActivity.class);
            intent1.putExtra("key_teachermobile",mobile);
            startActivity(intent1,bundle);
            Log.e(TAG, "onClick: "+mobile.toString() );
            getFragmentManager().popBackStack();
        });
    }


    @Override
    public void onStandardClickdata(String data) {

    }

    @Override
    public void onStandardClick(int position) {

    }
    interface SendMessage {
        void sendData(ArrayList<String> message);
    }
    public void usertype_selected(String allSMSAdmin) {

        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            if (allSMSAdmin.equals("select")){
                Log.e(TAG, "usertype_selected: "+Schooli_id );
                try {
                    mCompositeDisposable.add(service.getTeacherDetails(allSMSAdmin,Schooli_id)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribeWith(getObserver_teacher()));
                } catch (Exception ex) {
                }
            }else if (allSMSAdmin.equals("AllStaff")){
                try {
                    usertype_staff(allSMSAdmin);
                } catch (Exception ex) {
                }
            }

        } catch (Exception ex) {

        }
    }

    public void usertype_staff(String allSMSAdmin) {
        try {
            String command = allSMSAdmin;
            Log.e(TAG, "usertype_staff: "+command+"_"+academic_id+"_"+Schooli_id );
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);

            Observable<DashboardDetailsPojo> call = service.getDashboardDetails(command, academic_id, Schooli_id);

            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<DashboardDetailsPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {
//                    progress.show();
                }

                @Override
                public void onNext(DashboardDetailsPojo body) {
                    try {
                        Log.e(TAG, "onNext: "+body.getDashboardDetails() );
                        generateSMS((ArrayList<DashboardDetail>) body.getDashboardDetails());
                    } catch (Exception ex) {
//                        progress.dismiss();
                        Log.e(TAG, "onNext: "+ex.toString() );
                        Toast.makeText(EntityNameListActivity.this, "Response Taking Time,Seems Network issue in staff ex!"+ex.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
//                    progress.dismiss();
                    Toast.makeText(EntityNameListActivity.this, "Response Taking Time,Seems Network issue in staff thowable!"+t.toString(), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onComplete() {
//                    progress.dismiss();
                }
            });
        } catch (Exception ex) {

        }
    }
    private void generateSMS(ArrayList<DashboardDetail> dashboardDetails) {
        Log.e(TAG, "generateStaffList: "+dashboardDetails.toString() );
        staffDetail.addAll(dashboardDetails);
        Log.e(TAG, "staffDetail: "+staffDetail.toString() );
        final CustomAdapter customAdapter=new CustomAdapter(EntityNameListActivity.this,null,staffDetail,null);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            DashboardDetail model = staffDetail.get(i);
            if (model.isSelected()) {
                model.setSelected(false);
                mobile.remove(i);
            }
            else
                model.setSelected(true);
            Log.e(TAG, "generateTeacherList_afetrClick: "+mTeacherDetails.toString());
            mobile.add(staffDetail.get(i).getIntmobileno());
            Log.e(TAG, "generateTeacherList_mobile: "+mobile.toString() );
            staffDetail.set(i, model);

            //now update adapter
            customAdapter.updateRecords(null,staffDetail,null);
        });
    }
    public DisposableObserver<TeacherDetailPojo> getObserver_teacher(){
        return new DisposableObserver<TeacherDetailPojo>() {

            @Override
            public void onNext(@NonNull TeacherDetailPojo teacherDetailPojo) {
                try {
                    generateTeacherList((ArrayList<TeacherDetail>) teacherDetailPojo.getTeacherDetail());
                } catch (Exception ex) {
                    Toast.makeText(EntityNameListActivity.this, "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(EntityNameListActivity.this, "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
            }
        };
    }

    private void generateTeacherList(ArrayList<TeacherDetail> teacherDetail) {

        Log.e(TAG, "generateTeacherList: "+teacherDetail.toString() );
        teacherDetails.addAll(teacherDetail);
        final CustomAdapter customAdapter=new CustomAdapter(this,teacherDetails,null,null);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            TeacherDetail model = teacherDetails.get(i);
            if (model.isSelected()) {
                model.setSelected(false);
//                mobile.remove(i);
            }
            else
                model.setSelected(true);
            Log.e(TAG, "generateTeacherList_afetrClick: "+mTeacherDetails.toString());
            mobile.add(teacherDetails.get(i).getIntMobileNo());
            Log.e(TAG, "generateTeacherList_mobile: "+mobile.toString() );
            teacherDetails.set(i, model);
            //now update adapter
            customAdapter.updateRecords(teacherDetails,null,null);
        });
    }
    private void callStudent(String standard,String div) {

        Log.e(TAG, "callStudent: "+Schooli_id+"__"+academic_id+"__"+standard+"__"+mdivId);
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            mCompositeDisposable.add(service.getStudentStandardWiseDetails("select",Schooli_id,academic_id,standard, div)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(getObserver()));
        } catch (Exception ex) {
        }
    }
    public DisposableObserver<StudentStandardwiseDetailPojo> getObserver(){
        return new DisposableObserver<StudentStandardwiseDetailPojo>() {

            @Override
            public void onNext(@NonNull StudentStandardwiseDetailPojo studentStandardwiseDetailPojo) {
                try {

                    generateStudentList((ArrayList<StudentStandardwiseDetail>) studentStandardwiseDetailPojo.getStudentStandardwiseDetail());
                } catch (Exception ex) {
                    Log.e(TAG, "onNext: "+ex.toString());
                    Toast.makeText(EntityNameListActivity.this, "Response Taking Time,Seems Network issue !"+ex.toString(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError_Mobile"+e.toString() );
                Toast.makeText(EntityNameListActivity.this, "Response Taking Time,Seems Network issue!"+e.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
            }
        };
    }

    private void generateStudentList(ArrayList<StudentStandardwiseDetail> studentStandardwiseDetail) {
        Log.d(TAG, "generateStudentList_mobile: "+studentStandardwiseDetail.toString());
            studentDetail.addAll(studentStandardwiseDetail);
        final CustomAdapter customAdapter=new CustomAdapter(EntityNameListActivity.this,null,null,studentDetail);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            StudentStandardwiseDetail model = studentDetail.get(i);
            if (model.isSelected()) {
                model.setSelected(false);
                mobile.remove(i);
            }
            else
                model.setSelected(true);

            mobile.add(studentDetail.get(i).getMobile_number());

            studentDetail.set(i, model);

            //now update adapter
            customAdapter.updateRecords(null,null,studentDetail);
        });


    }

}
