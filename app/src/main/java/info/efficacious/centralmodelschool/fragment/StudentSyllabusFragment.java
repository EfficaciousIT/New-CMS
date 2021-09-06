package info.efficacious.centralmodelschool.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

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


public class StudentSyllabusFragment extends Fragment {
    View myview;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    private GoogleApiClient client;
    String Standard_id, role_id;
    Context mContext;
    ArrayAdapter adapter;
    ListView listview;
    String stand_id;
    ConnectionDetector cd;
    RecyclerView recyclerView;
    RecyclerView.Adapter madapter;
    private CompositeDisposable mCompositeDisposable;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.fragment_leavelist, null);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        cd = new ConnectionDetector(getActivity().getApplicationContext());

        mContext = getActivity();
        try {

            stand_id = getArguments().getString("std_id");
        } catch (Exception ex) {

        }
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        role_id = settings.getString("TAG_USERTYPEID", "");
        Standard_id = settings.getString("TAG_STANDERDID", "");
        recyclerView = (RecyclerView) myview.findViewById(R.id.leavelist_list);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        if (!cd.isConnectingToInternet()) {

            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setMessage("No Internet Connection");
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
        myview.setOnKeyListener((v, keyCode, event) -> {
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
                            Student_Std_Fragment student_std_activity = new Student_Std_Fragment();
                            Bundle args = new Bundle();
                            args.putString("pagename", "Syllabus");
                            student_std_activity.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();

                        }
                    } catch (Exception ex) {

                    }


                    return true;
                }
            }
            return false;
        });
        return myview;
    }
    public void  LoginAsync (){
        try {

            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            mCompositeDisposable.add(service.getSyllabusDetails("FillSubject","","",stand_id)
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
                madapter = new SubjectAdapter(getActivity(),taskListDataList,"SubjectName");
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