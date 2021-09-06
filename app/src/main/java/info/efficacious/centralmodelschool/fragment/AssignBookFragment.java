package info.efficacious.centralmodelschool.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.efficacious.centralmodelschool.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssignBookFragment extends Fragment {


    public AssignBookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assign_book, container, false);
    }

}
