package info.efficacious.centralmodelschool.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.activity.EntityNameListActivity;
import info.efficacious.centralmodelschool.entity.DashboardDetail;
import info.efficacious.centralmodelschool.entity.StudentStandardwiseDetail;
import info.efficacious.centralmodelschool.entity.TeacherDetail;

public class CustomAdapter extends BaseAdapter {
    EntityNameListActivity activity;
    List<TeacherDetail> users;
    List<DashboardDetail> mstaff;
    List<StudentStandardwiseDetail> mstudent;
    LayoutInflater inflater;

    //short to create constructer using command+n for mac & Alt+Insert for window


    public CustomAdapter(EntityNameListActivity activity, List<TeacherDetail> users, List<DashboardDetail> staff, List<StudentStandardwiseDetail> student) {
        this.activity = activity;
        this.users = users;
        inflater = activity.getLayoutInflater();
        this.mstaff = staff;
        this.mstudent = student;
    }


    @Override
    public int getCount() {
        if (users != null)
            return users.size();
        else if (mstaff != null)
            return mstaff.size();
        else if (mstudent != null) {
            return mstudent.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;

        if (view == null) {

            view = inflater.inflate(R.layout.list_view_item, viewGroup, false);

            holder = new ViewHolder();

            holder.tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
            holder.ivCheckBox = (ImageView) view.findViewById(R.id.iv_check_box);

            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();

        if (users != null) {
            TeacherDetail teachermodel = users.get(i);
            holder.tvUserName.setText(teachermodel.getName());
            if (teachermodel.isSelected())
                holder.ivCheckBox.setBackgroundResource(R.drawable.ic_check_black_24dp);

            else
                holder.ivCheckBox.setBackgroundColor(Color.GRAY);
        } else if (mstaff!=null)
        {
            DashboardDetail staffmodel = mstaff.get(i);
            holder.tvUserName.setText(staffmodel.getIntmobileno());
            if (staffmodel.isSelected())
                holder.ivCheckBox.setBackgroundResource(R.drawable.ic_check_black_24dp);
            else
                holder.ivCheckBox.setBackgroundColor(Color.GRAY);
        }
        else {
            StudentStandardwiseDetail studentmodel = mstudent.get(i);
            holder.tvUserName.setText(studentmodel.getName());
            if (studentmodel.isSelected())
                holder.ivCheckBox.setBackgroundResource(R.drawable.ic_check_black_24dp);
            else
                holder.ivCheckBox.setBackgroundColor(Color.GRAY);
        }



        return view;

    }

    public void updateRecords(List<TeacherDetail> users, List<DashboardDetail> staff, List<StudentStandardwiseDetail> student) {
        this.users = users;
        this.mstaff = staff;
        this.mstudent = student;
        notifyDataSetChanged();
    }

    class ViewHolder {

        TextView tvUserName;
        ImageView ivCheckBox;

    }
}
