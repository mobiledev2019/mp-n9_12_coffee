package com.example.managercoffee.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.managercoffee.ADAPTER.DayAdapter;
import com.example.managercoffee.MODEL.DayCount;
import com.example.managercoffee.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Income_overview extends AppCompatActivity {
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private CollectionReference collect=db.collection("day");
    private static final String INDEX="NAME";
    private DayAdapter dayAdapter;
    private RecyclerView recyclerView;
    private TextView income_total,items_total,income_avg,items_avg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_overview);

        Toolbar toolbar= findViewById(R.id.income_overview_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setupTextView();
        setupRecycleView();
    }

    private void setupTextView() {
        income_total=findViewById(R.id.total_income_value);
        items_total=findViewById(R.id.total_item_value);
        income_avg=findViewById(R.id.avg_income);
        items_avg=findViewById(R.id.avg_item);
    }

    private void setupRecycleView() {
        Query query = collect.orderBy("day",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<DayCount> options = new FirestoreRecyclerOptions.Builder<DayCount>().
                setQuery(query,DayCount.class)
                .build();

        dayAdapter =new DayAdapter(options);

        recyclerView = findViewById(R.id.day_record);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(dayAdapter);
        dayAdapter.SetOnItemClickListener(new DayAdapter.OnDayClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                DayCount i =documentSnapshot.toObject(DayCount.class);
                Intent intent = new Intent(getApplicationContext(), Day_overview.class);
                intent.putExtra(INDEX,i.getDay());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        dayAdapter.stopListening();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        dayAdapter.startListening();
        getDaylist();

    }
    private void getDaylist(){
        collect.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            List<DayCount> dayCounts = new ArrayList<>();
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                dayCounts= queryDocumentSnapshots.toObjects(DayCount.class);
                showValue(dayCounts);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    private void showValue(List<DayCount> dayCounts) {
        int items=0,income=0;
        for(DayCount dayCount : dayCounts){
            items=dayCount.getItemcount()+items;
            income=dayCount.getIncome()+income;
        }
        income_total.setText(String.valueOf(income)+" $ ");
        items_total.setText(String.valueOf(items)+" items");
        income_avg.setText(String.valueOf(income/dayCounts.size())+" $/day ");
        items_avg.setText(String.valueOf(items/dayCounts.size())+" items/day ");

    }
}
