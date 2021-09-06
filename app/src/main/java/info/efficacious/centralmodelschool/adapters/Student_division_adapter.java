package info.efficacious.centralmodelschool.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.Tab.StudentAttendanceActivity;
import info.efficacious.centralmodelschool.Tab.TimetableActivity_student;
import info.efficacious.centralmodelschool.activity.MainActivity;
import info.efficacious.centralmodelschool.entity.StandardDetail;


public class Student_division_adapter extends RecyclerView.Adapter<Student_division_adapter.DivisionHolder> {

    private ArrayList<StandardDetail> dataList;
    private final Context mcontext;
    private String pagename;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    public String role_id;
    public static String div,std_id;

    public Student_division_adapter(Context context, ArrayList<StandardDetail> dataList, String value) {
        this.dataList = dataList;
        this.mcontext = context;
        this.pagename = value;
    }

    @Override
    public Student_division_adapter.DivisionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.standard_division, parent, false);
        return new Student_division_adapter.DivisionHolder(view);
    }

    @Override
    public void onBindViewHolder(final Student_division_adapter.DivisionHolder holder, final int position) {
        try {
            holder.textView.setText(dataList.get(position).getVchDivisionName());
        } catch (Exception ex) {

        }
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                div = String.valueOf(dataList.get(position).getIntDivisionId());
                Log.d("Tag","Division_Id"+div);
                std_id= StandardAdapter.Std_id_division;
                Log.d("Tag","Division_Id+STD:"+div+std_id);
                if (pagename.contentEquals("Standarad_division")) {
                    try {
                        ((Activity) mcontext).finish();

                        StudentAttendanceActivity studentAttendanceActivity = new StudentAttendanceActivity();
                        Bundle args = new Bundle();
                        args.putString("std_id", StandardAdapter.Std_id_division);
                        args.putString("std_name", StandardAdapter.std_name_division);
                        args.putString("std_div", String.valueOf(dataList.get(position).getIntDivisionId()));
                        args.putString("selected_layout", "Stdwise_name");
                        studentAttendanceActivity.setArguments(args);
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, studentAttendanceActivity).commitAllowingStateLoss();

                    } catch (Exception ex) {

                    }

                } else if (pagename.contentEquals("Standarad_TimeTable")) {
                    try {
                        ((Activity) mcontext).finish();
                        TimetableActivity_student timetableActivity_student = new TimetableActivity_student();
                       /* settings.edit().putString("TAG_DIVISIONID", div).commit();
                        Bundle args = new Bundle();
                        args.putString("std_id", StandardAdapter.Std_id_division);
                        args.putString("std_name", StandardAdapter.std_name_division);
                        args.putString("std_div", String.valueOf(dataList.get(position).getIntDivisionId()));
                        args.putString("selected_layout", "Stdwise_name");
                        timetableActivity_student.setArguments(args);*/
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, timetableActivity_student).commitAllowingStateLoss();

                    } catch (Exception ex) {

                    }

                } else if (pagename.contentEquals("Standarad_Result")) {
                    try {
                        ((Activity) mcontext).finish();
                    } catch (Exception ex) {

                    }

                    if (role_id.contentEquals("3")) {
                        try {
//                            Result_Tab studentResultFragment = new Result_Tab();
//                            Bundle args = new Bundle();
//                            args.putString("Div_name", dataList.get(position).getVchDivisionName());
//                            args.putString("Std_name", StandardAdapter.std_name_division);
//                            args.putInt("Div_id", Integer.parseInt(String.valueOf(dataList.get(position).getIntDivisionId())));
//                            args.putInt("Std_id", Integer.parseInt(StandardAdapter.Std_id_division));
//                            studentResultFragment.setArguments(args);
//                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, studentResultFragment).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }
                    } else {
                        try {
//                            StudentResultFragment studentResultFragmen = new StudentResultFragment();
//                            Bundle args = new Bundle();
//                            args.putString("Div_name", dataList.get(position).getVchDivisionName());
//                            args.putString("Std_name", StandardAdapter.std_name_division);
//                            args.putInt("Div_id", Integer.parseInt(String.valueOf(dataList.get(position).getIntDivisionId())));
//                            args.putInt("Std_id", Integer.parseInt(StandardAdapter.Std_id_division));
//                            studentResultFragmen.setArguments(args);
//                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, studentResultFragmen).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }
                    }


                } else {
                    try {
                        TimetableActivity_student timetableActivity_student = new TimetableActivity_student();
                        Bundle args = new Bundle();
                        args.putString("std_id", String.valueOf(dataList.get(position).getIntStandardId()));
                        args.putString("div_id", String.valueOf(dataList.get(position).getIntDivisionId()));
                        timetableActivity_student.setArguments(args);
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, timetableActivity_student).commitAllowingStateLoss();

                    } catch (Exception ex) {

                    }

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class DivisionHolder extends RecyclerView.ViewHolder {

        TextView textView;

        DivisionHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView38);
        }


    }

}

