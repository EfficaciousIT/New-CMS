package info.efficacious.centralmodelschool.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.adapters.StudentNameAdapter;
import info.efficacious.centralmodelschool.entity.StudentStandardwiseDetail;

public class StudentList extends AppCompatActivity implements View.OnLongClickListener {
    public boolean is_in_actionMode = false;
    private RecyclerView mstudent_name_recyclerview;
    ArrayList<StudentStandardwiseDetail> mStudentStandardwiseDetails = new ArrayList<>();
    private TextView appbar;
    private boolean isMultiSelect = false;
    private Toolbar toolbar;
    private List<Integer> selectedIds = new ArrayList<>();
    private ImageButton ibcheck;
    StudentNameAdapter studentNameAdapter;
    int counter = 0;
    private static final String TAG = "StudentList";
    ArrayList<String> numberofstudent=new ArrayList<>();
    ArrayList<String> mstudents = new ArrayList<>();
    FragmentManager fragmentManager;
    ArrayList<String > mmobileNumber=new ArrayList<>();
    ArrayList<String> selectedMobile=new ArrayList<>();
    int mpos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        fragmentManager = getSupportFragmentManager();
        Toast.makeText(this, "inside_studentlist", Toast.LENGTH_SHORT).show();
        mstudent_name_recyclerview = findViewById(R.id.student_name_recyclerview);
        appbar = findViewById(R.id.appbar_title);
        ibcheck = findViewById(R.id.ibcheck);
        ibcheck.setVisibility(View.GONE);
        appbar.setText("Select Student");
        appbar.setVisibility(View.GONE);
        Intent intent = getIntent();
        mstudents = intent.getStringArrayListExtra("studentsName");
        mmobileNumber=  intent.getStringArrayListExtra("studentdetails");
        Log.e(TAG, "mMobileNumber: "+mmobileNumber.toString() );
        if (mstudents.size() > 0) {
            callRecycler();
        }
            ibcheck.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("mstudent",numberofstudent);
                bundle.putStringArrayList("mobile",selectedMobile);
                // set Fragmentclass Arguments
                Intent newMessageintent=new Intent(this,NewmessegesActivity.class);
                startActivity(newMessageintent,bundle);
                this.finish();
            });
    }
    private void callRecycler() {
        studentNameAdapter = new StudentNameAdapter(this, mstudents,mmobileNumber);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mstudent_name_recyclerview.setLayoutManager(linearLayoutManager);
        mstudent_name_recyclerview.setAdapter(studentNameAdapter);
    }
    @Override
    public boolean onLongClick(View view) {
        ibcheck.setVisibility(View.VISIBLE);
        appbar.setVisibility(View.VISIBLE);
        appbar.setText("Select");
        is_in_actionMode = true;
        studentNameAdapter.notifyDataSetChanged();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }
    public void prepareselection(View view, int position) {
        mpos=position;
        numberofstudent.clear();
        selectedMobile.clear();
        if (((CheckBox) view).isChecked()) {
            numberofstudent.add(mstudents.get(position));
            selectedMobile.add(mmobileNumber.get(position));
            Log.e(TAG, "prepareselection: "+selectedMobile );
            counter = counter+1;
            updateCounter(counter);
        } else {
            mstudents.remove(mstudents.get(position));
            counter = counter - 1;
            updateCounter(counter);
        }
    }
    public void updateCounter(int counter) {
        if (counter == 0) {
            appbar.setText("0 item Selected");
        } else {
            appbar.setText(counter + "item selected");
        }
    }
}
