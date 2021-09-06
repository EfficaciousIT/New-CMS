package info.efficacious.centralmodelschool.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import info.efficacious.centralmodelschool.Interface.DataService;
import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.activity.MainActivity;
import info.efficacious.centralmodelschool.activity.NewmessegesActivity;
import info.efficacious.centralmodelschool.adapters.CustomAdapter;
import info.efficacious.centralmodelschool.entity.DashboardDetail;
import info.efficacious.centralmodelschool.entity.DashboardDetailsPojo;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class EntityNameList extends Fragment {

//    ArrayList<String> sms_phone_no_array = new ArrayList<String>();
//    String academic_id,Schooli_id;
//    SharedPreferences settings;
//    private static final String PREFRENCES_NAME = "myprefrences";
//    List<TeacherDetail> teacherDetails=new ArrayList<>();
//    List<DashboardDetail> staffDetail=new ArrayList<>();
//    private static final String TAG = "EntityNameList";
//    private CompositeDisposable mCompositeDisposable;
//    ListView listView;
//    Button btn_cancel,btn_done;
//    ArrayList<TeacherDetail> mTeacherDetails=new ArrayList<>();
//    int preSelectedIndex = -1;
//    String args ;
//    boolean flag = false;
//    SendMessage SM;
//    public ArrayList<String> mobile=new ArrayList<>();
//    public EntityNameList() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view=inflater.inflate(R.layout.fragment_teacher_name_list, container, false);
//        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
//        academic_id = settings.getString("TAG_ACADEMIC_ID", "");
//        Schooli_id = settings.getString("TAG_SCHOOL_ID", "");
//
//        listView= (ListView) view.findViewById(R.id.listview);
//        btn_cancel=view.findViewById(R.id.btn_cancel);
//        btn_done=view.findViewById(R.id.btn_doneselectedTeacher);
//        FragmentManager mfragment = getFragmentManager();
//        Bundle bundle=getArguments();
//        args=bundle.getString("args");
//        if (args!=null)
//        {
//            if ( args.equals("fromStaff"))
//            {
//                usertype_staff("AllStaffByPrincipal");
//            }else if (args.equals("fromTeacher")){
//                usertype_selected("select");
//            }
//        }
////        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("TeacherName");
//        btn_done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SM.sendData(mobile);
//                Bundle bundle=new Bundle();
//                bundle.putStringArrayList("mobile",mobile);
//                bundle.putString("key","fromTeacherNameList");
//                Intent intent=new Intent(getContext(),NewmessegesActivity.class);
//                startActivity(intent,bundle);
//
//                Log.e(TAG, "onClick: "+mobile.toString() );
//                        getFragmentManager().popBackStack();
//
//            }
//        });
//        return view;
//
//    }
//    interface SendMessage {
//        void sendData(ArrayList<String> message);
//    }
//    @Override
//    public void onStop() {
//        super.onStop();
//    }
//
//    public void usertype_selected(String allSMSAdmin) {
//
//        try {
//            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
//            if (allSMSAdmin.equals("select")){
//                try {
//                    mCompositeDisposable.add(service.getTeacherDetails(allSMSAdmin,Schooli_id)
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribeOn(Schedulers.io())
//                            .subscribeWith(getObserver()));
//                } catch (Exception ex) {
//                }
//            }else if (allSMSAdmin.equals("AllStaff")){
//                try {
//                    usertype_staff(allSMSAdmin);
//                } catch (Exception ex) {
//                }
//            }
//
//        } catch (Exception ex) {
//
//        }
//    }
//    public void usertype_staff(String allSMSAdmin) {
//        try {
//            String command = allSMSAdmin;
//            Log.e(TAG, "usertype_staff: "+command+"_"+academic_id+"_"+Schooli_id );
//            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
//
//            Observable<DashboardDetailsPojo> call = service.getDashboardDetails(command, academic_id, Schooli_id);
//
//            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<DashboardDetailsPojo>() {
//                @Override
//                public void onSubscribe(Disposable disposable) {
////                    progress.show();
//                }
//
//                @Override
//                public void onNext(DashboardDetailsPojo body) {
//                    try {
//                        Log.e(TAG, "onNext: "+body.getDashboardDetails() );
//                        generateSMS((ArrayList<DashboardDetail>) body.getDashboardDetails());
//                    } catch (Exception ex) {
////                        progress.dismiss();
//                        Log.e(TAG, "onNext: "+ex.toString() );
//                        Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue in staff ex!"+ex.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onError(Throwable t) {
////                    progress.dismiss();
//                    Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue in staff thowable!"+t.toString(), Toast.LENGTH_SHORT).show();
//
//                }
//
//                @Override
//                public void onComplete() {
////                    progress.dismiss();
//                }
//            });
//        } catch (Exception ex) {
//
//        }
//    }
//
//    private void generateSMS(ArrayList<DashboardDetail> dashboardDetails) {
//        Log.e(TAG, "generateStaffList: "+dashboardDetails.toString() );
//        staffDetail.addAll(dashboardDetails);
//        Log.e(TAG, "staffDetail: "+staffDetail.toString() );
//        final CustomAdapter customAdapter=new CustomAdapter(getContext(),null,staffDetail);
//        listView.setAdapter(customAdapter);
//        listView.setOnItemClickListener((adapterView, view, i, l) -> {
//            DashboardDetail model = staffDetail.get(i);
//            if (model.isSelected()) {
//                model.setSelected(false);
//                mobile.remove(i);
//            }
//            else
//                model.setSelected(true);
//            Log.e(TAG, "generateTeacherList_afetrClick: "+mTeacherDetails.toString());
//            mobile.add(staffDetail.get(i).getIntmobileno());
//            Log.e(TAG, "generateTeacherList_mobile: "+mobile.toString() );
//            staffDetail.set(i, model);
//
//            //now update adapter
//            customAdapter.updateRecords(null,staffDetail);
//        });
//    }
//
//    public DisposableObserver<TeacherDetailPojo> getObserver(){
//        return new DisposableObserver<TeacherDetailPojo>() {
//
//            @Override
//            public void onNext(@NonNull TeacherDetailPojo teacherDetailPojo) {
//                try {
//                    generateTeacherList((ArrayList<TeacherDetail>) teacherDetailPojo.getTeacherDetail());
//                } catch (Exception ex) {
//                    Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onComplete() {
//            }
//        };
//    }
//
//    private void generateTeacherList(ArrayList<TeacherDetail> teacherDetail) {
//
//        Log.e(TAG, "generateTeacherList: "+teacherDetail.toString() );
//        teacherDetails.addAll(teacherDetail);
//        final CustomAdapter customAdapter=new CustomAdapter(this,teacherDetails,null);
//        listView.setAdapter(customAdapter);
//        listView.setOnItemClickListener((adapterView, view, i, l) -> {
//            TeacherDetail model = teacherDetails.get(i);
//            if (model.isSelected()) {
//                model.setSelected(false);
////                mobile.remove(i);
//            }
//            else
//                model.setSelected(true);
//
//            Log.e(TAG, "generateTeacherList_afetrClick: "+mTeacherDetails.toString());
//
//
//            mobile.add(teacherDetails.get(i).getIntMobileNo());
//            Log.e(TAG, "generateTeacherList_mobile: "+mobile.toString() );
//            teacherDetails.set(i, model);
//            //now update adapter
//            customAdapter.updateRecords(teacherDetails,null);
//        });
//    }
//


//    private class sendsms extends AsyncTask<Void, Void, Void> {
//        private final ProgressDialog dialog = new ProgressDialog(getActivity());
//        String message = "";
//        int SMS_SendCount = 0;
//        int Total_Count = 0;
//
//        public sendsms(String sms) {
//            message = sms;
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            try {
//                Total_Count = mobile.size();
//                HttpURLConnection uc = null;
//                String requestUrl = "";
//                for (int i = 0; i < mobile.size(); i++) {
////                    requestUrl = ("http://alerts.justnsms.com/api/web2sms.php?workingkey=A2cabcee227fa491ee050155a13485498&sender=CMSBKP&to=" + URLEncoder.encode(sms_phone_no_array.get(i), "UTF-8") + "&message=" + URLEncoder.encode(message, "UTF-8") + "&format=json&custom=1,2&flash=0&unicode=1");
//                    requestUrl = ("http://www.smsjust.com/sms/user/urlsms.php?username=Central Model school &pass=$b5@ZtX3&senderid=CMSBKP&dest_mobileno=" + URLEncoder.encode(mobile.get(i), "UTF-8") + "&msgtype=UNI&message=" + URLEncoder.encode(message, "UTF-8") + "&response=Y");
//                    URL url = new URL(requestUrl);
//                    Log.d("TAG","multiurl"+url);
//                    uc = (HttpURLConnection) url.openConnection();
//                    System.out.println(uc.getResponseMessage());
//                    String response = uc.getResponseMessage();
//                    if (response.contentEquals("OK")) {
//                        SMS_SendCount = SMS_SendCount + 1;
//                    }
//                    uc.disconnect();
//                }
//
//
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
//
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            dialog.dismiss();
//            try {
//                sms_phone_no_array.clear();
//                flag = true;
//                Toast.makeText(getActivity(), "SMS Send Successfully", Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
//                alert.setMessage(SMS_SendCount + " SMS Sent Successfully Out of " + Total_Count);
//                alert.setPositiveButton("OK", null);
////                    alert.show();
////                    Sms_Fragment sms_fragment = new Sms_Fragment();
////                    MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, sms_fragment).commitAllowingStateLoss();
//                MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, new Admin_Dashboard()).commitAllowingStateLoss();
//
//            } catch (Exception ex) {
//
//            }
//        }
//    }


}
