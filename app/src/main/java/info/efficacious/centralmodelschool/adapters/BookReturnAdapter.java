package info.efficacious.centralmodelschool.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import info.efficacious.centralmodelschool.R;

public class BookReturnAdapter extends RecyclerView.Adapter<BookReturnAdapter.BookReturnViewholder> {
    @NonNull
    @Override
    public BookReturnAdapter.BookReturnViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.bookreturnlayout,null);
        BookReturnAdapter.BookReturnViewholder viewHolder=new BookReturnAdapter.BookReturnViewholder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookReturnViewholder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return 0;
    }
    class BookReturnViewholder extends RecyclerView.ViewHolder{

        public BookReturnViewholder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
