package info.efficacious.centralmodelschool.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.activity.StudentList;
import info.efficacious.centralmodelschool.entity.StudentStandardwiseDetail;

public class StudentNameAdapter extends RecyclerView.Adapter<StudentNameAdapter.AllStudentViewHolder>  {
    ArrayList<String> mStudent =new ArrayList<>();
    List<Integer> selectedIds=new ArrayList<>();
    private static final String TAG = "StudentNameAdapter";
    boolean is_selected=false;
    StudentList studentList ;
    ArrayList<String> mmobile;
    public StudentNameAdapter(StudentList context,ArrayList<String> student,ArrayList<String> mobile ) {
        this.studentList=context;
        this.mStudent =student ;
        this.mmobile=mobile;
    }

    @NonNull
    @Override
    public StudentNameAdapter.AllStudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.studentlistchecklist,parent,false);
        AllStudentViewHolder allStudentViewHolder=new AllStudentViewHolder(view,studentList);
        return allStudentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllStudentViewHolder holder, int position) {
        holder.mtextView.setText(mmobile.get(position) );
        if (! studentList.is_in_actionMode)
        {
            holder.mcheckbox.setVisibility(View.GONE);
        }else {

            holder.mcheckbox.setVisibility(View.VISIBLE);
            holder.mcheckbox.setChecked(false);

        }
    }


    @Override
    public int getItemCount() {
        return mStudent.size();
    }

    class AllStudentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mtextView;
        CheckBox mcheckbox;
        LinearLayout linearlayout_student;
        StudentList  mstudentList ;

        public AllStudentViewHolder(@NonNull View itemView, StudentList studentList) {
            super(itemView);
            mtextView=itemView.findViewById(R.id.textview_studentname);
            mcheckbox=itemView.findViewById(R.id.chkEnable);
            linearlayout_student=itemView.findViewById(R.id.linearlayout_student);
            this.mstudentList =studentList ;
            linearlayout_student.setOnLongClickListener(studentList);
            mcheckbox.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.e(TAG, "onClick: "+getAdapterPosition() );
            mstudentList.prepareselection(view,getAdapterPosition());
        }
    }
    public String getItem(int position){
        return mmobile.get(position);
    }
    public void setSelectedIds(List<Integer> selectedIds) {
        this.selectedIds = selectedIds;
        notifyDataSetChanged();
    }
}
