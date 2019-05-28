package com.example.managercoffee.UI;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.managercoffee.MODEL.item_order;
import com.example.managercoffee.MODEL.order;
import com.example.managercoffee.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;



public class Day_overview extends AppCompatActivity {
    private final FirebaseFirestore db =FirebaseFirestore.getInstance();
    private String date;
    private static final String INDEX="NAME";
    private BarChart item_chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_overview);
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true)
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        db.setFirestoreSettings(settings);
        Toolbar toolbar= findViewById(R.id.day_overview_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setDisplayHomeAsUpEnabled(true);

        initChart();


        Intent intent = getIntent();
        date=intent.getStringExtra(INDEX);



    }

    private void initChart() {
        item_chart =findViewById(R.id.item_chart);
        item_chart.getDescription().setEnabled(false);
        item_chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                XAxis xAxis=item_chart.getXAxis();
                Toast.makeText(getApplicationContext(),xAxis.getFormattedLabel((int)e.getX()), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        YAxis RyAxis = item_chart.getAxisRight();
        RyAxis.setDrawGridLines(false);
        RyAxis.setAxisMinimum(0f);
        YAxis LyAxis =item_chart.getAxisLeft();
        LyAxis.setDrawGridLines(false);
        LyAxis.setAxisMinimum(0f);
        XAxis xAxis =item_chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
    }


    @Override
    protected void onStart() {
        super.onStart();
        getData();

    }

    private void getData() {
        db.collection("order").whereEqualTo("date",date).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            List<order> orderList =new ArrayList<>();
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                orderList = queryDocumentSnapshots.toObjects(order.class);
                exeValue(orderList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void exeValue(List<order> orderList) {
        List<item_order> item_orderList =new ArrayList<>();
        for(order o : orderList){
            for(item_order item : o.getOrderlist()){
                if(itemExist(item_orderList,item)){
                    item_orderList=setAmount(item_orderList,item);
                }
                else{
                    item_orderList.add(item);
                }
            }
        }
        setupItemChart(item_orderList);
    }

    private List<item_order> setAmount(List<item_order> item_orderList, item_order item) {
        for(item_order i : item_orderList){
            if(i.getName().equals(item.getName())){
                i.setAmmount(i.getAmmount()+item.getAmmount());
            }
        }
        return item_orderList;
    }

    private void setupItemChart(final List<item_order> item_orderList) {
        XAxis xAxis =item_chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(){
            @Override
            public String getFormattedValue(float value) {
                if(value>=0){
                    if(value<=item_orderList.size()-1){
                        return item_orderList.get((int) value).getName();
                    }
                    return "";
                }
                return "";
            }
        });
        BarData barData =new BarData();
        barData.addDataSet((IBarDataSet) setData(item_orderList));

        xAxis.setAxisMaximum(barData.getXMax() + 0.5f);
        item_chart.setData(barData);
        item_chart.invalidate();
    }

    private DataSet setData(List<item_order> item_orderList) {
        BarData barData =new BarData();
        List<BarEntry> entries = new ArrayList<BarEntry>();
        for(int i=0;i<item_orderList.size();i++){
            entries.add(new BarEntry(i+0.5f,item_orderList.get(i).getAmmount()));
        }
        BarDataSet set =new BarDataSet(entries,"Sold");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setDrawValues(true);
        set.setValueTextColor(Color.rgb(150, 123, 182));
        set.setValueTextSize(10f);
        barData.addDataSet(set);
        return set;
    }


    private boolean itemExist(List<item_order> item_orderList, item_order item) {
        for(item_order i : item_orderList){
            if(i.getName().equals(item.getName())){
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

}
