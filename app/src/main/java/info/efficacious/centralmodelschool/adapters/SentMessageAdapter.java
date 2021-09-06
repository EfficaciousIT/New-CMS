package info.efficacious.centralmodelschool.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import info.efficacious.centralmodelschool.R;

public class SentMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
List<String> mstrings=new ArrayList<>();
String title;
    public SentMessageAdapter(List<String> strings,String mtitle) {
        this.mstrings=strings;
        this.title=mtitle;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sentmessagelist, parent, false);
        return new Listviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


    }






    @Override
    public int getItemCount() {
        return mstrings.size();
    }

    public class Listviewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView  student_name,message;

        public Listviewholder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.message_pic);
            student_name=itemView.findViewById(R.id.Student_name);
            message=itemView.findViewById(R.id.Message);

        }

    }
}
