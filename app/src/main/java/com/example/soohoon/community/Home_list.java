package com.example.soohoon.community;

class Home_list {

    String number;
    String title;
    private String hit;
    String date;
    String content;
    private String nic;
    int cnt;

    Home_list(String number, String title, String date, String nic, String content,int cnt) {
        this.content=content;
        this.number = number;
        this.title = title;
        this.date = date;
        this.nic = nic;
        this.cnt = cnt;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHit() {
        return hit;
    }

    public void setHit(String hit) {
        this.hit = hit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }
}
