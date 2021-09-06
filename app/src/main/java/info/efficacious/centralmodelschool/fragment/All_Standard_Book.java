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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

import info.efficacious.centralmodelschool.Interface.DataService;
import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.adapters.Book_Adapter;
import info.efficacious.centralmodelschool.common.ConnectionDetector;
import info.efficacious.centralmodelschool.entity.LibraryDetail;
import info.efficacious.centralmodelschool.entity.LibraryDetailPojo;
import info.efficacious.centralmodelschool.webApi.RetrofitInstance;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class All_Standard_Book extends Fragment {
    View myview;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    androidx.appcompat.widget.SearchView searchView;
    String Schooli_id;
    ConnectionDetector cd;
    RecyclerView recyclerView;
    private ProgressDialog progress;
    Book_Adapter madapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview=inflater.inflate(R.layout.activity_allstudent,null);
        cd = new ConnectionDetector(getActivity());
        searchView = (androidx.appcompat.widget.SearchView) myview.findViewById(R.id.search_view_student);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        Schooli_id= settings.getString("TAG_SCHOOL_ID", "");
        recyclerView  = (RecyclerView) myview.findViewById(R.id.allstudent_list);
        progress = new ProgressDialog(getActivity());
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.setMessage("loading...");
        progress.show();
        if (!cd.isConnectingToInternet())
        {

            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setMessage("No InternetConnection");
            alert.setPositiveButton("OK",null);
            alert.show();

        }

        else {

            StudentAsync ();
        }
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                madapter.getFilter().filter(newText);
//                return true;
//            }
//        });
        return myview;
    }
    private void setupSearchView()
    {
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search Book Name Here");
    }
    public void  StudentAsync (){
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<LibraryDetailPojo> call = service.getLibraryDetails("AllBooks",Schooli_id,"","");
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<LibraryDetailPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                @Override
                public void onNext(LibraryDetailPojo body) {
                    try {
                        generateBookList((ArrayList<LibraryDetail>) body.getLibraryDetail());
                    } catch (Exception ex) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    progress.dismiss();
                    Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();

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

    public void generateBookList(ArrayList<LibraryDetail> taskListDataList) {
//        try {
//            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
//                madapter = new Book_Adapter(taskListDataList,getActivity());
//
//                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//
//                recyclerView.setLayoutManager(layoutManager);
//
//                recyclerView.setAdapter(madapter);
//                setupSearchView();
//            } else {
//                Toast toast = Toast.makeText(getActivity(),
//                        "No Data Available",
//                        Toast.LENGTH_SHORT);
//                View toastView = toast.getView();
//                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//                toastView.setBackgroundResource(R.drawable.no_data_available);
//                toast.show();
//            }
//
//        } catch (Exception ex) {
//            progress.dismiss();
//            Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
//        }
    }
}