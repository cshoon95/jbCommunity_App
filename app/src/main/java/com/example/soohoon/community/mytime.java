package com.example.soohoon.community;


public  class mytime {
    int courseYear;
    int courseCredit;
    int coursePersonnel;
    String courseProfessor;
    String courseTerm;
    String courseTitle;
    String courseTime;
    String courseClass;
    int courseRival;
    private int courseID;
    int courseGrade;
    String userID;


    public mytime() {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.courseCredit = courseCredit;
        this.coursePersonnel = coursePersonnel;
        this.courseGrade = courseGrade;
        this.courseRival =courseRival;

    }

    public mytime(String userID,int courseCredit, String courseProfessor, String courseTime, String courseTitle, int courseGrade, int courseID) {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.courseCredit = courseCredit;
        this.courseTime = courseTime;
        this.courseGrade = courseGrade;
        this.courseProfessor = courseProfessor;
        this.userID =userID;
    }




    public int getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(int courseYear) {
        this.courseYear = courseYear;
    }

    public int getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(int courseCredit) {
        this.courseCredit = courseCredit;
    }

    public int getCoursePersonnel() {
        return coursePersonnel;
    }

    public void setCoursePersonnel(int coursePersonnel) {
        this.coursePersonnel = coursePersonnel;
    }

    public String getCourseProfessor() {
        return courseProfessor;
    }

    public void setCourseProfessor(String courseProfessor) {
        this.courseProfessor = courseProfessor;
    }

    public String getCourseTerm() {
        return courseTerm;
    }

    public void setCourseTerm(String courseTerm) {
        this.courseTerm = courseTerm;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getCourseClass() {
        return courseClass;
    }

    public void setCourseClass(String courseClass) {
        this.courseClass = courseClass;
    }

    public int getCourseRival() {
        return courseRival;
    }

    public void setCourseRival(int courseRival) {
        this.courseRival = courseRival;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getCourseGrade() {
        return courseGrade;
    }

    public void setCourseGrade(int courseGrade) {
        this.courseGrade = courseGrade;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
