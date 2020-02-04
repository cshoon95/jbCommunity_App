package com.example.soohoon.community;

public class Bus {

    String busarea; // 백, 삼, 화
    String busTime;
    String busgoback; // 등교, 하교
    String busNo; // 버스 번호

    public String getBusarea() {
        return busarea;
    }

    public void setBusarea(String busarea) {
        this.busarea = busarea;
    }

    public String getBusTime() {
        return busTime;
    }

    public void setBusTime(String busTime) {
        this.busTime = busTime;
    }

    public String getBusgoback() {
        return busgoback;
    }

    public void setBusgoback(String busgoback) {
        this.busgoback = busgoback;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public Bus(String busarea, String busTime, String busNo) {
        this.busarea = busarea;
        this.busTime = busTime;
        this.busNo = busNo;
    }
}
