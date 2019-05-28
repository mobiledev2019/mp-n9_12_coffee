package com.example.managercoffee.ADAPTER;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.managercoffee.AsyncTask.DownloadImageTask;
import com.example.managercoffee.MODEL.item;
import com.example.managercoffee.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class ItemAdapter extends FirestoreRecyclerAdapter<item, ItemAdapter.ItemViewHolder>{

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private OnItemClickListener listener;
    public ItemAdapter(@NonNull FirestoreRecyclerOptions<item> options) {
        super(options);
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card,viewGroup,false);
        return new ItemViewHolder(itemView);
    }


    @Override
    protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull item model) {
        holder.item_name.setText(model.getName());
        holder.item_price.setText(String.valueOf(model.getPrice()));
        new DownloadImageTask(holder.item_image).execute(model.getURL());
    }




    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView item_image;
        TextView item_name,item_price;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            item_image = itemView.findViewById(R.id.item_image);
            item_name =itemView.findViewById(R.id.item_name);
            item_price=itemView.findViewById(R.id.item_price);
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
    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void SetOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
}
