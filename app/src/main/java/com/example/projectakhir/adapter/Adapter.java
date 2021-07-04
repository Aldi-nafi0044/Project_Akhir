package com.example.projectakhir.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectakhir.R;
import com.example.projectakhir.database.database;
import com.github.mikephil.charting.charts.BarChart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    private ArrayList<database> daftar;
    private Context context;
    private DatabaseReference databaseReference;

    public Adapter(ArrayList<database> daftar, Context context) {
        this.daftar = daftar;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pencapaian, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull  Adapter.ViewHolder holder, int position) {
        String kode, timer, calender;
        timer = daftar.get(position).getTimer();
        calender = daftar.get(position).getCalender();
        kode = daftar.get(position).getKode();
        holder.tv_waktu.setText(timer);
        holder.tv_tanggal.setText(calender);

    }

    @Override
    public int getItemCount() {
        return daftar.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_tanggal, tv_waktu;

        ViewHolder(View v){
            super(v);
            tv_tanggal = (TextView)v.findViewById(R.id.tvtangal);
            tv_waktu = (TextView)v.findViewById(R.id.tvwaktu);

            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
    }

}
