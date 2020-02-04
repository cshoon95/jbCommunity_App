package com.example.soohoon.community;

import android.content.Context;
import android.widget.TextView;

import org.apache.http.conn.ConnectionPoolTimeoutException;

public class Schedule {



    private String monday[] = new String[12];
    private String tuesday[] = new String[12];
    private String wendesday[] = new String[12];
    private String thursday[] = new String[12];
    private String friday[] = new String[12];



    public Schedule() {

        for (int i = 0; i < 12; i++) {
            monday[i] = "";
            tuesday[i] = "";
            wendesday[i] = "";
            thursday[i] = "";
            friday[i] = "";
        }
    }

    public void addSchedule(String courseTime) {
        int temp;
        //월:[3][4][5]강의정보 불러오게해줌
        if ((temp = courseTime.indexOf("월")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < courseTime.length() && courseTime.charAt(i) != ':'; i++) {
                if (courseTime.charAt(i) == '[') {
                    startPoint = i;
                }
                if (courseTime.charAt(i) == ']') {
                    endPoint = i;
                    monday[Integer.parseInt(courseTime.substring(startPoint + 1, endPoint))] = "수업";
                }
            }
        }

        if ((temp = courseTime.indexOf("화")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < courseTime.length() && courseTime.charAt(i) != ':'; i++) {
                if (courseTime.charAt(i) == '[') {
                    startPoint = i;
                }
                if (courseTime.charAt(i) == ']') {
                    endPoint = i;
                    tuesday[Integer.parseInt(courseTime.substring(startPoint + 1, endPoint))] = "수업";
                }
            }

        }
        if ((temp = courseTime.indexOf("수")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < courseTime.length() && courseTime.charAt(i) != ':'; i++) {
                if (courseTime.charAt(i) == '[') {
                    startPoint = i;
                }
                if (courseTime.charAt(i) == ']') {
                    endPoint = i;
                    wendesday[Integer.parseInt(courseTime.substring(startPoint + 1, endPoint))] = "수업";
                }
            }
        }
        if ((temp = courseTime.indexOf("목")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < courseTime.length() && courseTime.charAt(i) != ':'; i++) {
                if (courseTime.charAt(i) == '[') {
                    startPoint = i;
                }
                if (courseTime.charAt(i) == ']') {
                    endPoint = i;
                    thursday[Integer.parseInt(courseTime.substring(startPoint + 1, endPoint))] = "수업";
                }
            }
        }
        if ((temp = courseTime.indexOf("금")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < courseTime.length() && courseTime.charAt(i) != ':'; i++) {
                if (courseTime.charAt(i) == '[') {
                    startPoint = i;
                }
                if (courseTime.charAt(i) == ']') {
                    endPoint = i;
                    friday[Integer.parseInt(courseTime.substring(startPoint + 1, endPoint))] = "수업";
                }
            }
        }
    }


    public boolean validate(String courseTime) {
        if (courseTime.equals(""))
        {
            return true;
        }
        int temp;
        if ((temp = courseTime.indexOf("월")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < courseTime.length() && courseTime.charAt(i) != ':'; i++) {
                if (courseTime.charAt(i) == '[') {
                    startPoint = i;
                }
                if (courseTime.charAt(i) == ']') {
                    endPoint = i;
                    if (!monday[Integer.parseInt(courseTime.substring(startPoint + 1, endPoint))].equals("")) {
                        return false;
                    }
                }
            }
        }
        if ((temp = courseTime.indexOf("화")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < courseTime.length() && courseTime.charAt(i) != ':'; i++) {
                if (courseTime.charAt(i) == '[') {
                    startPoint = i;
                }
                if (courseTime.charAt(i) == ']') {
                    endPoint = i;
                    if (!tuesday[Integer.parseInt(courseTime.substring(startPoint + 1, endPoint))].equals("")) {
                        return false;
                    }
                }
            }
        }
        if ((temp = courseTime.indexOf("수")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < courseTime.length() && courseTime.charAt(i) != ':'; i++) {
                if (courseTime.charAt(i) == '[') {
                    startPoint = i;
                }
                if (courseTime.charAt(i) == ']') {
                    endPoint = i;
                    if (!wendesday[Integer.parseInt(courseTime.substring(startPoint + 1, endPoint))].equals("")) {
                        return false;
                    }
                }
            }
        }
        if ((temp = courseTime.indexOf("목")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < courseTime.length() && courseTime.charAt(i) != ':'; i++) {
                if (courseTime.charAt(i) == '[') {
                    startPoint = i;
                }
                if (courseTime.charAt(i) == ']') {
                    endPoint = i;
                    if (!thursday[Integer.parseInt(courseTime.substring(startPoint + 1, endPoint))].equals("")) {
                        return false;
                    }
                }
            }
        }
        if ((temp = courseTime.indexOf("금")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < courseTime.length() && courseTime.charAt(i) != ':'; i++) {
                if (courseTime.charAt(i) == '[') {
                    startPoint = i;
                }
                if (courseTime.charAt(i) == ']') {
                    endPoint = i;
                    if(!friday[Integer.parseInt(courseTime.substring(startPoint + 1, endPoint))].equals(""))
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void addSchedule(String courseTime, String courseTitle, String courseProfessor)
    {
        String professor;
        if(courseProfessor.equals(""))
        {
            professor= "";
        }
        else
        {
            professor = "(" + courseProfessor + ")";
        }
        int temp;
        //월:[3][4][5]강의정보 불러오게해줌
        if ((temp = courseTime.indexOf("월")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < courseTime.length() && courseTime.charAt(i) != ':'; i++) {
                if (courseTime.charAt(i) == '[') {
                    startPoint = i;
                }
                if (courseTime.charAt(i) == ']') {
                    endPoint = i;
                    monday[Integer.parseInt(courseTime.substring(startPoint + 1, endPoint))] = courseTitle + professor;
                }
            }
        }

        if ((temp = courseTime.indexOf("화")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < courseTime.length() && courseTime.charAt(i) != ':'; i++) {
                if (courseTime.charAt(i) == '[') {
                    startPoint = i;
                }
                if (courseTime.charAt(i) == ']') {
                    endPoint = i;
                    tuesday[Integer.parseInt(courseTime.substring(startPoint + 1, endPoint))] = courseTitle + professor;
                }
            }

        }
        if ((temp = courseTime.indexOf("수")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < courseTime.length() && courseTime.charAt(i) != ':'; i++) {
                if (courseTime.charAt(i) == '[') {
                    startPoint = i;
                }
                if (courseTime.charAt(i) == ']') {
                    endPoint = i;
                    wendesday[Integer.parseInt(courseTime.substring(startPoint + 1, endPoint))] = courseTitle + professor;
                }
            }
        }
        if ((temp = courseTime.indexOf("목")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < courseTime.length() && courseTime.charAt(i) != ':'; i++) {
                if (courseTime.charAt(i) == '[') {
                    startPoint = i;
                }
                if (courseTime.charAt(i) == ']') {
                    endPoint = i;
                    thursday[Integer.parseInt(courseTime.substring(startPoint + 1, endPoint))] = courseTitle + professor;
                }
            }
        }
        if ((temp = courseTime.indexOf("금")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < courseTime.length() && courseTime.charAt(i) != ':'; i++) {
                if (courseTime.charAt(i) == '[') {
                    startPoint = i;
                }
                if (courseTime.charAt(i) == ']') {
                    endPoint = i;
                    friday[Integer.parseInt(courseTime.substring(startPoint + 1, endPoint))] = courseTitle + professor;
                }
            }
        }
    }



    public void setting(AutoResizeTextView[] monday, AutoResizeTextView[] tuesday, AutoResizeTextView[] wendesday, AutoResizeTextView[] thursday, AutoResizeTextView[] friday, Context context)
    {

        int maxLength = 0;
        String maxString = "";
        for (int i =0; i<12; i++)
        {
            if(this.monday[i].length() > maxLength)
            {
                maxLength = this.monday[i].length();
                maxString = this.monday[i];
            }

            if(this.tuesday[i].length() > maxLength)
            {
                maxLength = this.tuesday[i].length();
                maxString = this.tuesday[i];
            }
            if(this.wendesday[i].length() > maxLength)
            {
                maxLength = this.wendesday[i].length();
                maxString = this.wendesday[i];
            }
            if(this.thursday[i].length() > maxLength)
            {
                maxLength = this.thursday[i].length();
                maxString = this.thursday[i];
            }
            if(this.friday[i].length() > maxLength)
            {
                maxLength = this.friday[i].length();
                maxString = this.friday[i];
            }

        }

        for(int i = 0; i <12; i++)
        {
            if(!this.monday[i].equals(""))
            {
                monday[i].setText(this.monday[i]); //해당배열의 내용이(monday[i]) monday[i]에 들어감
                monday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            }
            else {
                monday[i].setText(maxString);
            }
            if(!this.tuesday[i].equals(""))
            {
                tuesday[i].setText(this.tuesday[i]);
                tuesday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            }
            else {
                tuesday[i].setText(maxString);
            }
            if(!this.wendesday[i].equals(""))
            {
                wendesday[i].setText(this.wendesday[i]);
                wendesday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            }
            else {
                wendesday[i].setText(maxString);
            }
            if(!this.thursday[i].equals(""))
            {
                thursday[i].setText(this.thursday[i]);
                thursday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            }
            else {
                thursday[i].setText(maxString);
            }
            if(!this.friday[i].equals(""))
            {
                friday[i].setText(this.friday[i]);
                friday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            }
            else {
                friday[i].setText(maxString);
            }
        }
    }
}