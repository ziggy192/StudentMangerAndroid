package com.example.admin.managerstundent.Ultils;

import com.example.admin.managerstundent.Remote.RetrofitClient;
import com.example.admin.managerstundent.Service.StudentDemoService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: DangNHH
 * 27/05/2018
 *
 * This class contain all interface service
 */
public class Common {

    private static final String BASE_URL = "http://myfirstazurewebapp20180423110404.azurewebsites.net/api/";
    public static final String weekdays[] = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};


    public static SimpleDateFormat formatter = new SimpleDateFormat();
    public static StudentDemoService getService(){
        return RetrofitClient.getClient(BASE_URL).create(StudentDemoService.class);
    }

    public static String dateFormater(String pattern, Date date) {
        formatter.applyPattern(pattern);
        return formatter.format(date);
    }

    public static String getWeekDay(int weekdayNum) {
        return weekdays[weekdayNum];
    }
}
