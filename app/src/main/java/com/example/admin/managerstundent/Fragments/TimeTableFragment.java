package com.example.admin.managerstundent.Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.managerstundent.Activity.SlotDetailActivity;
import com.example.admin.managerstundent.Activity.TableActivity;
import com.example.admin.managerstundent.R;
import com.example.admin.managerstundent.Ultils.BottomNavigationViewHelper;
import com.github.eunsiljo.timetablelib.data.TimeData;
import com.github.eunsiljo.timetablelib.data.TimeGridData;
import com.github.eunsiljo.timetablelib.data.TimeTableData;
import com.github.eunsiljo.timetablelib.view.TimeTableView;
import com.github.eunsiljo.timetablelib.viewholder.TimeTableItemViewHolder;
import com.sdsmdg.tastytoast.TastyToast;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeTableFragment extends Fragment {


    private TimeTableView timeTable;

    private List<String> mTitles = Arrays.asList("Japanese", "English", "Math", "Physics", "Chemistry", "Biology");
    private List<String> mHeaders = Arrays.asList("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");

    public TimeTableFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_table, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        timeTable = (TimeTableView)view.findViewById(R.id.timetabledummy);
        timeTable.setStartHour(5);
        initData();
    }


    private void initData(){
        final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        timeTable.setStartHour(0);
        timeTable.setShowHeader(true);
        timeTable.setTableMode(TimeTableView.TableMode.SHORT);
        DateTime now = DateTime.now();
        long mNow = getMillis("2018-06-29 03:00:00");
        // ArrayList<TimeTableData> dataTimeTable = getSamples(mNow, mHeaders, mTitles);
        ArrayList<TimeTableData> dataTimeTable = initDetailData();
        timeTable.setTimeTable(mNow, dataTimeTable);
        timeTable.setOnTimeItemClickListener(new TimeTableItemViewHolder.OnTimeItemClickListener() {
            @Override
            public void onTimeItemClick(View view, int position, TimeGridData item) {
                TimeData time = item.getTime();
                TastyToast.makeText(TimeTableFragment.this.getActivity(), time.getTitle() + ", " + sdf.format(new DateTime(time.getStartMills()).toDate()) +
                        " - " + sdf.format(new DateTime(time.getStopMills()).toDate()), TastyToast.LENGTH_SHORT, TastyToast.DEFAULT);
//                Toast.makeText(TableActivity.this, time.getTitle() + ", " + sdf.format(new DateTime(time.getStartMills()).toDate()) +
//                        " ~ " + sdf.format(new DateTime(time.getStopMills()).toDate()), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(TimeTableFragment.this.getActivity(), SlotDetailActivity.class);

                String teacherName = "Nghialq";
                String subjectName = "English";
                String timeStr = "13:00AM - 15:00PM";
                String dayOfWeek = "Monday";
                String roomName = "R01";
                Bundle bundle = new Bundle();
                bundle.putString(SlotDetailActivity.TEACHER_NAME_KEY, teacherName);
                bundle.putString(SlotDetailActivity.SUBJECT_NAME_KEY, subjectName);
                bundle.putString(SlotDetailActivity.TIME_KEY, timeStr);
                bundle.putString(SlotDetailActivity.DAYOFWEEK_KEY, dayOfWeek);
                bundle.putString(SlotDetailActivity.ROOM_NAME_KEY, roomName);
                intent.putExtra(SlotDetailActivity.PARAMS_KEY, bundle);
                startActivity(intent);

            }
        });
    }

    private ArrayList<TimeTableData> getSamples(long date, List<String> headers, List<String> titles){
        TypedArray colors_table = getResources().obtainTypedArray(R.array.colors_table);

        ArrayList<TimeTableData> tables = new ArrayList<>();
        for(int i=0; i<headers.size(); i++){
            ArrayList<TimeData> values = new ArrayList<>();
            DateTime start = new DateTime(date);
            DateTime end = start.plusMinutes((int)((Math.random() * 10) + 1) * 30);
            for(int j=0; j<titles.size(); j++){
                int color = colors_table.getResourceId(j, 0);
                int textColor = R.color.black;
                TimeData timeData = new TimeData(j, titles.get(j), color, textColor, start.getMillis(), end.getMillis());
                values.add(timeData);

                start = end.plusMinutes((int)((Math.random() * 10) + 1) * 10);
                end = start.plusMinutes((int)((Math.random() * 10) + 1) * 30);
            }

            tables.add(new TimeTableData(headers.get(i), values));
        }
        return tables;
    }



    private ArrayList<TimeTableData> initDetailData(){
        ArrayList<TimeTableData> dataGrid = new ArrayList<>();
        ArrayList<TimeData> values = new ArrayList<>();
        values.add(new TimeData(0, "Japanese", R.color.color_table_1_light, getMillis("2018-06-29 11:00:00"), getMillis("2018-06-29 13:00:00")));
        values.add(new TimeData(1, "English", R.color.color_table_2_light, getMillis("2018-06-29 07:00:00"), getMillis("2018-06-29 09:00:00")));

        ArrayList<TimeData> values2 = new ArrayList<>();
        values.add(new TimeData(0, "Japanese", R.color.color_table_1_light, getMillis("2018-06-29 13:30:00"), getMillis("2018-06-29 15:00:00")));
        values2.add(new TimeData(1, "English", R.color.color_table_2_light, getMillis("2018-06-29 07:30:00"), getMillis("2018-06-29 09:30:00")));
        values2.add(new TimeData(2, "Math 9", R.color.color_table_3_light, getMillis("2018-06-29 11:40:00"), getMillis("2018-06-29 13:45:00")));
        values2.add(new TimeData(4, "Physics 10", R.color.color_table_5_light, getMillis("2018-06-29 14:00:00"), getMillis("2018-06-29 15:30:00")));
        values.add(new TimeData(5, "Chemistry 11", R.color.color_table_6_light, getMillis("2018-06-29 16:30:00"), getMillis("2018-06-29 18:00:00")));
        values.add(new TimeData(6, "Biology 11", R.color.color_table_7_light, getMillis("2018-06-29 13:30:00"), getMillis("2018-06-29 15:45:00")));

        ArrayList<TimeTableData> tables = new ArrayList<>();
        tables.add(new TimeTableData("Sun", values));
        tables.add(new TimeTableData("Mon", values2));
        tables.add(new TimeTableData("Tue", values));
        tables.add(new TimeTableData("Wed", values2));
        tables.add(new TimeTableData("Thu", values));
        tables.add(new TimeTableData("Fri", values2));
        tables.add(new TimeTableData("Sat", values));
        dataGrid.addAll(tables);
        return dataGrid;
    }
    private long getMillis(String day){
        DateTime date = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(day);
        return date.getMillis();
    }


}
