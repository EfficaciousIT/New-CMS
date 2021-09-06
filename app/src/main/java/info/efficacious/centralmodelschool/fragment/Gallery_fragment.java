package info.efficacious.centralmodelschool.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.ArrayList;

import info.efficacious.centralmodelschool.Interface.DataService;
import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.adapters.GalleryAdapter;
import info.efficacious.centralmodelschool.dialogbox.Gallery_dialogBox;
import info.efficacious.centralmodelschool.entity.SchoolDetail;
import info.efficacious.centralmodelschool.entity.SchoolDetailsPojo;
import info.efficacious.centralmodelschool.webApi.RetrofitInstance;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class Gallery_fragment extends Fragment {
    View myview;
    String  school_id;
    String  role_id;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    RecyclerView imagesgrid;
    GalleryAdapter adapter;
    private ProgressDialog progress;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.gallery_fragment, null);
        imagesgrid = (RecyclerView) myview.findViewById(R.id.imagesgrid);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        role_id = settings.getString("TAG_USERTYPEID", "");
        school_id = settings.getString("TAG_SCHOOL_ID", "");
        FloatingActionButton fab = (FloatingActionButton) myview.findViewById(R.id.fab1);
        try {
            if (role_id.contentEquals("1") || role_id.contentEquals("2") || role_id.contentEquals("3")) {
                fab.setVisibility(View.GONE);
            } else {
                fab.setVisibility(View.VISIBLE);
            }
        } catch (Exception ex) {

        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getActivity(), Gallery_dialogBox.class);
                    startActivity(intent);
                } catch (Exception ex) {

                }
            }
        });
        try {
            progress = new ProgressDialog(getActivity());
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.setMessage("loading...");
            GalleryAsync ();
        } catch (Exception ex) {

        }

        return myview;
    }

    public void  GalleryAsync (){
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<SchoolDetailsPojo> call = service.getSchoolDetails("selectGallery");
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<SchoolDetailsPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                @Override
                public void onNext(SchoolDetailsPojo body) {
                    try {
                        generateImageList((ArrayList<SchoolDetail>) body.getSchoolDetails());

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

    public void generateImageList(ArrayList<SchoolDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
               /* for (int i=0;i<=taskListDataList.size();i++) {
                    Log.d("TAG", "generateImageList" + taskListDataList.get(i).getName());
                }*/
                adapter = new GalleryAdapter(taskListDataList,getActivity());

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

                imagesgrid.setLayoutManager(layoutManager);

                imagesgrid.setAdapter(adapter);

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
            Toast.makeText(getActivity(), " Exception Response Taking Time,Seems Network issue!"+ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
