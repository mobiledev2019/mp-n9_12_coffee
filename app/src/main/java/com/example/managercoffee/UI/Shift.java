package com.example.managercoffee.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.managercoffee.MODEL.DayCount;
import com.example.managercoffee.R;
import com.firebase.ui.firestore.FirestoreArray;
import com.google.firebase.firestore.Query;

public class Shift extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift);
    }
}
