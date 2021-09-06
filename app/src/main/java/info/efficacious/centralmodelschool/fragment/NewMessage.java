package info.efficacious.centralmodelschool.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import info.efficacious.centralmodelschool.Interface.DataService;
import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.activity.MainActivity;
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

/**
 * A simple {@link Fragment} subclass.
 */
//public class NewMessage extends Fragment
//         {
//    View view;
//    private static final String PREFRENCES_NAME = "myprefrences";
//    Button btn_all, btn_student, btn_teacher, btn_staff, btnDone;
//    String school_id;
//    RecyclerView standard_rv, standardgrid;
//    ConnectionDetector cd;
//    SharedPreferences settings;
//    private static final String TAG = "NewMessage";
//    private CompositeDisposable mCompositeDisposable;
//    int numberOfColumns = 6;
//    LinearLayout standardslayout;
//    String standard;
//    ArrayList<StandardDetail> mstandardDetails = new ArrayList<>();
//    ArrayList<String> mstandard_id = new ArrayList<>();
//    ArrayList<String> mobilenumber=new ArrayList<>() ;
//    ArrayList<String> mteacher_mobile = new ArrayList<>();
//    ArrayList<String> studentname = new ArrayList<>();
//    TextView tv_selected_count;
//    EditText edt_msg;
//    String msgData;
//    Button btnSendSms;
//    Bundle bundle=new Bundle();
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
////        Bundle args = getArguments();
////        if (args != null) {
////            if (args.getString("key").equals("fromTeacherNameList")) {
////                mobilenumber= new ArrayList<>();
//////                mobilenumber = getArguments().getStringArrayList("mobile");
////                mobilenumber=(args.getStringArrayList("mobile"));
////                mteacher_mobile=mobilenumber;
////                Log.e(TAG, "onCreateView_teacher: " + mobilenumber.toString());
//////                tv_selected_count.setVisibility(View.VISIBLE);
//////                tv_selected_count.setText(mobilenumber.size() + "selected");
//////                tv_selected_count.setMovementMethod(LinkMovementMethod.getInstance());
////            } else {
////                tv_selected_count.setVisibility(View.GONE);
////                mobilenumber = args.getStringArrayList("mobile");
////                studentname =args.getStringArrayList("mstudent");
//////                tv_selected_count.setText(mobilenumber.size() + "selected");
//////                tv_selected_count.setMovementMethod(LinkMovementMethod.getInstance());
////                Log.e(TAG, "onCreateView: " + mobilenumber.toString() + "__" + studentname.toString());
////            }
////        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        view = inflater.inflate(R.layout.fragment_new_message, container, false);
//        btn_all = view.findViewById(R.id.btn_all);
//        btn_student = view.findViewById(R.id.btn_student);
//        btn_teacher = view.findViewById(R.id.btn_teacher);
//        btn_staff = view.findViewById(R.id.btn_staff);
//        btnDone = view.findViewById(R.id.btnDone);
//        edt_msg = view.findViewById(R.id.edt_msg);
//        edt_msg.requestFocus();
//        btnSendSms = view.findViewById(R.id.send_msg);
//        tv_selected_count = view.findViewById(R.id.selected_count);
//        cd = new ConnectionDetector(getActivity().getApplicationContext());
//        standardslayout = view.findViewById(R.id.standardslayout);
//        standardslayout.setVisibility(View.GONE);
////        standard_rv=view.findViewById(R.id.standard_rv);
//        btn_all.setOnClickListener(this);
//        btn_student.setOnClickListener(this);
//        btn_teacher.setOnClickListener(this);
//        btn_staff.setOnClickListener(this);
//        btnDone.setOnClickListener(this);
//        btnSendSms.setOnClickListener( this);
//        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
//        school_id = settings.getString("TAG_SCHOOL_ID", "");
//        standardgrid = view.findViewById(R.id.standardgrid);
////        standardgrid.setVisibility(View.GONE);
//        if (!cd.isConnectingToInternet()) {
//            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
//            alert.setMessage("No Internet Connection");
//            alert.setPositiveButton("OK", null);
//            alert.show();
//
//        } else {
//            try {
//                standardCall();
//            } catch (Exception ex) {
//
//            }
//        }
//        return view;
//    }
//
//    private void standardCall() {
//        DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
//        mCompositeDisposable.add(service.getStandardDetails("select", school_id, "", "", "", "", "")
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribeWith(getObserver()));
//    }
//
//    public DisposableObserver<StandardDetailsPojo> getObserver() {
//        return new DisposableObserver<StandardDetailsPojo>() {
//
//            @Override
//            public void onNext(@NonNull StandardDetailsPojo dashboardDetailsPojo) {
//                try {
//                    mstandardDetails = (ArrayList<StandardDetail>) dashboardDetailsPojo.getStandardDetails();
//                    generateStandradByLectures((ArrayList<StandardDetail>) dashboardDetailsPojo.getStandardDetails());
//                } catch (Exception ex) {
//                }
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };
//    }
//
//    @Override
//    public void onStart() {
//
//        super.onStart();
//
//        Bundle bundle = this.getArguments();
//
//        if (bundle != null) {
//            Log.e(TAG, "onStart: " + bundle.getString("key"));
//            // handle your code here.
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
//            mCompositeDisposable.dispose();
//        }
//    }
//
//    private void generateStandradByLectures(ArrayList<StandardDetail> standardDetails) {
//        Log.e("TAG", "Standard" + standardDetails.get(0).getVchStandardName());
//
//        if (standardDetails != null || !standardDetails.isEmpty()) {
//            StandardGridAdapter standardGridAdapter = new StandardGridAdapter(standardDetails, getContext(), this);
////            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
////            RecyclerView.LayoutManager gridlayoutmanager=new GridLayoutManager(getActivity(),numberOfColumns);
//            StaggeredGridLayoutManager gridlayoutmanager = new StaggeredGridLayoutManager(numberOfColumns, LinearLayoutManager.VERTICAL);
//            standardgrid.setLayoutManager(gridlayoutmanager);
//            standardgrid.setAdapter(standardGridAdapter);
//        }
//    }
//
//    @Override
//    public void onStandardClickdata(String data) {
//        int position = Integer.parseInt(data);
//        mstandard_id.clear();
//        mstandard_id.add(String.valueOf(mstandardDetails.get(position).getIntStandardId()));
//        Log.e(TAG, "mstandard_id: " + mstandard_id);
//    }
//
//    @Override
//    public void onStandardClick(int position) {
//
//
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btn_all:
//                standardslayout.setVisibility(View.GONE);
//                break;
//            case R.id.btn_student:
//                boolean selected = !view.isSelected();
//                view.setSelected(selected);
//                view.setBackgroundColor(selected ? Color.GREEN : Color.TRANSPARENT);
//                Log.e(TAG, "btn_student_onClick: ");
//                standardslayout.setVisibility(View.VISIBLE);
//                btnDone.setVisibility(View.VISIBLE);
//                break;
//            case R.id.btn_teacher:
//                EntityNameList entityTeacherNameList = new EntityNameList();
//                FragmentManager manager = getFragmentManager();
//                bundle.putString("args","fromTeacher");
//                entityTeacherNameList.setArguments(bundle);
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.replace(R.id.content_main, entityTeacherNameList);
//                transaction.addToBackStack(null);
//                transaction.commit();
//                break;
//            case R.id.btn_staff:
//                EntityNameList entityStaffNameList = new EntityNameList();
//                bundle.putString("args","fromStaff");
//                entityStaffNameList.setArguments(bundle);
//                FragmentManager staffmanager = getFragmentManager();
//                FragmentTransaction stafftransaction = staffmanager.beginTransaction();
//                stafftransaction.replace(R.id.content_main, entityStaffNameList);
//                stafftransaction.addToBackStack(null);
//                stafftransaction.commit();
//                break;
//            case R.id.btnDone:
//
//                Toast.makeText(getContext(), "You_select" + mstandard_id, Toast.LENGTH_SHORT).show();
//                Bundle bundle = new Bundle();
//                bundle.putStringArrayList("key", mstandard_id);
//                bundle.putString("args", "fromNewMsg");
//                BottomLayoutSheetDialog bottomLayoutSheetDialog = new BottomLayoutSheetDialog();
//                bottomLayoutSheetDialog.setArguments(bundle);
//                bottomLayoutSheetDialog.show(getFragmentManager(), "exampleBottomSheet");
//
//                standardgrid.setVisibility(View.GONE);
//                btnDone.setVisibility(View.GONE);
//                break;
//            case R.id.send_msg:
//                msgData = edt_msg.getText().toString();
//                Log.e(TAG, "onCreateView_btnSendSms: "+mobilenumber.toString() );
//                multiple multiple=new multiple(msgData,mobilenumber);
//                multiple.execute();
//                tv_selected_count.setText("");
//                break;
//        }
//    }
//    @Override
//    public void onButtonClicked(String text) {
//        Log.e(TAG, "onButtonClicked: " + text);
//    }
//
//    @Override
//    public void sendData(ArrayList<String> message) {
//        Log.d(TAG, "sendData: "+message.toString());
//        mobilenumber=message;
//    }
//
//    private class multiple extends AsyncTask<Void, Void, Void> {
//        private final ProgressDialog dialog = new ProgressDialog(getActivity());
//        String message = "";
//        int SMS_SendCount = 0;
//        int Total_Count = 0;
//        ArrayList<String> mmobile_number=new ArrayList<>();
//        public multiple(String sms,ArrayList<String> mobile_number) {
//            message = sms;
//            mmobile_number=mobile_number;
//        }
//
//        protected Void doInBackground(Void... voids) {
//            try {
//                Total_Count = mmobile_number.size();
//                HttpURLConnection uc = null;
//                String requestUrl = "";
//                for (int i = 0; i < mmobile_number.size(); i++) {
//                    Log.d("TAG","multiple"+mmobile_number.get(i)+"__"+message);
////                    requestUrl = ("http://alerts.justnsms.com/api/web2sms.php?workingkey=A2cabcee227fa491ee050155a13485498&sender=CMSBKP&to=" + URLEncoder.encode(phone_no_list.get(i), "UTF-8") + "&message=" + URLEncoder.encode(message, "UTF-8") + "&format=json&custom=1,2&flash=0&unicode=1");
//                    requestUrl = ("http://www.smsjust.com/sms/user/urlsms.php?username=Central Model school &pass=$b5@ZtX3&senderid=CMSBKP&dest_mobileno=" + URLEncoder.encode(mmobile_number.get(i), "UTF-8") + "&msgtype=UNI&message=" + URLEncoder.encode(message, "UTF-8") + "&response=Y");
////                    POST("http://www.smsjust.com/sms/user/urlsms.php?username=Central Model school &pass=$b5@ZtX3&senderid=CMSBKP&dest_mobileno=" + strMobileNo + "&msgtype=TXT&message=" + txtNotice.Text.Trim() + "&response=Y", "");
//
//                    URL url = new URL(requestUrl);
//                    Log.d("TAG","url"+url);
//                    uc = (HttpURLConnection) url.openConnection();
//                    String responseMessage = uc.getResponseMessage();
//                    Log.d("TAG", "responseMessage" + responseMessage);
//                    if (responseMessage.contentEquals("OK")) {
//                        SMS_SendCount = SMS_SendCount + 1;
//                    }
//                    uc.disconnect();
//                }
//            } catch (Exception ex) {
//                System.out.println(ex.getMessage());
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dialog.setCancelable(false);
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.setMessage("Sending SMS...");
//            dialog.show();
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            dialog.dismiss();
//            try {
////                phone_no.setText("");
////                sms_box3.setText("");
////                flag = true;
//                Toast.makeText(getActivity(), "SMS Send Successfully", Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
//                alert.setMessage(SMS_SendCount + " SMS Sent Successfully Out of " + Total_Count);
//                alert.setPositiveButton("OK", null);
//                alert.show();
//
//            } catch (Exception ex) {
//            }
//        }
//    }
//
////    private class sendsms extends AsyncTask<Void, Void, Void> {
////        private final ProgressDialog dialog = new ProgressDialog(getActivity());
////        String message = "";
////        int SMS_SendCount = 0;
////        int Total_Count = 0;
////
////        public sendsms(String sms) {
////            message = sms;
////        }
////        @Override
////        protected Void doInBackground(Void... voids) {
////            try {
////                Total_Count = mobilenumber.size();
////                HttpURLConnection uc = null;
////                Log.e(TAG, "doInBackground: "+Total_Count  );
////                String requestUrl = "";
////                for (int i = 0; i < mobilenumber.size(); i++) {
//////                    requestUrl = ("http://alerts.justnsms.com/api/web2sms.php?workingkey=A2cabcee227fa491ee050155a13485498&sender=CMSBKP&to=" + URLEncoder.encode(sms_phone_no_array.get(i), "UTF-8") + "&message=" + URLEncoder.encode(message, "UTF-8") + "&format=json&custom=1,2&flash=0&unicode=1");
////                    requestUrl = ("http://www.smsjust.com/sms/user/urlsms.php?username=Central Model school &pass=$b5@ZtX3&senderid=CMSBKP&dest_mobileno=" + URLEncoder.encode(mobilenumber.get(i), "UTF-8") + "&msgtype=UNI&message=" + URLEncoder.encode(message, "UTF-8") + "&response=Y");
////                    URL url = new URL(requestUrl);
////                    Log.d("TAG","multiurl"+url);
////                    uc = (HttpURLConnection) url.openConnection();
////                    System.out.println(uc.getResponseMessage());
////                    String response = uc.getResponseMessage();
////                    if (response.contentEquals("OK")) {
////                        SMS_SendCount = SMS_SendCount + 1;
//////                        Toast.makeText(getActivity(), "SMS Send Successfully", Toast.LENGTH_SHORT).show();
////                    }
////                    uc.disconnect();
////                }
////
////
////            } catch (Exception ex) {
////                System.out.println(ex.getMessage());
////            }
////            return null;
////        }
////
////        @Override
////        protected void onPreExecute() {
////            super.onPreExecute();
////            dialog.setCancelable(false);
////            dialog.setCanceledOnTouchOutside(false);
////            dialog.setMessage("Sending SMS...");
////            dialog.show();
////
////        }
////
////        @Override
////        protected void onPostExecute(Void aVoid) {
////            super.onPostExecute(aVoid);
////            dialog.dismiss();
////            try {
////               /* sms_phone_no_array.clear();
////                flag = true;*/
////                edt_msg.setText("");
////                Toast.makeText(getActivity(), "SMS Send Successfully", Toast.LENGTH_SHORT).show();
////                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
////                alert.setMessage(SMS_SendCount + " SMS Sent Successfully Out of " + Total_Count);
////                alert.setPositiveButton("OK", null);
//////                    alert.show();
//////                    Sms_Fragment sms_fragment = new Sms_Fragment();
//////                    MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, sms_fragment).commitAllowingStateLoss();
//////                MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, new Admin_Dashboard()).commitAllowingStateLoss();
////
////            } catch (Exception ex) {
////
////            }
////        }
////    }
//
//}
