package info.efficacious.centralmodelschool.dialogbox;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import info.efficacious.centralmodelschool.Interface.DataService;
import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.adapters.StandardAdapter;
import info.efficacious.centralmodelschool.adapters.Student_division_adapter;
import info.efficacious.centralmodelschool.entity.StandardDetail;
import info.efficacious.centralmodelschool.entity.StandardDetailsPojo;
import info.efficacious.centralmodelschool.webApi.RetrofitInstance;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/*public class TimeTable_Division_Dialogbox extends AppCompatActivity {
     Button btnOk, btnCancel;
     private ProgressDialog progress;
     private CompositeDisposable mCompositeDisposable;
     SharedPreferences settings;
     private Context mcontext;
     Spinner spn_div;
     Division_spinner_adapter adapter1;
     private TextView tx_div;
     private ArrayList<HashMap<Object, Object>> dataList2;
     public String value;
     public String academic_id;
     public String school_id;
     public String role_id;
     public String userid;
     public static String Standard_id;
     public static String div;

     HashMap<Object, Object> map;
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         requestWindowFeature(Window.FEATURE_NO_TITLE);
         setContentView(R.layout.activity_time_table__division__dialogbox);
         Standard_id= StandardAdapter.std_id;
         btnCancel = findViewById(R.id.btn_cancel);
         mcontext = getApplicationContext();
         spn_div = findViewById(R.id.sp_div);
         btnOk=findViewById(R.id.btn_ok);
         btnOk.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(mcontext,""+div,Toast.LENGTH_LONG).show();



             }
         });
         *//*school_id = settings.getString("TAG_SCHOOL_ID", "");
        Standard_id = settings.getString("TAG_STANDERDID", "");*//*
        dataList2 = new ArrayList<HashMap<Object, Object>>();
//        tx_div = (TextView) findViewById(R.id.txt1);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    finish();
                } catch (Exception ex) {

                }
            }
        });
        spn_div.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                div = String.valueOf(dataList2.get(position).get("div"));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        try {
            progress = new ProgressDialog(TimeTable_Division_Dialogbox.this);
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.setMessage("loading...");
            DivisionAsync();
        } catch (Exception ex) {

        }

        *//*Intent intent = getIntent();
        Standard_id= intent.getStringExtra("std");*//*
    }

    private void DivisionAsync() {
        DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
        mCompositeDisposable.add(service.getStandardDetails("GetDivision", "1", "", Standard_id, "", "", "")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(getObserver()));
    }

    public DisposableObserver<StandardDetailsPojo> getObserver() {
        return new DisposableObserver<StandardDetailsPojo>() {

            @Override
            public void onNext(@NonNull StandardDetailsPojo dashboardDetailsPojo) {
                try {
                    generateDivByLectures((ArrayList<StandardDetail>) dashboardDetailsPojo.getStandardDetails());
                } catch (Exception ex) {

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(mcontext, "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    private void generateDivByLectures(ArrayList<StandardDetail> standardDetails) {
        try {
            if ((standardDetails != null && !standardDetails.isEmpty())) {
                dataList2.clear();
                for (int i = 0; i < standardDetails.size(); i++) {
                    map = new HashMap<Object, Object>();
                    map.put("div", String.valueOf(standardDetails.get(i).getVchDivisionName()));
                    dataList2.add(map);
                }
                try {
                    adapter1 = new Division_spinner_adapter(this, dataList2, "Division_name");
                    spn_div.setAdapter(adapter1);
                } catch (Exception ex) {

                }

            } else {

            }

        } catch (Exception ex) {
            progress.dismiss();
            Toast.makeText(this, "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
        }
//        Toast.makeText(mcontext, "" + standardDetails.get(0).getVchDivisionName(), Toast.LENGTH_LONG).show();

    }
*/
    public class TimeTable_Division_Dialogbox extends AppCompatActivity {

            TextView heading;
            RecyclerView recyclerView;
            String value;
            private static final String PREFRENCES_NAME = "myprefrences";
            SharedPreferences settings;
            String role_id, academic_id;
            String Standard_id, userid, school_id;
            private CompositeDisposable mCompositeDisposable;

            @Override
            protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_dialog_box);
            settings = getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
            role_id = settings.getString("TAG_USERTYPEID", "");
                Log.d("TAG","RollNO"+role_id);
            try {
                if (role_id.contentEquals("6") || role_id.contentEquals("7") || role_id.contentEquals("3")) {
                    school_id = StandardAdapter.intSchool_id;
                } else {
                    school_id = settings.getString("TAG_SCHOOL_ID", "");
                }
                userid = settings.getString("TAG_USERID", "");
                academic_id = settings.getString("TAG_ACADEMIC_ID", "");
                heading = (TextView) findViewById(R.id.title);
                heading.setText(" Division");

                recyclerView = (RecyclerView) findViewById(R.id.notificationlistView);
                value = StandardAdapter.page_name;
                Standard_id = StandardAdapter.Std_id_division;
                HolidayAsync();
            } catch (Exception ex) {

            }

        }

            public void HolidayAsync () {
            try {
                DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
                if (role_id.contentEquals("3")) {
                    mCompositeDisposable.add(service.getStandardDetails("selectDivisionByLectures", school_id, academic_id, Standard_id, "", userid, "")
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribeWith(getObserver()));
                } else {
                    mCompositeDisposable.add(service.getStandardDetails("GetDivision", school_id, "", Standard_id, "", "", "")
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribeWith(getObserver()));
                }

            } catch (Exception ex) {
            }
        }

            public DisposableObserver<StandardDetailsPojo> getObserver () {
            return new DisposableObserver<StandardDetailsPojo>() {

                @Override
                public void onNext(@NonNull StandardDetailsPojo dashboardDetailsPojo) {
                    try {
                        generateStandradByLectures((ArrayList<StandardDetail>) dashboardDetailsPojo.getStandardDetails());
                    } catch (Exception ex) {

                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    Toast.makeText(TimeTable_Division_Dialogbox.this, "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onComplete() {

                }
            };
        }

            @Override
            public void onDestroy () {
            super.onDestroy();
        }


            public void generateStandradByLectures (ArrayList < StandardDetail > taskListDataList) {
            try {
                if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                    Student_division_adapter division_adapter = new Student_division_adapter(TimeTable_Division_Dialogbox.this, taskListDataList, value);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TimeTable_Division_Dialogbox.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(division_adapter);
                } else {
                    finish();
                    Toast toast = Toast.makeText(TimeTable_Division_Dialogbox.this,
                            "No Data Available",
                            Toast.LENGTH_SHORT);
                    View toastView = toast.getView();
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toastView.setBackgroundResource(R.drawable.no_data_available);
                    toast.show();
                    // Toast.makeText(Standard_division_dialog.this, "No Data Available", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception ex) {
                Toast.makeText(TimeTable_Division_Dialogbox.this, "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
            }
        }

            @Override
            public void onBackPressed () {
            super.onBackPressed();
        }

        }



