package info.efficacious.centralmodelschool.dialogbox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import info.efficacious.centralmodelschool.Interface.DataService;
import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.activity.EntityNameActivity;
import info.efficacious.centralmodelschool.activity.NewmessegesActivity;
import info.efficacious.centralmodelschool.activity.StudentList;
import info.efficacious.centralmodelschool.adapters.BottomUpAdapter;
import info.efficacious.centralmodelschool.common.ConnectionDetector;
import info.efficacious.centralmodelschool.entity.DashboardDetail;
import info.efficacious.centralmodelschool.entity.DashboardDetailsPojo;
import info.efficacious.centralmodelschool.entity.StandardDetail;
import info.efficacious.centralmodelschool.entity.StandardDetailsPojo;
import info.efficacious.centralmodelschool.entity.StudentStandardwiseDetail;
import info.efficacious.centralmodelschool.entity.StudentStandardwiseDetailPojo;
import info.efficacious.centralmodelschool.webApi.RetrofitInstance;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class BottomLayoutSheetDialog extends BottomSheetDialogFragment implements BottomUpAdapter.onDivisionListener, View.OnClickListener {
    BottomSheetListener mbottomSheetListener;
    RecyclerView mRecyclerView;
    BottomUpAdapter mBottomUpAdapter;
    ArrayList<String> mstandard = new ArrayList<>();
    private static final String TAG = "BottomLayoutSheetDialog";
    private String school_id, mStandardId;
    ConnectionDetector cd;
    SharedPreferences settings;
    TextView mtextview_div;
    Button btn_cancel, btn_done;
    private String mDiv, macademic_id;
    int mdivId;
    private CompositeDisposable mCompositeDisposable;
    private BottomLayoutSheetDialog bottomLayoutSheetDialog;
    ArrayList<String> mstudentName = new ArrayList<>();
    private static final String PREFRENCES_NAME = "myprefrences";
    private String getArgs;
    ArrayList<String> divisions = new ArrayList<>();
    ArrayList<Integer> mDivisionId = new ArrayList<>();
    public static FragmentManager fragmentManager;
    ArrayList<String> mobilenumber = new ArrayList<>();

//        >http://122.170.4.112/CMSWebApi/api/Message/?command=ListOfStudentsStandardWise&intStandard_id=6&intSchool_id=1&intAcademic_id=4

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottomsheetlayout, container, false);
        settings = getContext().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        school_id = settings.getString("TAG_SCHOOL_ID", "");
        macademic_id = settings.getString("TAG_ACADEMIC_ID", "");
        mtextview_div = v.findViewById(R.id.textview_div);
        btn_cancel = v.findViewById(R.id.btndcancel);
        btn_done = v.findViewById(R.id.btnDoneN);
        btn_cancel.setOnClickListener(this);
        btn_done.setOnClickListener(view -> {
            if (getArgs.equals("fromNewMsg")) {
//                Intent intent=new Intent(getContext(),EntityNameListActivity.class);
                Log.e(TAG, "onCreateViewdiv: "+String.valueOf(mdivId) );
                Intent intent = new Intent(getContext(), EntityNameActivity.class);
                intent.putExtra("div", mdivId);
                intent.putExtra("std", mStandardId);
                intent.putExtra("args","frombottom");
                startActivity(intent);
                Standard_wise_sms(mStandardId, String.valueOf(mdivId));
            } else if (getArgs.equals("fromStudentlistFragment")) {
                Toast.makeText(getContext(), "fromStudentlistFragment", Toast.LENGTH_SHORT).show();

            }
        });

        mRecyclerView = v.findViewById(R.id.bottom_up_recycler);
        Bundle mArgs = getArguments();
        if (mArgs.getString("args").equals("fromNewMsg")) {
            mstandard = mArgs.getStringArrayList("key");
            mStandardId = mstandard.get(0);
            getArgs = mArgs.getString("args");
        } else if (mArgs.getString("args").equals("fromStudentlistFragment")) {
            getArgs = mArgs.getString("args");
        }
        mtextview_div.setText("Select Division for class" + mStandardId);
        fragmentManager = getFragmentManager();
        bottomLayoutSheetDialog = new BottomLayoutSheetDialog();
        Log.e(TAG, "onCreateView: " + mstandard);
        divisionCall();

        return v;
    }

    @Override
    public void OnClickDivision(String pos, int id) {
        Log.e(TAG, "OnClickDivision: " + pos + "id" + id);
        mDiv = pos;
        mdivId = id;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btndcancel:
                Intent intent=new Intent(getContext(), NewmessegesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

        }
    }

    private void callStudent() {

        Log.e(TAG, "callStudent: " + school_id + "__" + macademic_id + "__" + mStandardId + "__" + mdivId);
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            mCompositeDisposable.add(service.getStudentStandardWiseDetails("select", school_id, macademic_id, mStandardId, String.valueOf(mdivId))
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
                    Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue !" + ex.toString(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
            }
        };
    }

    private void generateStudentList(ArrayList<StudentStandardwiseDetail> studentStandardwiseDetail) {


        Log.e(TAG, "generateStudentList: " + studentStandardwiseDetail.get(0).toString());
        Toast.makeText(getContext(), "generateStudentList", Toast.LENGTH_SHORT).show();
        for (int i = 0; i < studentStandardwiseDetail.size(); i++) {
            mstudentName.add(studentStandardwiseDetail.get(i).getName());

        }

//        startActivity(new Intent(getActivity(),StudentList.class));
        Intent intent = new Intent(getActivity(), StudentList.class);
        Bundle bundle = new Bundle();
        intent.putStringArrayListExtra("studentsName", mstudentName);
        intent.putStringArrayListExtra("studentdetails", mobilenumber);
//        fragmentManager.beginTransaction().replace(R.id.content_main, new StudentListFragment(studentStandardwiseDetail)).commitAllowingStateLoss();
        startActivity(intent);

        dismiss();


    }

    public void Standard_wise_sms(String std_id, String std_div_id) {
        try {
            String command = "AllMessageToDivision";
            Log.e(TAG, "Standard_wise_sms: " + command + "_" + macademic_id + "-" + std_id + "_" + std_div_id);
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<DashboardDetailsPojo> call = service.getDashboardDetail(command, macademic_id, std_div_id, std_id);
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<DashboardDetailsPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {
//                    progress.show();
                }

                @Override
                public void onNext(DashboardDetailsPojo body) {
                    try {
                        Log.e(TAG, "onNext_Mobilenumber: " + body.getDashboardDetails().get(0).getIntBusAlert1());
//                        generateSMSNo((ArrayList<DashboardDetail>) body.getDashboardDetails());
                        mobilenumber.clear();
                        for (int i = 0; i < body.getDashboardDetails().size(); i++) {
                            mobilenumber.add(body.getDashboardDetails().get(i).getIntBusAlert1());
                        }
                        Log.e(TAG, "onNext_Mobilenumber_arraylist: " + mobilenumber.toString());

                    } catch (Exception ex) {
//                        progress.dismiss();
                        Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
//                    progress.dismiss();
                    Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onComplete() {
//                    progress.dismiss();
                }
            });
        } catch (Exception ex) {

        }
    }

    public void generateSMSNo(ArrayList<DashboardDetail> taskListDataList) {
        try {
//            mobilenumber.clear();
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                for (int i = 0; i <= taskListDataList.size(); i++) {
                    mobilenumber.add(taskListDataList.get(i).getIntBusAlert1());
                }
                Log.e(TAG, "generateSMSNo: " + mobilenumber.get(0));
                Toast.makeText(getActivity(), "Total Phone No Count:" + taskListDataList.size(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Total Phone No Count: 0", Toast.LENGTH_LONG).show();
            }

        } catch (Exception ex) {
//            progress.dismiss();
            Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }


    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }


    private void divisionCall() {
        if (mStandardId != null) {
            try {
                DataService service1 = RetrofitInstance.getRetrofitInstance().create(DataService.class);
                mCompositeDisposable.add(service1.getStandardDetails("GetDivision", school_id, "", mStandardId, "", "", "")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(getObserverdiv()));
            } catch (Exception e) {
                Log.e(TAG, "divisionCall: " + e.toString());
            }
        }
    }

    public DisposableObserver<StandardDetailsPojo> getObserverdiv() {
        return new DisposableObserver<StandardDetailsPojo>() {

            @Override
            public void onNext(@NonNull StandardDetailsPojo dashboardDetailsPojo) {
                try {
                    Log.e(TAG, "onNext: " + dashboardDetailsPojo.getStandardDetails().toString());
                    generateDivisionByLectures((ArrayList<StandardDetail>) dashboardDetailsPojo.getStandardDetails());
                } catch (Exception ex) {

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(getContext(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    private void generateDivisionByLectures(ArrayList<StandardDetail> standardDetails) {
        divisions.clear();
        for (int i = 0; i < standardDetails.size(); i++) {
            divisions.add(standardDetails.get(i).getVchDivisionName());
            mDivisionId.add(standardDetails.get(i).getIntDivisionId());
        }
        mBottomUpAdapter = new BottomUpAdapter(mDivisionId, divisions,mStandardId, getContext(), this);
        StaggeredGridLayoutManager gridlayoutmanager = new StaggeredGridLayoutManager(4, LinearLayoutManager.VERTICAL);

//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(gridlayoutmanager);
        mRecyclerView.setAdapter(mBottomUpAdapter);
        Log.e(TAG, "generateDivisionByLectures: " + divisions.toString());


    }
}
