package info.efficacious.centralmodelschool.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.adapters.SentMessageAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SentFragment extends Fragment {
View view;
RecyclerView sent_rv;
List<String> strings=new ArrayList<>();
SentMessageAdapter madapter;
    public SentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         view=inflater.inflate(R.layout.fragment_sent, container, false);
        strings.add("Student");
        strings.add("Message");
        sent_rv=view.findViewById(R.id.sent_rv);
        madapter = new SentMessageAdapter(strings,"EventList");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        sent_rv.setLayoutManager(layoutManager);

        sent_rv.setAdapter(madapter);
        return view;

    }

}
