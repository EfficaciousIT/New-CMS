package info.efficacious.centralmodelschool.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import info.efficacious.centralmodelschool.MultiImages.models.Image;
import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.entity.StandardDetail;

public class StandardGridAdapter extends RecyclerView.Adapter<StandardGridAdapter.StandardGridHolder>

{
    private static final String TAG = "StandardGridAdapter";
    ArrayList<StandardDetail> mstandardDetails=new ArrayList<>();
    private OnStandardListener mOnStandardListener;
    ArrayList<String> mstandardstore=new ArrayList<>();
    Context mcontext;
    int count=0;


    int mcount;
    public StandardGridAdapter(ArrayList<StandardDetail> standardDetails, Context context,OnStandardListener onStandardListener) {
        this.mstandardDetails = standardDetails;
        this.mcontext=context;
        this.mOnStandardListener=onStandardListener;
    }

    @NonNull
    @Override
    public StandardGridHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.standards_layout,parent,false);
        return new StandardGridHolder(view,mOnStandardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StandardGridHolder holder, int position) {

        holder.btn.setText(mstandardDetails.get(position).getVchStandardName());
        String s="all";
        holder.btn.setTextSize(12);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btn.setTextColor(Color.BLUE);

                mOnStandardListener.onStandardClickdata(String.valueOf(position));
            }
        });




    }

    @Override
    public int getItemCount() {
        return mstandardDetails.size();
    }



    public class StandardGridHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Button btn;
        OnStandardListener onStandardListener;
        public StandardGridHolder(@NonNull View itemView,OnStandardListener onStandardListener) {
            super(itemView);
            btn = itemView.findViewById(R.id.info_text);
            this.onStandardListener=onStandardListener;
            itemView.setOnClickListener(this);

        }



        @Override
        public void onClick(View view) {
//            onStandardListener.onStandardClickdata(String.valueOf(getAdapterPosition()));
//            onStandardListener.onStandardClick(integers.get(0));
        }
    }
    public interface OnStandardListener{
        void onStandardClickdata(String data);
        void onStandardClick(int position);
    }

}
