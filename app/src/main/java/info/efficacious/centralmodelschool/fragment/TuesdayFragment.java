package info.efficacious.centralmodelschool.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import info.efficacious.centralmodelschool.Interface.DataService;
import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.Tab.TimetableActivity_student;
import info.efficacious.centralmodelschool.adapters.StandardAdapter;
import info.efficacious.centralmodelschool.adapters.StudentTimetableAdapter;

import info.efficacious.centralmodelschool.adapters.Student_division_adapter;
import info.efficacious.centralmodelschool.entity.TimeTableDetail;
import info.efficacious.centralmodelschool.entity.TimeTableDetailPojo;
import info.efficacious.centralmodelschool.webApi.RetrofitInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class TuesdayFragment extends Fragment {
    String Std_id, school_id,div_id;
    RecyclerView mrecyclerView;
    RecyclerView.Adapter madapter;
    private CompositeDisposable mCompositeDisposable;
    private ProgressDialog progress;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String Academic_id, role_id;

    public TuesdayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tuesday, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mrecyclerView = (RecyclerView) getActivity().findViewById(R.id.tuesday_list);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        Academic_id = settings.getString("TAG_ACADEMIC_ID", "");
        role_id = settings.getString("TAG_USERTYPEID", "");
        if (role_id.contentEquals("5")||role_id.contentEquals("6"))
            div_id= Student_division_adapter.div;
        else
            div_id=settings.getString("TAG_DIVISIONID","");
        try
        {
            if(role_id.contentEquals("6")||role_id.contentEquals("7")||role_id.contentEquals("3"))
            {
                school_id= StandardAdapter.intSchool_id;
            }else
            {
                school_id= settings.getString("TAG_SCHOOL_ID", "");
            }
//            Std_id= TimetableActivity_student.stdid;
            try {
                if (role_id.contentEquals("5")||role_id.contentEquals("6"))
                    Std_id = Student_division_adapter.std_id;
                else
                    Std_id = TimetableActivity_student.stdid;

            } catch (Exception ex) {

            }
        }catch (Exception ex)
        {

        }
        /*try {
            Std_id = TimetableActivity_student.stdid;
        } catch (Exception ex) {

        }*/

        try {
            tueAsync ();
        } catch (Exception ex) {

        }

    }

    public void  tueAsync (){
        try {
            Log.d("TAG","tueAsync"+school_id+"__"+Academic_id+"__"+Std_id+"__"+div_id);
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            mCompositeDisposable.add(service.getTimeTableDetails("selectStudentTT",school_id,"tuesday",Academic_id,Std_id,"",div_id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(getObserver()));
        } catch (Exception ex) {
        }
    }
    public DisposableObserver<TimeTableDetailPojo> getObserver(){
        return new DisposableObserver<TimeTableDetailPojo>() {

            @Override
            public void onNext(@NonNull TimeTableDetailPojo timeTableDetailPojo) {
                try {
                    generateTimetableList((ArrayList<TimeTableDetail>) timeTableDetailPojo.getTimeTableDetail());
                } catch (Exception ex) {
                    Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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


    public void generateTimetableList(ArrayList<TimeTableDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                madapter = new StudentTimetableAdapter(getActivity(),taskListDataList,"Student");

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

                mrecyclerView.setLayoutManager(layoutManager);

                mrecyclerView.setAdapter(madapter);
            } else {

            }

        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
        }
    }

}