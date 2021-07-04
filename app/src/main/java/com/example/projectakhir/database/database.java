package com.example.projectakhir.database;

import java.io.Serializable;

public class database implements Serializable {
    String kode;
    String timer;
    String Calender;

    public database() {
    }

    public database(String timer, String calender) {
        this.timer = timer;
        this.Calender = calender;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getCalender() {
        return Calender;
    }

    public void setCalender(String calender) {
        Calender = calender;
    }

    @Override
    public String toString() {
        return "database{" +
                "timer='" + timer + '\'' +
                ", Calender='" + Calender + '\'' +
                '}';
    }
}
