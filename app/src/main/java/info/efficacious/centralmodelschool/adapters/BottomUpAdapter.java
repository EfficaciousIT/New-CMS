package info.efficacious.centralmodelschool.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.entity.StandardDetail;
import io.reactivex.disposables.CompositeDisposable;

public class BottomUpAdapter extends RecyclerView.Adapter<BottomUpAdapter.BottomUpAdapterViewHolder>  {
    ArrayList<String> mDivisionList =new ArrayList<>();
    private String mDivision;
    private Context mContext;
    private static final String TAG = "BottomUpAdapter";
    private CompositeDisposable mCompositeDisposable;
    private onDivisionListener mOnDivisionListener;
    private ArrayList<Integer> mDivid;
    private int mdivisonId;
    private String mstandardid;

    public BottomUpAdapter(ArrayList<Integer> divId,ArrayList<String> division, String standardid,Context context,onDivisionListener onDivisionListener) {
        this.mDivisionList=division;
        this.mContext=context;
        this.mOnDivisionListener=onDivisionListener;
        this.mDivid=divId;
        this.mstandardid=standardid;
    }

    @NonNull
    @Override
    public BottomUpAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.bottomupdesign,parent,false);
        return new BottomUpAdapterViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull BottomUpAdapterViewHolder holder, int position) {


        Log.e(TAG, "Size: "+mDivisionList.size() );
        Log.e(TAG, "onBindViewHolder: "+mDivisionList.get(position) );

        holder.btndiv.setText(mDivisionList.get(position));
        holder.btndiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.btndiv.setTextColor(Color.BLUE);
                mdivisonId=mDivid.get(position);
                mDivision=mDivisionList.get(position);

                mOnDivisionListener.OnClickDivision(mDivision,mdivisonId);
                Toast.makeText(mContext, "You have selected"+mDivision, Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public int getItemCount() {
        return mDivisionList.size();
    }

    class BottomUpAdapterViewHolder extends  RecyclerView.ViewHolder  {
        Button btndiv;

        public BottomUpAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            btndiv=itemView.findViewById(R.id.div_bottom_btn);


        }


    }
public interface onDivisionListener {
          void OnClickDivision(String pos,int id) ;

}
}
