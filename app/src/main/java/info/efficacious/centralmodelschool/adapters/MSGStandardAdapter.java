package info.efficacious.centralmodelschool.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.entity.StandardDetail;

public class MSGStandardAdapter extends RecyclerView.Adapter<MSGStandardAdapter.StandardHolder> {
    private ArrayList<StandardDetail> dataList;

    @NonNull
    @Override
    public MSGStandardAdapter.StandardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sms_layout, parent, false);
        return new MSGStandardAdapter.StandardHolder(view);

    }

    public MSGStandardAdapter(ArrayList<StandardDetail> dataList) {
        this.dataList = dataList;
    }

    @Override
    public void onBindViewHolder(@NonNull StandardHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }
    class StandardHolder extends RecyclerView.ViewHolder {

        Button button;
        StandardHolder(View itemView) {
            super(itemView);
            button=itemView.findViewById(R.id.btn_standard);



        }


    }
}
