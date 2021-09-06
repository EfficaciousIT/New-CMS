package info.efficacious.centralmodelschool.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.Tab.TimetableActivity_student;
import info.efficacious.centralmodelschool.activity.MainActivity;
import info.efficacious.centralmodelschool.dialogbox.Standard_division_dialog;
import info.efficacious.centralmodelschool.entity.StandardDetail;
import info.efficacious.centralmodelschool.fragment.OnlineClassTimetable;
import info.efficacious.centralmodelschool.fragment.StandardWise_Book;
import info.efficacious.centralmodelschool.fragment.StudentExamFragment;
import info.efficacious.centralmodelschool.fragment.StudentSyllabusFragment;

public class StandardOnlineTimeTableAdapter extends RecyclerView.Adapter<StandardOnlineTimeTableAdapter.StandardListHolder> {

    private ArrayList<StandardDetail> dataList;
    private final Context mcontext;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String role_id;
    public static String Std_id_division, std_name_division, page_name, std_id, intSchool_id;
    private String pagename;
    public StandardOnlineTimeTableAdapter(ArrayList<StandardDetail> dataList, Context context, String value) {
        this.dataList = dataList;
        this.mcontext = context;
        this.pagename = value;
    }

    @Override
    public StandardOnlineTimeTableAdapter.StandardListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.standard_row, parent, false);
        return new StandardOnlineTimeTableAdapter.StandardListHolder(view);
    }

    @Override
    public void onBindViewHolder(final StandardOnlineTimeTableAdapter.StandardListHolder holder, final int position) {
        try {
            settings = mcontext.getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
            role_id = settings.getString("TAG_USERTYPEID", "");
            try {
                holder.id.setText(String.valueOf(dataList.get(position).getIntStandardId()));
                holder.name.setText(dataList.get(position).getVchStandardName());
            } catch (Exception ex) {

            }
        } catch (Exception ex) {

        }
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                StudentSyllabusFragment subjectFragment = new StudentSyllabusFragment();
//                Bundle args = new Bundle();
//                args.putString("std_id", String.valueOf(dataList.get(position).getIntStandardId()));
//                subjectFragment.setArguments(args);
//                MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, subjectFragment).commitAllowingStateLoss();

                OnlineClassTimetable onlineTimetableFragment = new OnlineClassTimetable();
                Bundle args = new Bundle();
                args.putString("std_id", String.valueOf(dataList.get(position).getIntStandardId()));
                onlineTimetableFragment.setArguments(args);
                MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, onlineTimetableFragment).commitAllowingStateLoss();

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class StandardListHolder extends RecyclerView.ViewHolder {

        TextView id;
        TextView name;
        RelativeLayout linear;
        StandardListHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.id_standard);
            name = (TextView) itemView.findViewById(R.id.name_standard);
            linear = (RelativeLayout) itemView.findViewById(R.id.Linear);
        }


    }

}