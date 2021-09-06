package info.efficacious.centralmodelschool.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import info.efficacious.centralmodelschool.Interface.DataService;
import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.adapters.StandardOnlineDetailAdapter;
import info.efficacious.centralmodelschool.adapters.StandardOnlineTimeTableAdapter;
import info.efficacious.centralmodelschool.entity.StandardDetail;
import info.efficacious.centralmodelschool.entity.StandardDetailsPojo;
import info.efficacious.centralmodelschool.webApi.RetrofitInstance;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class OnlineTimeDetailStandard extends Fragment {
    View myview;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String Standard_id;
    Context mContext;
    String value, role_id, userid, academic_id;
    String school_id;
    private CompositeDisposable mCompositeDisposable;
    StandardOnlineDetailAdapter standardAdapter;
    RecyclerView recyclerView;
    RecyclerView.Adapter madapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.activity_syllabus, null);
        mContext = getContext();
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        Standard_id = settings.getString("TAG_STANDERDID", "");
        school_id = settings.getString("TAG_SCHOOL_ID", "");
        recyclerView = (RecyclerView) myview.findViewById(R.id.standard_Syallbus);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        role_id = settings.getString("TAG_USERTYPEID", "");
        academic_id = settings.getString("TAG_ACADEMIC_ID", "");
        try {
            Log.d("RESULT123",role_id);
//            if (role_id.contentEquals("3")) {
//                userid = settings.getString("TAG_USERID", "");
//                StudenStandardtAsync();
//            } else {
                LoginAsync ();
          //  }
        } catch (Exception ex) {

        }
        return myview;
    }


    public void StudenStandardtAsync () {
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            mCompositeDisposable.add(service.getStandardDetails("selectStandradByLectures",school_id,academic_id,"","",userid,"")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(getObserver()));
        } catch (Exception ex) {
        }
    }
    public DisposableObserver<StandardDetailsPojo> getObserver(){
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


    public void generateStandradByLectures(ArrayList<StandardDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                standardAdapter = new StandardOnlineDetailAdapter(taskListDataList, getActivity(),value);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(standardAdapter);
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

    public void  LoginAsync (){
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            if (role_id.contentEquals("6") || role_id.contentEquals("7")) {
                mCompositeDisposable.add(service.getStandardDetails("selectStandardByPrincipal","","","","","","")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(getObserver()));
            }else
            {
                mCompositeDisposable.add(service.getStandardDetails("select",school_id,"","","","","")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(getObserver()));
            }

        } catch (Exception ex) {
        }
    }

}
