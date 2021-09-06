package info.efficacious.centralmodelschool.activity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import info.efficacious.centralmodelschool.Interface.DataService;
import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.entity.DashboardDetail;
import info.efficacious.centralmodelschool.entity.DashboardDetailsPojo;
import info.efficacious.centralmodelschool.entity.Message;
import info.efficacious.centralmodelschool.entity.MessagePojo;
import info.efficacious.centralmodelschool.entity.StudentStandardwiseDetail;
import info.efficacious.centralmodelschool.entity.StudentStandardwiseDetailPojo;
import info.efficacious.centralmodelschool.entity.TeacherDetail;
import info.efficacious.centralmodelschool.entity.TeacherDetailPojo;
import info.efficacious.centralmodelschool.webApi.RetrofitInstance;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class EntityNameActivity extends ListActivity implements View.OnClickListener {
    Button btn_cancel, btn_done;
    String args, mdivId, standard, arguments,studentstandard;
    int division;
    String academic_id, Schooli_id,cmd;
    SharedPreferences settings;
    private static final String PREFRENCES_NAME = "myprefrences";
    private static final String TAG = "EntityNameActivity";
    private List<DashboardDetail> staffDetail = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ArrayList<String> arraylist,arraynamelist;
    ArrayList<String> mobileArray = new ArrayList<>();
    List<TeacherDetail> teacherDetails = new ArrayList<>();
    List<Message> messages=new ArrayList<>();
    List<StudentStandardwiseDetail> studentStandardwiseDetails = new ArrayList<>();
    private CompositeDisposable mCompositeDisposable;
    String[] arrays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_name);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EntityNameActivity.this,NewmessegesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        btn_done = findViewById(R.id.btn_doneselectedTeacher);
        settings = this.getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        academic_id = settings.getString("TAG_ACADEMIC_ID", "");
        Schooli_id = settings.getString("TAG_SCHOOL_ID", "");
        Bundle newmessagebundle = getIntent().getExtras();
        args = newmessagebundle.getString("args");
        arguments = newmessagebundle.getString("std");
        division = newmessagebundle.getInt("div");
        studentstandard=newmessagebundle.getString("standard_id");
        settings = this.getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        btn_done.setOnClickListener(this);
        if (args != null) {
            if (args.equals("fromStaff")) {
                usertype_staff("AllStaffByPrincipal");
            } else if (args.equals("fromTeacher")) {
                usertype_selected("select");
            }else if (args.equals("fromstudent"))
            {
                cmd="ListOfAllStudents";
                callAllStudent(cmd,studentstandard,Schooli_id,academic_id);
            }else if (division != 0 && arguments != null) {
                mdivId = String.valueOf(division);
                standard = arguments;
                callStudent(standard, mdivId);
                Log.d(TAG, "onCreate_c" +
                        "allstudent: " + standard + "__" + mdivId);
            }else if (division==0)
            {
                Log.d(TAG, "onCreate: divison is 0");
                //        >http://122.170.4.112/CMSWebApi/api/Message/?command=ListOfStudentsStandardWise&intStandard_id=6&intSchool_id=1&intAcademic_id=4
                cmd="ListOfStudentsStandardWise";
                callAllStudent( cmd,arguments,Schooli_id,academic_id);
            }
        }

        View.OnClickListener clickListener = v -> {
            CheckBox chk = (CheckBox) v;
            Toast.makeText(this, "You click all", Toast.LENGTH_SHORT).show();
            int itemCount = getListView().getCount();
            if (itemCount!=0) {
                for (int i = 0; i < itemCount; i++) {
                    getListView().setItemChecked(i, chk.isChecked());
                    mobileArray.add(arrays[i]);
                }
            }else {
                for (int i = 0; i < itemCount; i++) {
                    getListView().setItemChecked(i, chk.isChecked());
                    mobileArray.remove(arrays[i]);
                }
            }
        };
        /** Defining click event listener for the listitem checkbox */
        CheckBox chkAll = (CheckBox) findViewById(R.id.chkAll);

        /** Setting a click listener for the checkbox **/
        chkAll.setOnClickListener(clickListener);

        /** Setting a click listener for the listitem checkbox **/
        getListView().setOnItemClickListener((adapterView, view, i, l) -> {
            Log.d(TAG, "onItemClick_listview: " + i + "Long" + l);
            if (mobileArray.contains(arrays[i].toString()))
                mobileArray.remove(arrays[i]);
            else
                mobileArray.add(arrays[i]);
        });


    }

    private void callAllStudent(String cmd,String studentstandard, String schooli_id, String academic_id) {

        Log.e(TAG, "callAllStudent: "+studentstandard+schooli_id+academic_id);
                try {
                    DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);

                    Observable<MessagePojo> call = service.getMessage(cmd, schooli_id, studentstandard, academic_id);
                    call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MessagePojo>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(MessagePojo messagePojo) {
                            generatestuentlist((ArrayList<Message>) messagePojo.getSendMessages());
                            Log.e(TAG, "onNext: " + messagePojo.getSendMessages().get(0).toString());
                        }

                        @Override
                        public void onError(Throwable e) {

                            Toast.makeText(EntityNameActivity.this, "Throwable" + e, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {
                            Toast.makeText(EntityNameActivity.this, "onComplete", Toast.LENGTH_SHORT).show();

                        }
                    });
                }catch (Exception e){
                    Toast.makeText(this, "Exception"+e, Toast.LENGTH_SHORT).show();
                }

    }

    private void generatestuentlist(ArrayList<Message> message) {
        Log.e(TAG, "generateTeacherList: " + message.toString());
        message.addAll(message);
        arraylist = new ArrayList<>();
        arraynamelist=new ArrayList<>();
        for (int i = 0; i < message.size(); i++) {
            arraylist.add(message.get(i).getIntBusAlert1());
        }
        for (int i = 0; i < message.size(); i++) {
            arraynamelist.add(message.get(i).getStudent_Name());
        }

        arrays = new String[arraylist.size()];
        arrays = arraylist.toArray(arrays);


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, arraynamelist);
        getListView().setAdapter(adapter);

    }

    public void usertype_staff(String allSMSAdmin) {
        try {
            String command = allSMSAdmin;
            Log.e(TAG, "usertype_staff: " + command + "_" + academic_id + "_" + Schooli_id);
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
                        Log.e(TAG, "onNext: " + body.getDashboardDetails());
                        generateSMS((ArrayList<DashboardDetail>) body.getDashboardDetails());
                    } catch (Exception ex) {
//                        progress.dismiss();
                        Log.e(TAG, "onNext: " + ex.toString());
                        Toast.makeText(EntityNameActivity.this, "Response Taking Time,Seems Network issue in staff ex!" + ex.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
//                    progress.dismiss();
                    Toast.makeText(EntityNameActivity.this, "Response Taking Time,Seems Network issue in staff thowable!" + t.toString(), Toast.LENGTH_SHORT).show();

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
        Log.e(TAG, "generateStaffList: " + dashboardDetails.toString());
        staffDetail.addAll(dashboardDetails);
        arraylist = new ArrayList<>();
        arraynamelist=new ArrayList<>();
        for (int i = 0; i < staffDetail.size(); i++) {
            arraylist.add(staffDetail.get(i).getIntmobileno());
        }
        for (int i = 0; i < staffDetail.size(); i++) {
            arraynamelist.add(staffDetail.get(i).getStaffName());
        }

        arrays = new String[arraylist.size()];
        arrays = arraylist.toArray(arrays);


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, arraynamelist);
        getListView().setAdapter(adapter);

    }

    private int getCheckedItemCount() {
        int cnt = 0;
        SparseBooleanArray positions = getListView().getCheckedItemPositions();
        Log.d(TAG, "int_position: " + asKeyList(positions));
//        String[] s=  asKeyList(positions);
//        mobileArray.add(arrays[Integer.parseInt(s)]);

        int itemCount = getListView().getCount();
        for (int i = 0; i < itemCount; i++) {
            if (positions.get(i))
                cnt++;
        }

        Log.d(TAG, "getCheckedItemCount_positions: " + positions);
        Log.d(TAG, "getCheckedItemCount_cnt: " + cnt);
        Log.d(TAG, "getCheckedItemCount_item: " + itemCount);
        return cnt;
    }


    public static ArrayList<Integer> asKeyList(
            SparseBooleanArray sparseArray) {
        ArrayList<Integer> arrayList = null;
        if (sparseArray != null && sparseArray.size() > 0) {
            int count = sparseArray.size();
            arrayList = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                arrayList.add(sparseArray.keyAt(i));
            }/*from   w w  w . j av  a2s.  c o m*/
        }
        return arrayList;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_doneselectedTeacher:
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("mobile", mobileArray);
                bundle.putString("key", "fromTeacherNameList");
//                SM.sendData(mobile);
                Intent intent1 = new Intent(EntityNameActivity.this, NewmessegesActivity.class);
                intent1.putExtra("key_teachermobile", mobileArray);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1, bundle);
                Log.e(TAG, "onClick: " + mobileArray.toString());
                getFragmentManager().popBackStack();
//                                Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onClick_done: " + mobileArray.toString());

                break;
        }
    }

    public void usertype_selected(String allSMSAdmin) {

        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            if (allSMSAdmin.equals("select")) {
                Log.e(TAG, "usertype_selected: " + Schooli_id);
                try {
                    mCompositeDisposable.add(service.getTeacherDetails(allSMSAdmin, Schooli_id)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribeWith(getObserver_teacher()));
                } catch (Exception ex) {
                }
            } else if (allSMSAdmin.equals("AllStaff")) {
                try {
                    usertype_staff(allSMSAdmin);
                } catch (Exception ex) {
                }
            }

        } catch (Exception ex) {

        }
    }

    public DisposableObserver<TeacherDetailPojo> getObserver_teacher() {
        return new DisposableObserver<TeacherDetailPojo>() {

            @Override
            public void onNext(@NonNull TeacherDetailPojo teacherDetailPojo) {
                try {
                    generateTeacherList((ArrayList<TeacherDetail>) teacherDetailPojo.getTeacherDetail());
                } catch (Exception ex) {
                    Toast.makeText(EntityNameActivity.this, "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(EntityNameActivity.this, "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
            }
        };
    }

    private void generateTeacherList(ArrayList<TeacherDetail> teacherDetail) {

        Log.e(TAG, "generateTeacherList: " + teacherDetail.toString());
        teacherDetails.addAll(teacherDetail);
        arraylist = new ArrayList<>();
        arraynamelist=new ArrayList<>();
        for (int i = 0; i < teacherDetails.size(); i++) {
            arraylist.add(teacherDetails.get(i).getIntMobileNo());
        }
        for (int i = 0; i < teacherDetails.size(); i++) {
            arraynamelist.add(teacherDetails.get(i).getName());
        }
        Log.d(TAG, "generateTeacherList_name: "+arraynamelist.toString());
        arrays = new String[arraylist.size()];
        arrays = arraylist.toArray(arrays);


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, arraynamelist);
        getListView().setAdapter(adapter);

    }

    private void callStudent(String standard, String div) {
 //http://122.170.4.112/CMSWebApi/api/Message/?command=ListOfStudentsStandardWise&intStandard_id=6&intSchool_id=1&intAcademic_id=4

        Log.e(TAG, "callStudent: " + Schooli_id + "__" + academic_id + "__" + standard + "__" + mdivId);
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            mCompositeDisposable.add(service.getStudentStandardWiseDetails("select", Schooli_id, academic_id, standard, div)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(getObserver()));
        } catch (Exception ex) {
        }
    }

    public DisposableObserver<StudentStandardwiseDetailPojo> getObserver() {
        return new DisposableObserver<StudentStandardwiseDetailPojo>() {

            @Override
            public void onNext(@NonNull StudentStandardwiseDetailPojo studentStandardwiseDetailPojo) {
                try {

                    generateStudentList((ArrayList<StudentStandardwiseDetail>) studentStandardwiseDetailPojo.getStudentStandardwiseDetail());
                } catch (Exception ex) {
                    Log.e(TAG, "onNext: " + ex.toString());
                    Toast.makeText(EntityNameActivity.this, "Response Taking Time,Seems Network issue !" + ex.toString(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError_Mobile" + e.toString());
                Toast.makeText(EntityNameActivity.this, "Response Taking Time,Seems Network issue!" + e.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
            }
        };
    }

    private void generateStudentList(ArrayList<StudentStandardwiseDetail> studentStandardwiseDetail) {
        Log.d(TAG, "generateStudentList_mobile: " + studentStandardwiseDetail.toString());
        studentStandardwiseDetails.addAll(studentStandardwiseDetail);
        arraylist = new ArrayList<>();
        arraynamelist=new ArrayList<>();
        for (int i = 0; i < studentStandardwiseDetails.size(); i++) {
            arraylist.add(studentStandardwiseDetails.get(i).getMobile_number());
        }for (int i = 0; i < studentStandardwiseDetails.size(); i++) {
            arraynamelist.add(studentStandardwiseDetails.get(i).getName());
        }
        arrays = new String[arraylist.size()];
        arrays = arraylist.toArray(arrays);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, arraynamelist);
        getListView().setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
