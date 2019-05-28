package com.example.managercoffee.ADAPTER;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.managercoffee.MODEL.DayCount;
import com.example.managercoffee.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class DayAdapter extends FirestoreRecyclerAdapter<DayCount, DayAdapter.DayViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private OnDayClickListener listener;
    public DayAdapter(@NonNull FirestoreRecyclerOptions<DayCount> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DayViewHolder holder, int position, @NonNull DayCount model) {
        holder.day.setText(model.getDay());
        holder.items.setText(String.valueOf(model.getItemcount())+" items");
        holder.income.setText(String.valueOf(model.getIncome())+" $");
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.day_card,viewGroup,false);
        return new DayViewHolder(itemView);
    }

    class DayViewHolder extends RecyclerView.ViewHolder {
        TextView day,items,income;

        DayViewHolder(@NonNull View itemView) {
            super(itemView);
            day =itemView.findViewById(R.id.day);
            items =itemView.findViewById(R.id.item_sold);
            income =itemView.findViewById(R.id.income);
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
    public interface OnDayClickListener{

        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void SetOnItemClickListener(OnDayClickListener listener){
        this.listener=listener;
    }
}
