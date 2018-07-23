package com.example.admin.managerstundent.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.managerstundent.Activity.SlotDetailActivity;
import com.example.admin.managerstundent.Constant.Constant;
import com.example.admin.managerstundent.Entity.ClassDetail;
import com.example.admin.managerstundent.Entity.TimeSlotModel;
import com.example.admin.managerstundent.HttpServices.HttpHelper;
import com.example.admin.managerstundent.R;
import com.example.admin.managerstundent.Ultils.Common;
import com.example.admin.managerstundent.Ultils.DummyDatabase;
import com.github.eunsiljo.timetablelib.data.TimeData;
import com.github.eunsiljo.timetablelib.data.TimeGridData;
import com.github.eunsiljo.timetablelib.data.TimeTableData;
import com.github.eunsiljo.timetablelib.view.TimeTableView;
import com.github.eunsiljo.timetablelib.viewholder.TimeTableItemViewHolder;
import com.sdsmdg.tastytoast.TastyToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
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


    private static final String TAG = TimeTableFragment.class.toString();
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
        initData();
        //todo first init from data from database
        ArrayList<TimeTableData> dataTimeTable = getTimeTableListFromClassDetailList(DummyDatabase.getClassDetailsToMakeTimetable());
        setupUI(dataTimeTable);

        //todo then setupUI again when receive classDetailsEvent
//        setupUI();
    }


    private void initData(){
        timeTable.setStartHour(0);
        timeTable.setShowHeader(true);
        timeTable.setTableMode(TimeTableView.TableMode.SHORT);


    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(sticky = true)
    public void onGetClassDetailsFromStudentId(HttpHelper.GetClassDetailsListStudentIdResponseEvent event) {
        if (event.isSuccess()) {

            List<ClassDetail> classDetailList = event.getClassDetailList();

            DummyDatabase.setClassDetailsToMakeTimetable(classDetailList);

            Log.d(TAG, "onGetClassDetailsFromStudentId: Success, setting UI..");

            ArrayList<TimeTableData> dataTimeTable = getTimeTableListFromClassDetailList(classDetailList);
            setupUI(dataTimeTable);
        } else {
            Log.d(TAG, "onGetClassDetailsFromStudentId: Failure");

        }
        EventBus.getDefault().removeStickyEvent(event);

    }
    private void setupUI( ArrayList<TimeTableData> dataTimeTable) {
        final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

        long mNow = getDateTimeFromString("00:00am").getMillis();
        // ArrayList<TimeTableData> dataTimeTable = getSamples(mNow, mHeaders, mTitles);


        timeTable.setTimeTable(mNow, dataTimeTable);
        timeTable.setOnTimeItemClickListener(new TimeTableItemViewHolder.OnTimeItemClickListener() {
            @Override
            public void onTimeItemClick(View view, int position, TimeGridData item) {
                TimeData time = item.getTime();
                TastyToast.makeText(TimeTableFragment.this.getActivity(), time.getTitle() + ", " + sdf.format(new DateTime(time.getStartMills()).toDate()) +
                        " - " + sdf.format(new DateTime(time.getStopMills()).toDate()), TastyToast.LENGTH_SHORT, TastyToast.DEFAULT);
//                Toast.makeText(TableActivity.this, time.getTitle() + ", " + sdf.format(new DateTime(time.getStartMills()).toDate()) +
//                        " ~ " + sdf.format(new DateTime(time.getStopMills()).toDate()), Toast.LENGTH_SHORT).show();


                int row = item.getRowCount();
                int column = position;
                int classDetailId = (int) item.getTime().getKey();
                Log.d(TAG, String.format("onTimeItemClick: row=%s,column=%s,key=%s", row, column,classDetailId));
                Intent intent = new Intent(TimeTableFragment.this.getActivity(), SlotDetailActivity.class);
                //todo setClassDetailHere, not dummy data
                ClassDetail choosedClassDetail = new ClassDetail(0, "English 1");;
                for (ClassDetail classDetail1 :
                        DummyDatabase.getClassDetailsToMakeTimetable()) {
                    if (classDetail1.getClassId() == classDetailId) {
                        choosedClassDetail = classDetail1;
                    }
                }


//                String teacherName = "Nghialq";
//                String subjectName = "English";
//                String timeStr = "13:00AM - 15:00PM";
//                String dayOfWeek = "Monday";
//                String roomName = "R01";
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.CLASS_DETAIL_MODEL_KEY, choosedClassDetail);
//                bundle.putString(SlotDetailActivity.TEACHER_NAME_KEY, teacherName);
//                bundle.putString(SlotDetailActivity.SUBJECT_NAME_KEY, subjectName);
//                bundle.putString(SlotDetailActivity.TIME_KEY, timeStr);
//                bundle.putString(SlotDetailActivity.DAYOFWEEK_KEY, dayOfWeek);
//                bundle.putString(SlotDetailActivity.ROOM_NAME_KEY, roomName);
//                intent.putExtra(SlotDetailActivity.PARAMS_KEY, bundle);
                startActivity(intent);

            }
        });
    }
//
//    private ArrayList<TimeTableData> getSamples(long date, List<String> headers, List<String> titles){
//        TypedArray colors_table = getResources().obtainTypedArray(R.array.colors_table);
//
//        ArrayList<TimeTableData> tables = new ArrayList<>();
//        for(int i=0; i<headers.size(); i++){
//            ArrayList<TimeData> values = new ArrayList<>();
//            DateTime start = new DateTime(date);
//            DateTime end = start.plusMinutes((int)((Math.random() * 10) + 1) * 30);
//            for(int j=0; j<titles.size(); j++){
//                int color = colors_table.getResourceId(j, 0);
//                int textColor = R.color.black;
//                TimeData timeData = new TimeData(j, titles.get(j), color, textColor, start.getMillis(), end.getMillis());
//                values.add(timeData);
//
//                start = end.plusMinutes((int)((Math.random() * 10) + 1) * 10);
//                end = start.plusMinutes((int)((Math.random() * 10) + 1) * 30);
//            }
//
//            tables.add(new TimeTableData(headers.get(i), values));
//        }
//        return tables;
//    }

    private ArrayList<TimeTableData> getTimeTableListFromClassDetailList(List<ClassDetail> classDetails) {
        ArrayList<TimeTableData> result = new ArrayList<>();

        result.add(new TimeTableData("Mon", getTimeDataFromDayOfWeek(classDetails, "Monday")));
        result.add(new TimeTableData("Tue", getTimeDataFromDayOfWeek(classDetails, "Tuesday")));
        result.add(new TimeTableData("Wed", getTimeDataFromDayOfWeek(classDetails, "Wednesday")));
        result.add(new TimeTableData("Thu", getTimeDataFromDayOfWeek(classDetails, "Thursday")));
        result.add(new TimeTableData("Fri", getTimeDataFromDayOfWeek(classDetails, "Friday")));
        result.add(new TimeTableData("Sat", getTimeDataFromDayOfWeek(classDetails, "Saturday")));
        result.add(new TimeTableData("Sun", getTimeDataFromDayOfWeek(classDetails, "Sunday")));

        return result;
    }

    private ArrayList<TimeTableData> initDetailData(){
        //todo setdata here
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
        tables.add(new TimeTableData("Tue", values));
        tables.add(new TimeTableData("Thu", values));
        tables.add(new TimeTableData("Sat", values));
        tables.add(new TimeTableData("Mon", values2));
        tables.add(new TimeTableData("Wed", values2));
        tables.add(new TimeTableData("Fri", values2));
        return tables;
    }

    private ArrayList<TimeData> getTimeDataFromDayOfWeek(List<ClassDetail> classDetails, String dayOfWeek) {
        ArrayList<TimeData> timeDataArrayList = new ArrayList<>();

        for (ClassDetail classDetail :
                classDetails) {
            for (TimeSlotModel timeSlotModel :
                    classDetail.getTimeSlotModelList()) {
                if (timeSlotModel.getDayOfWeek().toLowerCase().equals(dayOfWeek.toLowerCase())) {
                    String timeString = timeSlotModel.getTime();

                    String timeStartStr = timeString.substring(0, timeString.indexOf("-")).trim();
                    String timeStopStr = timeString.substring(timeString.indexOf("-")+1).trim();
                    DateTime timeStart = getDateTimeFromString(timeStartStr);
                    DateTime timeStop = getDateTimeFromString(timeStopStr);
                    Log.d(TAG, String.format("getTimeDataFromDayOfWeek: timeString=%s,timeStart=%s,timeStop=%s"
                            ,timeString,timeStart.hourOfDay().getAsShortText(),timeStop.hourOfDay().getAsShortText()));
                    timeDataArrayList.add(
                            new TimeData(classDetail.getClassId()
                                    , classDetail.getClassName()
                                    , Common.getColorsFromClassId(classDetail.getClassId())
                                    , timeStart.getMillis()
                                    , timeStop.getMillis())
                    );
                }
            }
        }
        return timeDataArrayList;
    }

    private DateTime getDateTimeFromString(String time) {
        return DateTimeFormat.forPattern("HH:mmaa").parseDateTime(time);

    }
    private long getMillis(String day){
        DateTime date = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(day);
        return date.getMillis();
    }




}
