package com.example.projectakhir;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectakhir.database.database;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private TextView timer,tvcalender;
    private Button btnulang, btncapai, btnmulai;
    Long StartTime, milisecond;
    long seconds,seconds2;
    long minutes, minutes2;
    long hours,hours2;
    long days;
    Handler handler;
    String tmr, cldr, currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvcalender = (TextView)findViewById(R.id.tvdate);
        timer = (TextView)findViewById(R.id.tvTimer);
        btnulang = (Button)findViewById(R.id.btnmengulang);
        btncapai = (Button)findViewById(R.id.btnpencapai);
        btnmulai = (Button) findViewById(R.id.btnstart);

        handler = new Handler();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        /*untuk kalender*/
        Date calendar = Calendar.getInstance().getTime();
        currentDate = DateFormat.getDateInstance(DateFormat.FULL).getDateTimeInstance().format(Calendar.getInstance().getTime());
        tvcalender.setText(currentDate);

        /*selesai*/

        btncapai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this,LihatPencapaian.class);
                startActivity(intent);
            }
        });


        btnulang.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                handler.removeCallbacks(runnable, timer);
                tmr = timer.getText().toString();
                cldr = tvcalender.getText().toString();

                simpanData(new database(tmr,cldr));

                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable,0);
            }
        });
        btnmulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable,0);
                btnmulai.setEnabled(false);
            }
        });
    }
    public Runnable runnable = new Runnable() {
        @Override
        public void run(){
            milisecond = SystemClock.uptimeMillis()- StartTime;
            seconds = milisecond / 1000; seconds2 = seconds % 60;
            minutes = seconds / 60; minutes2 = minutes % 60;
            hours = minutes / 60; hours2 = hours % 24;
            days = hours / 24;
            timer.setText(days + ":" + String.format("%02d",hours2) + ":" + String.format("%02d",minutes2) + ":" + String.format("%02d",seconds2) );
            handler.postDelayed(this,0);
        }
    };
    private void simpanData(database db){
        databaseReference.child("database").push().setValue(db).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                timer.setText("");
                tvcalender.setText(currentDate);

                Toast.makeText(MainActivity.this,"mengulang dan data telah tersimpan",Toast.LENGTH_LONG).show();
            }
        });
    }
}