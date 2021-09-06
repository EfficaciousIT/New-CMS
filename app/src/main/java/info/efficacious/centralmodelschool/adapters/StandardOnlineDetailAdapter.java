package info.efficacious.centralmodelschool.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.activity.MainActivity;
import info.efficacious.centralmodelschool.entity.StandardDetail;
import info.efficacious.centralmodelschool.fragment.OnlineClassDetail;
import info.efficacious.centralmodelschool.fragment.OnlineClassTimetable;

public class StandardOnlineDetailAdapter extends RecyclerView.Adapter<StandardOnlineDetailAdapter.StandardListHolder> {

    private ArrayList<StandardDetail> dataList;
    private final Context mcontext;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String role_id;
    public static String Std_id_division, std_name_division, page_name, std_id, intSchool_id;
    private String pagename;
    public StandardOnlineDetailAdapter(ArrayList<StandardDetail> dataList, Context context, String value) {
        this.dataList = dataList;
        this.mcontext = context;
        this.pagename = value;
    }

    @Override
    public StandardOnlineDetailAdapter.StandardListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.standard_row, parent, false);
        return new StandardOnlineDetailAdapter.StandardListHolder(view);
    }

    @Override
    public void onBindViewHolder(final StandardOnlineDetailAdapter.StandardListHolder holder, final int position) {
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

                OnlineClassDetail onlineTimetableFragment = new OnlineClassDetail();
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