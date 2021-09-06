package info.efficacious.centralmodelschool.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import info.efficacious.centralmodelschool.Interface.DataService;
import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.activity.MainActivity;
import info.efficacious.centralmodelschool.adapters.SubjectAdapter;

import info.efficacious.centralmodelschool.common.ConnectionDetector;

import info.efficacious.centralmodelschool.entity.SyllabusDetail;
import info.efficacious.centralmodelschool.entity.SyllabusDetailPojo;
import info.efficacious.centralmodelschool.webApi.RetrofitInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class SyllabusDetailFragment extends Fragment {

    View myview;
    RecyclerView recyclerView;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String Standard_id, academic_id;
    Context mContext;
    String subject_id = "", stand_id;
    String standard_id1, Schooli_id, role_id;
    ConnectionDetector cd;
    RecyclerView.Adapter madapter;
    private CompositeDisposable mCompositeDisposable;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.activity_syllabusdetails, null);
        cd = new ConnectionDetector(getActivity().getApplicationContext());
        mContext = getActivity();

        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        role_id = settings.getString("TAG_USERTYPEID", "");
        Schooli_id = settings.getString("TAG_SCHOOL_ID", "");
        Standard_id = settings.getString("TAG_STANDERDID", "");
        academic_id = settings.getString("TAG_ACADEMIC_ID", "");
      //  standard_id1 = StudentSyllabusFragment.stand_id;
        try {
            standard_id1=getArguments().getString("stand_id");
            subject_id = getArguments().getString("sub_id");
        } catch (Exception ex) {

        }
        recyclerView = (RecyclerView) myview.findViewById(R.id.syllabusdetails__list);
        if (!cd.isConnectingToInternet()) {

            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setMessage("No InternetConnection");
            alert.setPositiveButton("OK", null);
            alert.show();

        } else {
            try {

                LoginAsync ();
            } catch (Exception ex) {

            }

        }
        myview.setFocusableInTouchMode(true);
        myview.requestFocus();
        myview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        try {
                            if (role_id.contentEquals("1") || role_id.contentEquals("2")) {
                                stand_id = settings.getString("TAG_STANDERDID", "");
                                StudentSyllabusFragment studentSyllabusFragment = new StudentSyllabusFragment();
                                Bundle args = new Bundle();
                                args.putString("std_id", stand_id);
                                studentSyllabusFragment.setArguments(args);
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, studentSyllabusFragment).commitAllowingStateLoss();
                            } else {
                                stand_id = standard_id1;
                                StudentSyllabusFragment studentSyllabusFragment = new StudentSyllabusFragment();
                                Bundle args = new Bundle();
                                args.putString("std_id", stand_id);
                                studentSyllabusFragment.setArguments(args);
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, studentSyllabusFragment).commitAllowingStateLoss();
                            }
                        } catch (Exception ex) {

                        }

                        return true;
                    }
                }
                return false;
            }
        });
        return myview;
    }
    public void  LoginAsync (){
        try {
            String command;
            if (role_id.contentEquals("6") || role_id.contentEquals("7")) {
                command="FillGridByPrincipal";
            } else {
                command= "FillGrid";
            }
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            mCompositeDisposable.add(service.getSyllabusDetails(command,Schooli_id,subject_id,standard_id1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(getObserver()));
        } catch (Exception ex) {
        }
    }
    public DisposableObserver<SyllabusDetailPojo> getObserver(){
        return new DisposableObserver<SyllabusDetailPojo>() {

            @Override
            public void onNext(@NonNull SyllabusDetailPojo syllabusDetailPojo) {
                try {
                    generateSyllabusList((ArrayList<SyllabusDetail>) syllabusDetailPojo.getSyllabusDetail());
                } catch (Exception ex) {
                    Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    public void generateSyllabusList(ArrayList<SyllabusDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                madapter = new SubjectAdapter(getActivity(),taskListDataList,"SyllabusDetail");
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(madapter);
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
            Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }

}
