package info.efficacious.centralmodelschool.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.webApi.RetrofitInstance;


public class Admission_Fragment extends Fragment {
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    WebView webView;
    private ProgressDialog progress;
    String command,User_id,student_id,role_id,academic_id,school_id ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_address_form, null);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        progress = new ProgressDialog(getActivity());
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.setMessage("loading...");
        student_id= settings.getString("TAG_STANDERDID", "");
        role_id = settings.getString("TAG_USERTYPEID", "");
        academic_id = settings.getString("TAG_ACADEMIC_ID", "");
        User_id = settings.getString("TAG_USERID", "");
        school_id = settings.getString("TAG_SCHOOL_ID", "");
        webView=view.findViewById(R.id.webview);
        progress.show();

        String url= RetrofitInstance.readmisiionUrl+"strStudentId="+User_id+"&strStandardId="+student_id+"&strSchoolId="+school_id+"&strAcademicId="+academic_id;

        //Toast.makeText(getContext(),""+url,Toast.LENGTH_LONG).show();
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progress.dismiss();
            }

        });

        return view;

    }
}

