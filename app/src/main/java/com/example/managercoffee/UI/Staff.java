package com.example.managercoffee.UI;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import com.example.managercoffee.ADAPTER.StaffAdapter;
import com.example.managercoffee.MODEL.staff;
import com.example.managercoffee.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Staff extends AppCompatActivity{
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private CollectionReference collect=db.collection("staff_profile");
    private StaffAdapter staffAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        Toolbar toolbar = findViewById(R.id.staff_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setupRecycleView();
    }
    private void setupRecycleView() {
        Query query = collect.orderBy("name",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<staff> options = new FirestoreRecyclerOptions.Builder<staff>().
                setQuery(query,staff.class)
                .build();

        staffAdapter =new StaffAdapter(options);

        recyclerView = findViewById(R.id.Frag_staff);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(staffAdapter);
        staffAdapter.SetOnItemClickListener(new StaffAdapter.OnStaffClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        staffAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        staffAdapter.stopListening();
    }

}
