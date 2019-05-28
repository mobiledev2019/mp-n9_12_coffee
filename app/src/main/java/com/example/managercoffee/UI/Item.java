package com.example.managercoffee.UI;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import com.example.managercoffee.ADAPTER.ItemAdapter;
import com.example.managercoffee.MODEL.item;
import com.example.managercoffee.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Item extends AppCompatActivity{
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private CollectionReference collect=db.collection("item_info");
    private ItemAdapter itemAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Toolbar toolbar= findViewById(R.id.item_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //setupSearchBar();
        setupRecycleView();
    }

    private void setupRecycleView() {
        Query query = collect.orderBy("price",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<item> options = new FirestoreRecyclerOptions.Builder<item>().
                setQuery(query,item.class)
                .build();

        itemAdapter =new ItemAdapter(options);

        recyclerView = findViewById(R.id.Frag_item);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(itemAdapter);
        itemAdapter.SetOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                item i =documentSnapshot.toObject(item.class);
                // open new activity
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        itemAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        itemAdapter.stopListening();
    }

}
