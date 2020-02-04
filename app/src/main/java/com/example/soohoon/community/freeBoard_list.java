package com.example.soohoon.community;


class freeBoard_list {

    String content;
    String number;
    String nic;
    String date;
    String userID;
    String userPer;


    public freeBoard_list(String number, String date, String content, String nic, String userID,String userPer    ) {
        this.number =number;
        this.date=date;
        this.content=content;
        this.nic=nic;
        this.userID=userID;
        this.userPer=userPer;
    }

    public String getUserPer() {
        return userPer;
    }

    public void setUserPer(String userPer) {
        this.userPer = userPer;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public String getNumber() {
        return number;
    }



    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
