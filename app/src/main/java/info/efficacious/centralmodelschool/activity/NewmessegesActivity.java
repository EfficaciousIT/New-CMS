package info.efficacious.centralmodelschool.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import info.efficacious.centralmodelschool.Interface.DataService;
import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.adapters.StandardGridAdapter;
import info.efficacious.centralmodelschool.common.ConnectionDetector;
import info.efficacious.centralmodelschool.dialogbox.BottomLayoutSheetDialog;
import info.efficacious.centralmodelschool.entity.StandardDetail;
import info.efficacious.centralmodelschool.entity.StandardDetailsPojo;
import info.efficacious.centralmodelschool.webApi.RetrofitInstance;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NewmessegesActivity extends AppCompatActivity implements
        View.OnClickListener, StandardGridAdapter.OnStandardListener {
    View view;
    private static final String PREFRENCES_NAME = "myprefrences";
    Button btn_multiple, btn_student, btn_teacher, btn_staff, btnDone;
    String school_id;
    RecyclerView standard_rv, standardgrid;
    ConnectionDetector cd;
    SharedPreferences settings;
    private static final String TAG = "NewMessage";
    private CompositeDisposable mCompositeDisposable;
    int numberOfColumns = 6;
    LinearLayout standardslayout, rdgroupLinear;
    String standard;
    ArrayList<StandardDetail> mstandardDetails = new ArrayList<>();
    ArrayList<String> mstandard_id = new ArrayList<>();
    List<String> mobilenumber;
    ArrayList<String> mteacher_mobile = new ArrayList<>();
    ArrayList<String> studentname = new ArrayList<>();
    TextView tv_selected_count;
    EditText edt_msg, medt_multiplmobile;
    String msgData;
    Button btnSendSms;
    ImageButton btn_rdDone;
    int counter;
    LinearLayout btnids_layout;
    Bundle bundle = new Bundle();
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmesseges);
        edt_msg = findViewById(R.id.edt_msg);
        btnids_layout=findViewById(R.id.btnids_layout);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mteacher_mobile = bundle.getStringArrayList("key_teachermobile");
            counter = mteacher_mobile.size();
            edt_msg.setVisibility(View.VISIBLE);
            btnids_layout.setVisibility(View.VISIBLE);
            Log.d(TAG, "onCreate_counter: " + counter + "Mobile_number" + mteacher_mobile.toString());
        }
        else {
            edt_msg.setVisibility(View.GONE);
            btnids_layout.setVisibility(View.GONE);
        }
        rdgroupLinear = findViewById(R.id.radioButtonGroupLayout);
        medt_multiplmobile = findViewById(R.id.medtmultiplemobile);

        btn_multiple = findViewById(R.id.btn_multiple);
        btn_student = findViewById(R.id.btn_student);
        btn_teacher = findViewById(R.id.btn_teacher);
        btn_staff = findViewById(R.id.btn_staff);
        btnDone = findViewById(R.id.btnDone);
        btn_rdDone = findViewById(R.id.btn_rdDone);

        edt_msg.requestFocus();
        btnSendSms = findViewById(R.id.send_msg);
        tv_selected_count = findViewById(R.id.selected_count);
        cd = new ConnectionDetector(this.getApplicationContext());
        standardslayout = findViewById(R.id.standardslayout);
        standardslayout.setVisibility(View.GONE);

//        standard_rv=view.findViewById(R.id.standard_rv);
        btn_multiple.setOnClickListener(this);
        btn_student.setOnClickListener(this);
        btn_teacher.setOnClickListener(this);
        btn_staff.setOnClickListener(this);
        btnDone.setOnClickListener(this);
        btnSendSms.setOnClickListener(this);
        btn_rdDone.setOnClickListener(this);
        settings = this.getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        school_id = settings.getString("TAG_SCHOOL_ID", "");
        standardgrid = findViewById(R.id.standardgrid);
        tv_selected_count.setText(counter + "selected");
//        standardgrid.setVisibility(View.GONE);
        if (!cd.isConnectingToInternet()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("No Internet Connection");
            alert.setPositiveButton("OK", null);
            alert.show();

        } else {
            try {
                standardCall();
            } catch (Exception ex) {

            }
        }
    }

    private void standardCall() {
        Log.d(TAG, "standardCall: " + school_id);
        DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
        mCompositeDisposable.add(service.getStandardDetails("select", school_id, "", "", "", "", "")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(getObserver()));
    }

    public DisposableObserver<StandardDetailsPojo> getObserver() {
        return new DisposableObserver<StandardDetailsPojo>() {

            @Override
            public void onNext(@NonNull StandardDetailsPojo dashboardDetailsPojo) {
                try {
                    mstandardDetails = (ArrayList<StandardDetail>) dashboardDetailsPojo.getStandardDetails();
                    generateStandradByLectures((ArrayList<StandardDetail>) dashboardDetailsPojo.getStandardDetails());
                } catch (Exception ex) {
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(NewmessegesActivity.this, "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getFragmentManager().getBackStackEntryCount() == 0)
            finish();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_multiple:
                rdgroupLinear.setVisibility(View.VISIBLE);
                standardslayout.setVisibility(View.GONE);
                medt_multiplmobile.setVisibility(View.VISIBLE);
                medt_multiplmobile.requestFocus();
                tv_selected_count.setVisibility(View.GONE);
                break;
            case R.id.btn_student:
                edt_msg.setVisibility(View.GONE);
                btnids_layout.setVisibility(View.GONE);
                rdgroupLinear.setVisibility(View.GONE);
                boolean selected = !view.isSelected();
                view.setSelected(selected);
                Log.e(TAG, "btn_student_onClick: ");
                standardslayout.setVisibility(View.VISIBLE);
                btnDone.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_teacher:
                rdgroupLinear.setVisibility(View.GONE);
                standardslayout.setVisibility(View.GONE);
//                Intent intent = new Intent(NewmessegesActivity.this, EntityNameListActivity.class);

                Intent intent = new Intent(NewmessegesActivity.this, EntityNameActivity.class);
                intent.putExtra("args", "fromTeacher");
                startActivity(intent);
//                EntityNameList entityTeacherNameList = new EntityNameList();
//
//                FragmentManager manager=getSupportFragmentManager();
//                bundle.putString("args","fromTeacher");
//                entityTeacherNameList.setArguments(bundle);
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.replace(R.id.content_main,entityTeacherNameList).commitAllowingStateLoss();
//                transaction.addToBackStack(null);
                break;
            case R.id.btn_staff:
                rdgroupLinear.setVisibility(View.GONE);

                Intent staffintent = new Intent(NewmessegesActivity.this, EntityNameActivity.class);
//                Intent staffintent = new Intent(NewmessegesActivity.this, EntityNameListActivity.class);
                staffintent.putExtra("args", "fromStaff");
                startActivity(staffintent);
//                EntityNameList entityStaffNameList = new EntityNameList();
//                bundle.putString("args","fromStaff");
//                entityStaffNameList.setArguments(bundle);
//                FragmentManager staffmanager = getSupportFragmentManager();
//                FragmentTransaction stafftransaction = staffmanager.beginTransaction();
//                stafftransaction.replace(R.id.content_main, entityStaffNameList);
//                stafftransaction.addToBackStack(null);
                break;
            case R.id.btnDone:
                if (mstandard_id.get(0).equals("0"))
                {
                    Toast.makeText(this, "You_select"+mstandard_id, Toast.LENGTH_SHORT).show();
                    Intent studentintent = new Intent(NewmessegesActivity.this, EntityNameActivity.class);
                    Bundle bundle=new Bundle();
                    studentintent.putExtra("standard_id",mstandard_id.get(0));
                    studentintent.putExtra("args", "fromstudent");
                    startActivity(studentintent,bundle);
                }

                Bundle bundle = new Bundle();

                bundle.putStringArrayList("key", mstandard_id);
                bundle.putString("args", "fromNewMsg");
                BottomLayoutSheetDialog bottomLayoutSheetDialog = new BottomLayoutSheetDialog();
                bottomLayoutSheetDialog.setArguments(bundle);
                bottomLayoutSheetDialog.show(getSupportFragmentManager(), "exampleBottomSheet");

                standardgrid.setVisibility(View.GONE);
                btnDone.setVisibility(View.GONE);
                break;
            case R.id.send_msg:
                if (edt_msg.getText().toString().equals("")) {
                    Toast.makeText(this, "Pleasse Enter messgae", Toast.LENGTH_LONG);

                } else {
                    Log.e(TAG, "onClick: " + mteacher_mobile.size());
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Do you want to sent " + mteacher_mobile.size() + " messages?");
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                            msgData = edt_msg.getText().toString();
                            multiple multiple = new multiple(msgData, mteacher_mobile);
                            multiple.execute();
                            tv_selected_count.setText("");
                        }
                    });
                    builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                break;
            case R.id.btn_rdDone:

                if (medt_multiplmobile.getText().toString().equals("")) {

                    Toast.makeText(this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                } else {
                    edt_msg.setVisibility(View.VISIBLE);
                    btnids_layout.setVisibility(View.VISIBLE);
                    String phoneno = medt_multiplmobile.getText().toString();
                    mteacher_mobile = new ArrayList<String>(Arrays.asList(phoneno.split(",")));
                    Log.e(TAG, "onClick_multiple: " + mteacher_mobile.toString());
                    btn_rdDone.setVisibility(View.GONE);
                    medt_multiplmobile.setEnabled(false);
                    edt_msg.requestFocus();

                }
                break;
        }
    }

    private void generateStandradByLectures(ArrayList<StandardDetail> standardDetails) {
        Log.e("TAG", "Standard" + standardDetails.get(0).getVchStandardName());

        if (standardDetails != null || !standardDetails.isEmpty()) {
            StandardGridAdapter standardGridAdapter = new StandardGridAdapter(standardDetails, this, this);
//            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//            RecyclerView.LayoutManager gridlayoutmanager=new GridLayoutManager(getActivity(),numberOfColumns);
            StaggeredGridLayoutManager gridlayoutmanager = new StaggeredGridLayoutManager(numberOfColumns, LinearLayoutManager.VERTICAL);
            standardgrid.setLayoutManager(gridlayoutmanager);
            standardgrid.setAdapter(standardGridAdapter);
        }
    }

    @Override
    public void onStandardClickdata(String data) {
        int position = Integer.parseInt(data);
        mstandard_id.clear();
        mstandard_id.add(String.valueOf(mstandardDetails.get(position).getIntStandardId()));
        Log.e(TAG, "mstandard_id: " + mstandard_id);
    }


    @Override
    public void onStandardClick(int position) {

    }

    private class multiple extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(NewmessegesActivity.this);
        String message = "";
        int SMS_SendCount = 0;
        int Total_Count = 0;
        List<String> mmobile_number = new ArrayList<>();

        public multiple(String sms, List<String> mobile_number) {
            message = sms;
            mmobile_number = mobile_number;
        }

        protected Void doInBackground(Void... voids) {
            try {
                Total_Count = mmobile_number.size();
                HttpURLConnection uc = null;
                String requestUrl = "";
                for (int i = 0; i < mmobile_number.size(); i++) {
                    Log.d("TAG", "multiple" + mmobile_number.get(i) + "__" + message);
//                    requestUrl = ("http://alerts.justnsms.com/api/web2sms.php?workingkey=A2cabcee227fa491ee050155a13485498&sender=CMSBKP&to=" + URLEncoder.encode(phone_no_list.get(i), "UTF-8") + "&message=" + URLEncoder.encode(message, "UTF-8") + "&format=json&custom=1,2&flash=0&unicode=1");
//                    requestUrl = ("http://www.smsjust.com/sms/user/urlsms.php?username=Central Model school &pass=$b5@ZtX3&senderid=CMSBKP&dest_mobileno=" + URLEncoder.encode(mmobile_number.get(i), "UTF-8") + "&msgtype=TXT&message=" + URLEncoder.encode(message, "UTF-8") + "&response=Y");
                    requestUrl = ("http://www.smsjust.com/sms/user/urlsms.php?username=Arohan School&pass=XH9@xy0-&senderid=AROHAN&dest_mobileno=" + URLEncoder.encode(mmobile_number.get(i), "UTF-8") + "&msgtype=UNI&message=" + URLEncoder.encode(message, "UTF-8") + "&response=Y");


//                    "http://www.smsjust.com/sms/user/urlsms.php?username=Arohan School&pass=XH9@xy0-&senderid=AROHAN&dest_mobileno=" + OTPvalidate.mobile_number + "&msgtype=UNI&message=" + Message + "&response=Y";
//                    POST("http://www.smsjust.com/sms/user/urlsms.php?username=Central Model school &pass=$b5@ZtX3&senderid=CMSBKP&dest_mobileno=" + strMobileNo + "&msgtype=TXT&message=" + txtNotice.Text.Trim() + "&response=Y", "");

                    URL url = new URL(requestUrl);
                    Log.d("TAG", "url" + url);
                    uc = (HttpURLConnection) url.openConnection();
                    String responseMessage = uc.getResponseMessage();
                    Log.d("TAG", "responseMessage" + responseMessage);
                    if (responseMessage.contentEquals("OK")) {
                        SMS_SendCount = SMS_SendCount + 1;
                        //insert api call
                    }
                    uc.disconnect();
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Sending SMS...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            try {
//                phone_no.setText("");
//                sms_box3.setText("");
//                flag = true;
                Toast.makeText(NewmessegesActivity.this, "", Toast.LENGTH_SHORT).show();
                Toast.makeText(NewmessegesActivity.this, "SMS Send Successfully", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alert = new AlertDialog.Builder(NewmessegesActivity.this);
                alert.setMessage(SMS_SendCount + " SMS Sent Successfully Out of " + Total_Count);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        edt_msg.setText("");
                    }
                });
                alert.show();

            } catch (Exception ex) {
            }
        }
    }
}
