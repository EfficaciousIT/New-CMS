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

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

import info.efficacious.centralmodelschool.Interface.DataService;
import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.adapters.ChatAllUser_Adapter;
import info.efficacious.centralmodelschool.common.ConnectionDetector;
import info.efficacious.centralmodelschool.entity.ChatDetail;
import info.efficacious.centralmodelschool.entity.ChatDetailsPojo;
import info.efficacious.centralmodelschool.webApi.RetrofitInstance;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Teacher_Chat_Fragment extends Fragment {
    View myview;
    RecyclerView recyclerView;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String Academic_id, Schooli_id, role_id, Standard_id="", Division_id="", userid="";
    SearchView searchView;
    ChatAllUser_Adapter adapter;
    ConnectionDetector cd;
    private ProgressDialog progress;
    private static final String TAG = "Teacher_Chat_Fragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.chat_allusername_recylerview, null);
        recyclerView = (RecyclerView) myview.findViewById(R.id.chat_listview);
        cd = new ConnectionDetector(getActivity());
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
//        searchView = (SearchView) myview.findViewById(R.id.search_view_member);
//        searchView.setVisibility(View.GONE);
        Academic_id = settings.getString("TAG_ACADEMIC_ID", "");
        Schooli_id = settings.getString("TAG_SCHOOL_ID", "");
        role_id = settings.getString("TAG_USERTYPEID", "");
        userid = settings.getString("TAG_USERID", "");
        Log.e(TAG, "onCreateView: "+role_id+"__"+userid+"__"+Schooli_id+"__"+Academic_id );
        progress = new ProgressDialog(getActivity());
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.setMessage("loading...");
        try {
            if (role_id.contentEquals("2") || role_id.contentEquals("1")) {
                Standard_id = settings.getString("TAG_STANDERDID", "");
                Division_id = settings.getString("TAG_DIVISIONID", "");
                if (!cd.isConnectingToInternet()) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setMessage("No InternetConnection");
                    alert.setPositiveButton("OK", null);
                    alert.show();

                } else {
//                    ChatTeacherAsync ();
                    Toast.makeText(getContext(), "Studentlogin", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (!cd.isConnectingToInternet()) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setMessage("No InternetConnection");
                    alert.setPositiveButton("OK", null);
                    alert.show();

                } else {
                    ChatAllTeacherAsync ();
                    Toast.makeText(getContext(), "Adminlogin", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception ex) {

        }
    /*    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });*/

        return myview;
    }

  /*  private void setupSearchView() {
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search Teacher Name Here");
    }*/


    public void ChatTeacherAsync (){
        try {
            String cmd="selectTeacherStandardWise";

//            String cmd="";
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<ChatDetailsPojo> call = service.getChatUserDetails(cmd,Schooli_id,"",Standard_id,Division_id,Academic_id);
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ChatDetailsPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                @Override
                public void onNext(ChatDetailsPojo body) {
                    try {
                        generateUserList((ArrayList<ChatDetail>) body.getChatDetails());
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

    public void generateUserList(ArrayList<ChatDetail> taskListDataList) {
        for (int i = 0; i < taskListDataList.size(); i++){
            Log.e(TAG, "generateUserList: x" + taskListDataList.get(i).getName());
        }
        try {

            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                adapter = new ChatAllUser_Adapter(taskListDataList,getActivity(),role_id);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
//                setupSearchView();
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
            progress.dismiss();
            Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }

    public void ChatAllTeacherAsync (){
        try {
            String command;
            if (role_id.contentEquals("3")) {
                command="selectTeacher";
//                command="";
            } else if (role_id.contentEquals("6") || role_id.contentEquals("7")) {
                command="selectPrincipal";
            } else if (role_id.contentEquals("5")) {
                command = "selectTeacher";
            }
            else {
                command="selectAdmin";
            }
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<ChatDetailsPojo> call = service.getChatUserDetails(command,Schooli_id,"",Standard_id,Division_id,Academic_id);
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ChatDetailsPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                @Override
                public void onNext(ChatDetailsPojo body) {
                    try {
                        generateUserList1((ArrayList<ChatDetail>) body.getChatDetails());
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
    public void generateUserList1(ArrayList<ChatDetail> taskListDataList) {
        for (int i = 0; i < taskListDataList.size(); i++){
            Log.e(TAG, "generateUserList: x" + taskListDataList.get(i).getName());
        }
        try {

            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                adapter = new ChatAllUser_Adapter(taskListDataList,getActivity(),role_id);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
//                setupSearchView();
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
            progress.dismiss();
            Toast.makeText(getActivity(), "Response Taking Time,Seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }
}
