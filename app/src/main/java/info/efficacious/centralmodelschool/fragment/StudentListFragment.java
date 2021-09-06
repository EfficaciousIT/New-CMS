//package info.efficacious.centralmodelschool.fragment;
//
//
//import android.os.Bundle;
//
//import androidx.appcompat.view.ActionMode;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.Toolbar;
//
//import org.w3c.dom.Text;
//
//import java.security.PrivateKey;
//import java.util.ArrayList;
//import java.util.List;
//
//import info.efficacious.centralmodelschool.R;
//import info.efficacious.centralmodelschool.RecyclerItemClickListener;
//import info.efficacious.centralmodelschool.adapters.StudentNameAdapter;
//import info.efficacious.centralmodelschool.dialogbox.BottomLayoutSheetDialog;
//import info.efficacious.centralmodelschool.entity.StudentStandardwiseDetail;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class StudentListFragment extends Fragment implements ActionMode.Callback, View.OnLongClickListener {
//    private ActionMode actionMode;
//    private boolean is_in_actionMode=false;
//    private RecyclerView mstudent_name_recyclerview;
//    ArrayList<StudentStandardwiseDetail> mStudentStandardwiseDetails=new ArrayList<>();
//    private TextView appbar;
//    private boolean isMultiSelect = false;
//    Toolbar toolbar;
//    private List<Integer> selectedIds = new ArrayList<>();
//    private ImageButton ibcheck;
//    StudentNameAdapter studentNameAdapter;
//    public StudentListFragment(ArrayList<StudentStandardwiseDetail> studentStandardwiseDetails) {
//        this.mStudentStandardwiseDetails=studentStandardwiseDetails;
//        // Required empty public constructor
//    }
//
//    public StudentListFragment( ) {
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view=inflater.inflate(R.layout.fragment_student_list, container, false);
//        // Inflate the layout for this fragment
//        mstudent_name_recyclerview=view.findViewById(R.id.student_name_recyclerview);
//        appbar=view.findViewById(R.id.appbar_title);
//        ibcheck=view.findViewById(R.id.ibcheck);
//        ibcheck.setVisibility(View.GONE);
//        appbar.setText("Select Student");
//        appbar.setVisibility(View.GONE);
//        studentNameAdapter=new StudentNameAdapter(new StudentListFragment(),mStudentStandardwiseDetails);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
//        mstudent_name_recyclerview.setLayoutManager(linearLayoutManager);
//        mstudent_name_recyclerview.setAdapter(studentNameAdapter);
//
//
////        callBottomUp();
//        return view;
//
//    }
//    private void multiSelect(int position) {
//        StudentStandardwiseDetail data = studentNameAdapter.getItem(position);
//        if (data != null){
//            if (actionMode != null) {
//                if (selectedIds.contains(data.getStudentId()))
//                    selectedIds.remove(Integer.valueOf(data.getName()));
//                else
//                    selectedIds.add(data.getStandardId());
//
//                if (selectedIds.size() > 0)
//                    actionMode.setTitle(String.valueOf(selectedIds.size())); //show selected item count on action mode.
//                else{
//                    actionMode.setTitle(""); //remove item count from action mode.
//                    actionMode.finish(); //hide action mode.
//                }
//                studentNameAdapter.setSelectedIds(selectedIds);
//
//            }
//        }
//    }
//    private void callBottomUp() {
//        Bundle bundle=new Bundle();
//        bundle.putString("args","fromStudentlistFragment");
//        BottomLayoutSheetDialog bottomLayoutSheetDialog=new BottomLayoutSheetDialog();
//        bottomLayoutSheetDialog.setArguments(bundle);
//        bottomLayoutSheetDialog.show(getFragmentManager(),"Studentlistfragment");
//        bottomLayoutSheetDialog.setCancelable(false);
//    }
//
//
//
//    @Override
//    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//        return false;
//    }
//
//    @Override
//    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//        return false;
//    }
//
//    @Override
//    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//        return false;
//    }
//
//    @Override
//    public void onDestroyActionMode(ActionMode mode) {
//
//    }
//
//    @Override
//    public boolean onLongClick(View view) {
//
//        return false;
//    }
//}
//
