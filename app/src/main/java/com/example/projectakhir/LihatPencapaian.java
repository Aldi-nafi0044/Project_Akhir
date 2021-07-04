package com.example.projectakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.projectakhir.adapter.Adapter;
import com.example.projectakhir.database.database;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;

public class LihatPencapaian extends AppCompatActivity  {
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<database> datapencapaian;
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_pencapaian);
        recyclerView = (RecyclerView) findViewById(R.id.rv_lihatpencapaian);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        BarChart barChart = findViewById(R.id.barChartpencapaian);



        ArrayList<BarEntry> visitors = new ArrayList<>();
        visitors.add(new BarEntry( 2015, 475));
        visitors.add(new BarEntry( 2016, 508));
        visitors.add(new BarEntry( 2017, 660));
        visitors.add(new BarEntry( 2018, 550));
        visitors.add(new BarEntry( 2019, 630));
        visitors.add(new BarEntry( 2020, 470));

        BarDataSet barDataSet = new BarDataSet(visitors,"Visitors");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Bar Chart Example");
        barChart.animateY(2000);

        databaseReference.child("database").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datapencapaian = new ArrayList<>();
                for(DataSnapshot daftarDS: snapshot.getChildren()){
                    database db = daftarDS.getValue(database.class);
                    db.setKode(daftarDS.getKey());

                    datapencapaian.add(db);
                }adapter = new Adapter(datapencapaian, LihatPencapaian.this);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getDetails()+""+error.getMessage());
            }
        });
    }

}