package com.example.managercoffee.UI;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import com.github.mikephil.charting.data.DataSet;

import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.managercoffee.MODEL.DayCount;
import com.example.managercoffee.R;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.collect.Lists;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class dashbroad extends AppCompatActivity{
    private final FirebaseFirestore db =FirebaseFirestore.getInstance();
    private DrawerLayout drawerLayout;
    private ListenerRegistration listenerRegistration;
    private CombinedChart incomeChart;
    private ProgressBar progressBar;
    private static final String INDEX="NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbroad);



        progressBar=findViewById(R.id.progressbar);
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true)
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        db.setFirestoreSettings(settings);
        Toolbar toolbar = findViewById(R.id.dashbroad_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer);

        initChart();



        Button button =findViewById(R.id.income_status);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Income_overview.class));
            }
        });
    }

    private void initChart() {
        incomeChart =findViewById(R.id.income_chart);
        incomeChart.getDescription().setEnabled(false);
        incomeChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                XAxis xAxis=incomeChart.getXAxis();
                Intent intent =new Intent(getApplicationContext(),Day_overview.class);
                intent.putExtra(INDEX,xAxis.getFormattedLabel((int)e.getX()+1));
                startActivity(intent);
            }

            @Override
            public void onNothingSelected() {

            }
        });
        YAxis RyAxis = incomeChart.getAxisRight();
        RyAxis.setDrawGridLines(false);
        RyAxis.setAxisMinimum(0f);
        YAxis LyAxis =incomeChart.getAxisLeft();
        LyAxis.setDrawGridLines(false);
        LyAxis.setAxisMinimum(0f);
        XAxis xAxis =incomeChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                drawerLayout.openDrawer(Gravity.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onItemView(View view){
        Intent intent =new Intent(this,Item.class);
        startActivity(intent);
    }
    public void onStaffView(View view){
        Intent intent =new Intent(this,Staff.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadChartData();

        listenerRegistration = db.collection("day").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        listenerRegistration.remove();
    }


    private void loadChartData(){
        db.collection("day").orderBy("day",Query.Direction.DESCENDING).limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DayCount> dayList = queryDocumentSnapshots.toObjects(DayCount.class);
                dayList=new ArrayList<DayCount>(Lists.reverse(dayList));
                setupChart(dayList);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"FAIL TO CONNECT TO FIRECLOUD!",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setupChart(final List<DayCount> dayCounts){

        XAxis xAxis =incomeChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(){
            @Override
            public String getFormattedValue(float value) {
                if(value>=0){
                    if(value<=dayCounts.size()-1){
                        return dayCounts.get((int) value).getDay();
                    }
                    return "";
                }
                return "";
            }
        });
        CombinedData combinedData =new CombinedData();
        BarData barData =new BarData();
        barData.addDataSet((IBarDataSet) getIncome(dayCounts));

        LineData lineData=new LineData();
        lineData.addDataSet((ILineDataSet) getItemAmmount(dayCounts));
        combinedData.setData(barData);
        combinedData.setData(lineData);
        xAxis.setAxisMaximum(combinedData.getXMax() + 0.5f);
        incomeChart.setData(combinedData);
        progressBar.setVisibility(ProgressBar.GONE);
        incomeChart.invalidate();

    }
    private DataSet getIncome(final List<DayCount> dayCounts){
        BarData barData =new BarData();
        List<BarEntry> entries = new ArrayList<BarEntry>();
        for(int i=0;i<dayCounts.size();i++){
            entries.add(new BarEntry(i+0.5f,dayCounts.get(i).getIncome()));
        }
        BarDataSet set =new BarDataSet(entries,"Income");
        set.setColor(Color.rgb(150, 123, 182));
        set.setDrawValues(true);
        set.setValueTextColor(Color.rgb(150, 123, 182));
        set.setValueTextSize(10f);
        barData.addDataSet(set);
        return set;
    }
    private DataSet getItemAmmount(final List<DayCount> dayCounts){
        LineData lineData =new LineData();
        List<Entry> entries = new ArrayList<Entry>();
        for(int i=0;i<dayCounts.size();i++){
            entries.add(new Entry(i+0.5f,dayCounts.get(i).getItemcount()));
        }

        LineDataSet set = new LineDataSet(entries, "Items sold");
        set.setColor(Color.rgb(240, 238, 70));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));
        lineData.addDataSet(set);
        return set;
    }

}
