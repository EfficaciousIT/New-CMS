package info.efficacious.centralmodelschool.fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import info.efficacious.centralmodelschool.Interface.DataService;
import info.efficacious.centralmodelschool.MultiImages.activities.AlbumSelectActivity;
import info.efficacious.centralmodelschool.MultiImages.helpers.Constants;
import info.efficacious.centralmodelschool.MultiImages.models.Image;
import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.activity.MainActivity;
import info.efficacious.centralmodelschool.adapters.Division_spinner_adapter;
import info.efficacious.centralmodelschool.adapters.Standard_Spinner;
import info.efficacious.centralmodelschool.common.ConnectionDetector;
import info.efficacious.centralmodelschool.entity.NoticeboardDetail;
import info.efficacious.centralmodelschool.entity.StandardDetail;
import info.efficacious.centralmodelschool.entity.StandardDetailsPojo;
import info.efficacious.centralmodelschool.webApi.RetrofitInstance;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class ApplyNotice extends AppCompatActivity {
    View myview;
    Spinner Usertype, Standard;
    EditText IssueDate, EndDate, NoticeSubject, Notice, attach_Image;
    private Calendar calendar;
    private int year, month, day;
    String fromdate, enddate, Usertype_selected, Standard_selected, Year_id;
    Button saveBtn;
    ConnectionDetector cd;
    Division_spinner_adapter adapter;
    int insertflag;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String Issue_Date, End_Date, Notice_Subject, Notice_Detail, role_id, userid, Schooli_id, standard_id,academic_id;
    RelativeLayout std_relativee;
    HashMap<Object, Object> map;
    private ArrayList<HashMap<Object, Object>> dataList;
    InputStream inputStream;
    public static final int PICK_IMAGE = 1;
    ArrayList<StandardDetail> Standard_list = new ArrayList<StandardDetail>();
    private static final String TAG = "ApplyNotice";
    String pathname;
    private ProgressDialog progressDoalog;
    public static ArrayList<String> FilePath = new ArrayList<String>();
    public static ArrayList<String> FileName = new ArrayList<String>();
    private String imageFilePath;
    private String filename;
    private String extension;
    MultipartBody.Part body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_notice);


        Usertype = findViewById(R.id.spinner_usertype);
        Standard = findViewById(R.id.spinner_standard);
        IssueDate = findViewById(R.id.notice_issueDate);
        EndDate = findViewById(R.id.notice_end_date);
        NoticeSubject = findViewById(R.id.subject_notice);
        Notice = findViewById(R.id.notice_et);
        saveBtn = findViewById(R.id.btnnoticeSubmit);
        std_relativee = findViewById(R.id.std_relativee);
        attach_Image = findViewById(R.id.attach_Image);
        settings = this.getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        role_id = settings.getString("TAG_USERTYPEID", "");
        Year_id = settings.getString("TAG_ACADEMIC_ID", "");
        standard_id = settings.getString("TAG_STANDERDID", "");
        Schooli_id = settings.getString("TAG_SCHOOL_ID", "");
        academic_id = settings.getString("TAG_ACADEMIC_ID", "");
        try {
            if (role_id.contentEquals("6") || role_id.contentEquals("7")) {
                Schooli_id = "";
            } else {
                Schooli_id = settings.getString("TAG_SCHOOL_ID", "");
            }
        } catch (Exception ex) {

        }
        attach_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(ApplyNotice.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                2000);
                    }
                } else {
                    startGallery();
                }
            }
        });

        dataList = new ArrayList<HashMap<Object, Object>>();
        userid = settings.getString("TAG_USERID", "");
        cd = new ConnectionDetector(this.getApplicationContext());

        saveBtn.setOnClickListener(v -> {
            try {
                Issue_Date = IssueDate.getText().toString();
                End_Date = EndDate.getText().toString();
                Notice_Detail = Notice.getText().toString();
                Notice_Subject = NoticeSubject.getText().toString();
                if (!Issue_Date.contentEquals("") && !End_Date.contentEquals("") && !Notice_Subject.contentEquals("") && !Notice_Detail.contentEquals("") && !Usertype_selected.contentEquals("-- Select UserType --") && !attach_Image.getText().toString().contentEquals("")) {
                    if (Usertype_selected.contentEquals("1")) {
                        if (!Standard_selected.contentEquals("-- Select Standard--")) {
                            if (!cd.isConnectingToInternet()) {

                                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                                alert.setMessage("No Internet Connection");
                                alert.setPositiveButton("OK", null);
                                alert.show();

                            } else {
                                SubmitASYNC();
                            }
                        } else {
                            Toast.makeText(this, "Please Fill Proper Data", Toast.LENGTH_LONG).show();
                        }

                    } else if (Usertype_selected.contentEquals("0")) {
                        if (!cd.isConnectingToInternet()) {

                            AlertDialog.Builder alert = new AlertDialog.Builder(this);
                            alert.setMessage("No Internet Connection");
                            alert.setPositiveButton("OK", null);
                            alert.show();

                        } else {
                            Standard_selected = "0";
                            if (role_id.contentEquals("6") || role_id.contentEquals("7")) {
                                SubmitAllASYNCByPrincipal1();
                            } else {
                                SubmitAllASYNC();
                            }
                        }
                    } else {
                        if (!cd.isConnectingToInternet()) {

                            AlertDialog.Builder alert = new AlertDialog.Builder(this);
                            alert.setMessage("No Internet Connection");
                            alert.setPositiveButton("OK", null);
                            alert.show();

                        } else {
                            Standard_selected = "0";
                            if (role_id.contentEquals("6") || role_id.contentEquals("7")) {
                                SubmitASYNC1();
                            } else {
                                SubmitASYNC();
                            }

                        }
                    }
                } else {
                    if (TextUtils.isEmpty(Issue_Date)) {
                        IssueDate.setError("Enter Valid Issue Date");
                    }
                    if (TextUtils.isEmpty(End_Date)) {
                        EndDate.setError("Enter Valid End Date");
                    }
                    if (TextUtils.isEmpty(Notice_Detail)) {
                        Notice.setError("Enter Notice");
                    }
                    if (TextUtils.isEmpty(Notice_Subject)) {
                        NoticeSubject.setError("Enter Notice Subject");
                    }
                    if (Usertype_selected.contentEquals("-- Select UserType --")) {
                        setSpinnerError(Usertype, "Select Usertype");
                    }
                    //Toast.makeText(getActivity(),"Please Fill Proper Data", Toast.LENGTH_LONG).show();
                }
            } catch (Exception ex) {
                ex.getMessage();
            }


        });
        IssueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                //showDate1(year, month+1, day);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ApplyNotice.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                try {
                                    NumberFormat f = new DecimalFormat("00");
                                    fromdate = ((f.format(monthOfYear + 1)) + "/" + (f.format(dayOfMonth)) + "/" + year);
//                            tv_dateSelection.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    SimpleDateFormat sdf = new SimpleDateFormat("mm-dd-yyyy");
                                    Date date1 = null;
                                    try {
                                        date1 = sdf.parse(((f.format(monthOfYear + 1)) + "-" + dayOfMonth + "-" + year));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    Date date2 = null;
                                    try {
                                        date2 = sdf.parse(fromdate);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    fromdate = ((f.format(dayOfMonth)) + "/" + (f.format(monthOfYear + 1)) + "/" + year);
                                    IssueDate.setText(((f.format(dayOfMonth)) + "/" + (f.format(monthOfYear + 1)) + "/" + year));
                                } catch (Exception ex) {
                                }
                            }
                        }, year, month, day);

                datePickerDialog.show();
            }
        });
        EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                //showDate1(year, month+1, day);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ApplyNotice.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                try {
                                    NumberFormat f = new DecimalFormat("00");
                                    enddate = ((f.format(monthOfYear + 1)) + "/" + (f.format(dayOfMonth)) + "/" + year);
//                            tv_dateSelection.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    SimpleDateFormat sdf = new SimpleDateFormat("mm-dd-yyyy");
                                    Date date1 = null;
                                    try {
                                        date1 = sdf.parse(((f.format(monthOfYear + 1)) + "-" + dayOfMonth + "-" + year));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    Date date2 = null;
                                    try {
                                        date2 = sdf.parse(enddate);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    enddate = ((f.format(dayOfMonth)) + "/" + (f.format(monthOfYear + 1)) + "/" + year);
                                    EndDate.setText(((f.format(dayOfMonth)) + "/" + (f.format(monthOfYear + 1)) + "/" + year));
                                } catch (Exception ex) {
                                }
                            }
                        }, year, month, day);

                datePickerDialog.show();
            }
        });
        Usertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Usertype_selected = String.valueOf(dataList.get(i).get("UserType_id"));
                    Issue_Date = IssueDate.getText().toString();
                    End_Date = EndDate.getText().toString();
                    Notice_Detail = Notice.getText().toString();
                    Notice_Subject = NoticeSubject.getText().toString();
                    if (Usertype_selected.contentEquals("-- Select UserType --")) {
                        //Toast.makeText(getActivity(),"NO Data available for this Month", Toast.LENGTH_LONG).show();
                    } else {
                        if (!cd.isConnectingToInternet()) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(ApplyNotice.this);
                            alert.setMessage("No Internet Connection");
                            alert.setPositiveButton("OK", null);
                            alert.show();
                        } else {
                            if (Usertype_selected.contentEquals("1")) {
                                std_relativee.setVisibility(View.VISIBLE);
                                StandardAsync();
                            } else {
                                std_relativee.setVisibility(View.GONE);

                            }

                        }
                    }
                } catch (Exception ex) {

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Standard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {

                    Standard_selected = String.valueOf(Standard_list.get(i).getIntStandardId());
//                    Schooli_id = String.valueOf(Standard_list.get(i).getIntschoolId());
                    Log.d(TAG, "onItemSelected: "+Standard_list.get(i).getIntschoolId());
                } catch (Exception ex) {
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        if (!cd.isConnectingToInternet()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("No Internet Connection");
            alert.setPositiveButton("OK", null);
            alert.show();
        } else {
            try {
                UserTypeAsync userTypeAsync = new UserTypeAsync();
                userTypeAsync.execute();
            } catch (Exception ex) {

            }
        }
    }
    private void startGallery() {
        Intent intent = new Intent(ApplyNotice.this, AlbumSelectActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 1);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
            StringBuffer stringBuffer = new StringBuffer();
            progressDoalog = new ProgressDialog(ApplyNotice.this);
            progressDoalog.setCancelable(false);
            progressDoalog.setCanceledOnTouchOutside(false);
            progressDoalog.setMessage("uploading123...");
            progressDoalog.show();
            try {
                if (images.size() < 2) {
                    if (images.size() == 0) {
                        Toast.makeText(this, "Size 0", Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(this, "Has size", Toast.LENGTH_SHORT).show();
                        progressDoalog.dismiss();

                        for (int i = 0, l = images.size(); i < l; i++) {
                            stringBuffer.append(images.get(i).path + "\n");
                            if (i == 0) {
                                Log.d("TAG", "Image_upload" + BitmapFactory.decodeFile(images.get(i).path));
                            }
                            pathname = images.get(i).path;
                            filename = images.get(i).name;
                            FilePath.add(pathname);
                            String fileName = filename;
                            String filename = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss_SS", Locale.getDefault()).format(new Date());
                            extension = fileName.substring(fileName.lastIndexOf("."));
                            extension = filename + extension;
                            Log.d(TAG, "extension: " + extension);
                            String pathname = FilePath.get(0);
                            File file = new File(pathname);
                            Log.d(TAG, "onActivityResult: " + file);
                            RequestBody requestFile =
                                    RequestBody.create(
                                            MediaType.parse("image/*"),
                                            file
                                    );
                            body =
                                    MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
                            attach_Image.setText(pathname);
                            attach_Image.setEnabled(false);
                        }
                    }
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(ApplyNotice.this);
                    alert.setMessage("Cannot Share more than 1 files");
                    alert.setPositiveButton("OK", null);
                    alert.show();
                }
            } catch (Exception ex) {

            }
        }
    }

    private void call_upload(String Usertype_selected, String intStandard_id, String intDepartment_id, String intTeacher_id, String dtIssue_date,
                             String dtEnd_date, String vchSubject, String vchNotice, String intInserted_by, String InsertIP, String intSchool_id) {
        DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
        Observable<ResponseBody> call = service.uploadNotice(body, Usertype_selected, intStandard_id, intDepartment_id
                , intTeacher_id, dtIssue_date, dtEnd_date, vchSubject, vchNotice, intInserted_by, InsertIP, intSchool_id);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                progressDoalog.show();
            }

            @Override
            public void onNext(ResponseBody body) {
                try {
                    Toast.makeText(ApplyNotice.this, "" + body.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    progressDoalog.dismiss();
                    Log.d(TAG, "onNext: " + ex.toString());
                    Toast.makeText(ApplyNotice.this, "Response Taking Time,Seems Network issue!" + ex.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(ApplyNotice.this, "Response Taking Time,Seems Network issue!" + t.toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onComplete() {
                progressDoalog.dismiss();
                Toast.makeText(ApplyNotice.this, "Profile Uploaded Successfully", Toast.LENGTH_SHORT).show();
//                    Profile_Fragment profile_fragment = new Profile_Fragment();
//                    MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, profile_fragment).commitAllowingStateLoss();
//                Profile  profile  = new Profile ();
//                MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, profile ).commitAllowingStateLoss();

                finish();

            }
        });

    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    private class UserTypeAsync extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(ApplyNotice.this);

        @Override
        protected Void doInBackground(Void... params) {

            dataList.clear();

            try {
                map = new HashMap<Object, Object>();
                map.put("UserType", "-- Select UserType --");
                map.put("UserType_id", "-- Select UserType --");
                dataList.add(map);
                map = new HashMap<Object, Object>();
                map.put("UserType", "All");
                map.put("UserType_id", "0");
                dataList.add(map);
                map = new HashMap<Object, Object>();
                map.put("UserType", "Student");
                map.put("UserType_id", "1");
                dataList.add(map);
                map = new HashMap<Object, Object>();
                map.put("UserType", "Teacher");
                map.put("UserType_id", "3");
                dataList.add(map);
                map = new HashMap<Object, Object>();
                map.put("UserType", "Staff");
                map.put("UserType_id", "4");
                dataList.add(map);
                map = new HashMap<Object, Object>();
                map.put("UserType", "Admin");
                map.put("UserType_id", "5");
                dataList.add(map);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                adapter = new Division_spinner_adapter(ApplyNotice.this, dataList, "NoticeUserType");
                Usertype.setAdapter(adapter);
            } catch (Exception ex) {

            }

            this.dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Processing...");
            dialog.show();
        }
    }

    public void StandardAsync() {
        try {
            try {
                StandardDetail standardDetail;
                standardDetail = new StandardDetail(0, "All", 0);
                Standard_list.addAll(Collections.singleton(standardDetail));
            } catch (Exception ex) {

            }
            String command;
            if (role_id.contentEquals("6") || role_id.contentEquals("7")) {
                command = "selectStandardByPrincipal";
            } else {
                command = "select";
            }
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<StandardDetailsPojo> call = service.getStandardDetails(command, Schooli_id, Year_id, "", "", "", "");
            Log.d("TAG" , "IAM3");
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<StandardDetailsPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {

                }

                @Override
                public void onNext(StandardDetailsPojo body) {
                    try {
                        Standard_list.addAll(body.getStandardDetails());
                        Log.d(TAG, "onNext_checkingStandardlist: "+Standard_list.toString());
                        Standard_Spinner adapter = new Standard_Spinner(ApplyNotice.this, Standard_list);
                        Standard.setAdapter(adapter);
                    } catch (Exception ex) {

                        Toast.makeText(ApplyNotice.this, "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    Toast.makeText(ApplyNotice.this, "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onComplete() {

                }
            });
        } catch (Exception ex) {

        }
    }

    public void SubmitASYNC() {
        final ProgressDialog dialog = new ProgressDialog(ApplyNotice.this);
        try {
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Processing...");
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<ResponseBody> call = service.uploadNotice(body, Usertype_selected, Standard_selected, "0"
                    , "0", fromdate, enddate, Notice_Subject, Notice_Detail, userid, "192.168.1.150", Schooli_id);
            Log.d(TAG, "SubmitASYNC: "+body+"__"+"__"+ Usertype_selected+"__"+ Standard_selected+"__"+"0"+"__"+
                     "0"+ "__"+fromdate+"__"+ enddate+"__"+ Notice_Subject+"__"+ Notice_Detail+"__"+ userid+"__"+ "192.168.1.150"+ "__"+Schooli_id);
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    dialog.show();
                }

                @Override
                public void onNext(ResponseBody body) {
                    try {

                    } catch (Exception ex) {
                        dialog.dismiss();
                        Log.d(TAG, "SubmitASYNC_Exception: "+ex.toString());
                        Toast.makeText(ApplyNotice.this, "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    dialog.dismiss();
                    Log.d(TAG, "onError: "+t.toString());
                    Toast.makeText(ApplyNotice.this, "Response Taking Time,Seems Network issue!"+t.toString(), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onComplete() {
                    dialog.dismiss();
                    Toast.makeText(ApplyNotice.this, "Notice Created Successfully 2", Toast.LENGTH_SHORT).show();
                    Noticeboard noticeBoardTab = new Noticeboard();
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, noticeBoardTab).commitAllowingStateLoss();
                }
            });
        } catch (Exception ex) {
            Toast.makeText(ApplyNotice.this, "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }

    public void SubmitAllASYNC() {
        final ProgressDialog dialog = new ProgressDialog(ApplyNotice.this);
        try {
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Processing...");
            for (int i = 1; i < 6; i++) {
                String UserAll = String.valueOf(i);
                if (UserAll.contentEquals("2")) {
                } else {
                    DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
                    Observable<ResponseBody> call = service.uploadNotice(body, Usertype_selected, Standard_selected, "0"
                            , "0", fromdate, enddate, Notice_Subject, Notice_Detail, userid, "192.168.1.150", Schooli_id);

                    Log.d(TAG, "SubmitAllASYNC: "+body+ Usertype_selected+ Standard_selected+"0"+
                            "0"+ fromdate+ enddate+ Notice_Subject+ Notice_Detail+ userid+ "192.168.1.150"+ Schooli_id);
                    call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(Disposable disposable) {
                            dialog.show();
                        }

                        @Override
                        public void onNext(ResponseBody body) {
                            try {
                            } catch (Exception ex) {
                                dialog.dismiss();
                                Toast.makeText(ApplyNotice.this, "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable t) {
                            dialog.dismiss();
                            Log.d("Tag", "Noticeboard Exception" + t.toString());
                            Toast.makeText(ApplyNotice.this, "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {
                            if (UserAll.contentEquals("5")) {
                                dialog.dismiss();
                                Toast.makeText(ApplyNotice.this, "Notice Created Successfully 3", Toast.LENGTH_SHORT).show();
                                Noticeboard noticeBoardTab = new Noticeboard();
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, noticeBoardTab).commitAllowingStateLoss();
                            }
                        }
                    });
                }
            }
        } catch (Exception ex) {
        }
    }

    public void SubmitAllASYNCByPrincipal1() {
        final ProgressDialog dialog = new ProgressDialog(ApplyNotice.this);
        try {
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Processing...");
            for (int i = 1; i < 6; i++) {
                String UserAll = String.valueOf(i);
                if (UserAll.contentEquals("2")) {
                } else {
                    DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
                    Observable<ResponseBody> call = service.uploadNotice(body, Usertype_selected, Standard_selected, "0"
                            , "0", fromdate, enddate, Notice_Subject, Notice_Detail, userid, "", Schooli_id);
                    Log.d(TAG, "SubmitAllASYNCByPrincipal1: "+body+ Usertype_selected+ Standard_selected+"0"+
                            "0"+ fromdate+ enddate+ Notice_Subject+ Notice_Detail+ userid+ ""+ Schooli_id);

                    call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(Disposable disposable) {
                            dialog.show();
                        }

                        @Override
                        public void onNext(ResponseBody body) {
                            try {
                            } catch (Exception ex) {
                                dialog.dismiss();
                                Toast.makeText(ApplyNotice.this, "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable t) {
                            dialog.dismiss();
                            Toast.makeText(ApplyNotice.this, "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {
                            if (UserAll.contentEquals("5")) {
                                dialog.dismiss();
                                SubmitAllASYNCByPrincipal2();
                            }
                        }
                    });
                }
            }
        } catch (Exception ex) {
        }
    }

    public void SubmitAllASYNCByPrincipal2() {
        final ProgressDialog dialog = new ProgressDialog(ApplyNotice.this);
        try {
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Processing...");
            for (int i = 1; i < 6; i++) {
                String UserAll = String.valueOf(i);
                if (UserAll.contentEquals("2")) {
                } else {
                    DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
                    NoticeboardDetail noticeboardDetail = new NoticeboardDetail(Integer.parseInt(UserAll), Integer.parseInt(Standard_selected), 0, 0, fromdate, enddate, Notice_Subject, Notice_Detail, Integer.parseInt(userid), "", 2,academic_id);
                    Log.d("TAG" , "IAM1");
                    Observable<ResponseBody> call = service.InsertNotice("insert", noticeboardDetail);
                    call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(Disposable disposable) {
                            dialog.show();
                        }

                        @Override
                        public void onNext(ResponseBody body) {
                            try {
                            } catch (Exception ex) {
                                dialog.dismiss();
                                Toast.makeText(ApplyNotice.this, "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable t) {
                            dialog.dismiss();
                            Toast.makeText(ApplyNotice.this, "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {
                            if (UserAll.contentEquals("5")) {
                                dialog.dismiss();
                                Toast.makeText(ApplyNotice.this, "Notice Created Successfully 4", Toast.LENGTH_SHORT).show();
                                Noticeboard noticeBoardTab = new Noticeboard();
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, noticeBoardTab).commitAllowingStateLoss();
                            }
                        }
                    });
                }
            }
        } catch (Exception ex) {
        }
    }

    public void SubmitASYNC1() {
        final ProgressDialog dialog = new ProgressDialog(ApplyNotice.this);
        try {
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Processing...");
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);

            Observable<ResponseBody> call = service.uploadNotice(body, Usertype_selected, Standard_selected, "0"
                    , "0", fromdate, enddate, Notice_Subject, Notice_Detail, userid, "192.168.1.150", "1");

            Log.d(TAG, "SubmitASYNC: "+body+"__"+"__"+ Usertype_selected+"__"+ Standard_selected+"__"+"0"+"__"+
                    "0"+ "__"+fromdate+"__"+ enddate+"__"+ Notice_Subject+"__"+ Notice_Detail+"__"+ userid+"__"+ "192.168.1.150"+ "__"+"1");

            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    dialog.show();
                }

                @Override
                public void onNext(ResponseBody body) {
                    try {
                    } catch (Exception ex) {
                        dialog.dismiss();
                        Toast.makeText(ApplyNotice.this, "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(ApplyNotice.this, "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onComplete() {
                    dialog.dismiss();
                    SubmitASYNC2();
                }
            });
        } catch (Exception ex) {
            Log.d("CMS", this+"line number 862"+ex);
        }
    }

    public void SubmitASYNC2() {
        final ProgressDialog dialog = new ProgressDialog(ApplyNotice.this);
        try {
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Processing...");
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<ResponseBody> call = service.uploadNotice(body, Usertype_selected, Standard_selected, "0"
                    , "0", fromdate, enddate, Notice_Subject, Notice_Detail, userid, "", "2");
            Log.d("TAG" , "IAM2");
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    dialog.show();
                }

                @Override
                public void onNext(ResponseBody body) {
                    try {
                    } catch (Exception ex) {
                        dialog.dismiss();
                        Toast.makeText(ApplyNotice.this, "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(ApplyNotice.this, "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onComplete() {
                    dialog.dismiss();
                    Toast.makeText(ApplyNotice.this, "Notice Created Successfully 1", Toast.LENGTH_SHORT).show();
                    Noticeboard noticeBoardTab = new Noticeboard();
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, noticeBoardTab).commitAllowingStateLoss();
                }
            });
        } catch (Exception ex) {
        }
    }

    private void setSpinnerError(Spinner spinner, String error) {
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError(error); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.

        }
    }
}
