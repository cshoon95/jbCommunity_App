package com.example.soohoon.community;

import android.content.SharedPreferences;

public class Course {
     int courseYear;
     int courseCredit;
     int coursePersonnel;
     String courseProfessor;
     String courseTerm;
     String courseTitle;
     String courseTime;
    String courseClass;
    int courseRival;
    public int courseID;
    int courseGrade;
    String userID;

    public Course() {
        this.courseID = courseID;
        this.courseYear = courseYear;
        this.courseTerm = courseTerm;
        this.courseClass = courseClass;
        this.courseTitle = courseTitle;
        this.courseCredit = courseCredit;
        this.coursePersonnel = coursePersonnel;
        this.courseProfessor = courseProfessor;
        this.courseTime = courseTime;
        this.courseGrade = courseGrade;
        this.courseRival =courseRival;
        this.userID =userID;
    }

    public Course(int coursePersonnel, String courseTitle, int courseRival, int courseID, int courseGrade) {
        this.coursePersonnel = coursePersonnel;
        this.courseTitle = courseTitle;
        this.courseRival = courseRival;
        this.courseID = courseID;
        this.courseGrade = courseGrade;
    }

    public Course(int coursePersonnel, String courseTitle, int courseRival, int courseID, int courseGrade, int courseCredit) {
        this.coursePersonnel = coursePersonnel;
        this.courseTitle = courseTitle;
        this.courseRival = courseRival;
        this.courseID = courseID;
        this.courseGrade = courseGrade;
        this.courseCredit = courseCredit;
    }

    public int getCourseRival() {
        return courseRival;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setCourseRival(int courseRival) {
        this.courseRival = courseRival;
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
}
