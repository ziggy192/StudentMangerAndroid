package com.example.admin.managerstundent.Ultils;

import com.example.admin.managerstundent.R;
import com.example.admin.managerstundent.Remote.RetrofitClient;
import com.example.admin.managerstundent.Service.StudentDemoService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: DangNHH
 * 27/05/2018
 * <p>
 * This class contain all interface service
 */
public class Common {

    public static final String weekdays[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private static final String BASE_URL = "http://myfirstazurewebapp20180423110404.azurewebsites.net/api/";
    public static SimpleDateFormat formatter = new SimpleDateFormat();
//    public static StudentDemoService getService(){
//        return RetrofitClient.getClient(BASE_URL).create(StudentDemoService.class);
//    }

    public static String dateFormater(String pattern, Date date) {
        formatter.applyPattern(pattern);
        return formatter.format(date);
    }


    public static int getColorsFromClassId(int classId) {
        int result;
        switch (classId) {
            case 0:
                result = R.color.color_table_1_light;
                break;
            case 1:
                result = R.color.color_table_2_light;
                break;

            case 2:

                result = R.color.color_table_3_light;
                break;


            case 3:
                result = R.color.color_table_4_light;

                break;
            case 4:
                result = R.color.color_table_5_light;

                break;
            case 5:
                result = R.color.color_table_6_light;

                break;
            case 6:
                result = R.color.color_table_7_light;

                break;
            case 7:
                result = R.color.color_table_8_light;

                break;
            case 8:
                result = R.color.color_table_9_light;

                break;
            case 9:
                result = R.color.color_table_10_light;

                break;
            default:
                result = R.color.color_table_1_light;
                break;
        }
        return result;

    }


    public static String getWeekDay(int weekdayNum) {
        return weekdays[weekdayNum];
    }
}
