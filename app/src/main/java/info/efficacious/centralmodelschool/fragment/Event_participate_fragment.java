package info.efficacious.centralmodelschool.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import info.efficacious.centralmodelschool.Interface.DataService;
import info.efficacious.centralmodelschool.R;

import info.efficacious.centralmodelschool.adapters.EventListAdapter;
import info.efficacious.centralmodelschool.common.ConnectionDetector;
import info.efficacious.centralmodelschool.entity.EventDetail;
import info.efficacious.centralmodelschool.entity.EventDetailPojo;
import info.efficacious.centralmodelschool.webApi.RetrofitInstance;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class Event_participate_fragment extends Fragment {
    View myview;
    RecyclerView recyclerView;
    ArrayAdapter adapter;
    ConnectionDetector cd;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String  Year_id,Schooli_id;
    String userid, role_id, value,vchstandard_id;
    private ProgressDialog progress;
    EventListAdapter madapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.fragment_leavelist, null);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        Schooli_id= settings.getString("TAG_SCHOOL_ID", "");
        recyclerView = (RecyclerView) myview.findViewById(R.id.leavelist_list);
        cd = new ConnectionDetector(getContext().getApplicationContext());
        role_id = settings.getString("TAG_USERTYPEID", "");
        if (!cd.isConnectingToInternet()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setMessage("No Internet Connection");
            alert.setPositiveButton("OK", null);
            alert.show();
        } else {
            try
            {
                vchstandard_id = settings.getString("TAG_STANDERDID", "");
                userid = settings.getString("TAG_USERID", "");
                Year_id = settings.getString("TAG_ACADEMIC_ID", "");
                value = "Event";
                progress = new ProgressDialog(getActivity());
                progress.setCancelable(false);
                progress.setCanceledOnTouchOutside(false);
                progress.setMessage("loading...");
                progress.show();
                EventAsync();
            }catch (Exception ex)
            {

            }

        }
        return myview;
    }

    public void  EventAsync (){
        try {
            String command = null;
            if(role_id.contentEquals("1")||role_id.contentEquals("2"))
            {
                command="SelectEventParticipatedByStudent";
            }else if(role_id.contentEquals("3"))
            {
                command="SelectEventParticipatedByTeacher";

            }
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<EventDetailPojo> call = service.getEventDetails(command,vchstandard_id,Year_id,Schooli_id,userid);
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<EventDetailPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                @Override
                public void onNext(EventDetailPojo body) {
                    try {
                        generateEventDetail((ArrayList<EventDetail>) body.getEventDetail());
                    } catch (Exception ex) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    progress.dismiss();
                    Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onComplete() {
                    progress.dismiss();
                }
            });
        } catch (Exception ex) {
            progress.dismiss();
        }
    }

    public void generateEventDetail(ArrayList<EventDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                madapter = new EventListAdapter(taskListDataList,getActivity(),"EventParticipatedList");

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

                recyclerView.setLayoutManager(layoutManager);

                recyclerView.setAdapter(madapter);
            } else {

            }

        } catch (Exception ex) {
            progress.dismiss();
            Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }
}