package com.example.admin.managerstundent.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.managerstundent.Adapter.TimeSlotModelAdapter;
import com.example.admin.managerstundent.Constant.Constant;
import com.example.admin.managerstundent.Entity.ClassDetail;
import com.example.admin.managerstundent.Entity.TimeSlotModel;
import com.example.admin.managerstundent.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SlotDetailActivity extends AppCompatActivity {
    public static final String SUBJECT_NAME_KEY = "subject name";
    public static final String TEACHER_NAME_KEY = "teachear name";
    public static final String TIME_KEY = "time";
    public static final String DAYOFWEEK_KEY = "day of week";
    public static final String ROOM_NAME_KEY = "room name";
    public static final String PARAMS_KEY = "params key";

    @BindView(R.id.tvSubjectName)
    TextView tvSubjectName;
    @BindView(R.id.tvTeacherName)
    TextView tvTeacherName;
    @BindView(R.id.tvRoomName)
    TextView tvRoomName;
    @BindView(R.id.my_toolbar)

    Toolbar myToolbar;
    @BindView(R.id.lvTimeSlots)
    ListView lvTimeSlots;

    @BindView(R.id.tvClassName)
    TextView tvClassNameLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_detail);
        ButterKnife.bind(this);
        setSupportActionBar(myToolbar);
        setupUI(this.getIntent().getBundleExtra(PARAMS_KEY));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return true;

    }

    private void setupUI(Bundle bundle) {
        if (bundle == null) bundle = new Bundle();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle(bundle.getString(SUBJECT_NAME_KEY, " "));

        ClassDetail classDetail = (ClassDetail) bundle.getSerializable(Constant.CLASS_DETAIL_MODEL_KEY);
        if (classDetail == null) {
            classDetail = new ClassDetail(0,"English 1");
        }

        tvSubjectName.setText(classDetail.getSubjectName());
        tvTeacherName.setText(classDetail.getTeacherName());
        tvRoomName.setText(classDetail.getRoomName());
        tvClassNameLabel.setText(classDetail.getClassName());

        List<TimeSlotModel> timeSlotModelList = classDetail.getTimeSlotModelList();

        TimeSlotModelAdapter adapter = new TimeSlotModelAdapter(timeSlotModelList);
        lvTimeSlots.setAdapter(adapter);


    }

}
