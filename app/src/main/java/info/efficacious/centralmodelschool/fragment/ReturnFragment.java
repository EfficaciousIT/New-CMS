package info.efficacious.centralmodelschool.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.adapters.BookReturnAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReturnFragment extends Fragment {

    private RecyclerView recyclerView;
    private View view;
    private BookReturnAdapter bookReturnAdapter;

    public ReturnFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_return, container, false);
        recyclerView=view.findViewById(R.id.recycler_studentbookdetails);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        bookReturnAdapter=new BookReturnAdapter();
        recyclerView.setAdapter(bookReturnAdapter);
        return view;

    }

}
