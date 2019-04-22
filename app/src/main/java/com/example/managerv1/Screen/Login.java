package com.example.managerv1.Screen;

import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;


import com.example.managerv1.Model.Staff;
import com.example.managerv1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;


public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void onLoginClick(View view){
//        test();
        Intent i=new Intent(this,Home.class);
        startActivity(i);
    }

    private void test() {
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference staffadd = db.collection("staffs");
        DocumentReference s =db.collection("staffs").document("profile");
        Staff staff=new Staff("Do Hoang Viet Anh",new Date(1997,05,12),"Thai Binh","094532133");
        staffadd.document("NV01").set(staff).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }

}
