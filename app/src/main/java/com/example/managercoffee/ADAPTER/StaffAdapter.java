package com.example.managercoffee.ADAPTER;

import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.managercoffee.AsyncTask.DownloadImageTask;
import com.example.managercoffee.MODEL.staff;
import com.example.managercoffee.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class StaffAdapter extends FirestoreRecyclerAdapter<staff, StaffAdapter.StaffViewHolder> {
    private OnStaffClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public StaffAdapter(@NonNull FirestoreRecyclerOptions<staff> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull StaffViewHolder holder, int position, @NonNull staff model) {
        holder.Name.setText(model.getName());
        holder.Phone.setText(model.getPhone());
        new DownloadImageTask(holder.Image).execute(model.getURL());
    }

    @NonNull
    @Override
    public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.staff_card,viewGroup,false);
        return new StaffViewHolder(itemView);
    }
    class StaffViewHolder extends RecyclerView.ViewHolder {
        ImageView Image;
        TextView Name,Phone;
        StaffViewHolder(@NonNull View itemView) {

            super(itemView);
            Image =itemView.findViewById(R.id.staff_image);
            Name = itemView.findViewById(R.id.staff_name);
            Phone=itemView.findViewById(R.id.staff_phone);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION&&listener!=null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }
    public interface OnStaffClickListener{

        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void SetOnItemClickListener(OnStaffClickListener listener){
        this.listener=listener;
    }
}
