package com.example.admin.managerstundent.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.admin.managerstundent.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassDetailActivity extends AppCompatActivity {
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
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvDayOfWeek)
    TextView tvDayOfWeek;
    @BindView(R.id.tvRoomName)
    TextView tvRoomName;
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);
        ButterKnife.bind(this);
        setSupportActionBar(myToolbar);
        setupUI(this.getIntent().getBundleExtra(PARAMS_KEY));
    }

    private void setupUI(Bundle bundle) {
        if (bundle == null) bundle = new Bundle();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle("English");
        tvSubjectName.setText(bundle.getString(SUBJECT_NAME_KEY," "));
        tvTeacherName.setText(bundle.getString(TEACHER_NAME_KEY," "));
        tvTime.setText(bundle.getString(TIME_KEY," "));
        tvDayOfWeek.setText(bundle.getString(DAYOFWEEK_KEY," "));
        tvRoomName.setText(bundle.getString(ROOM_NAME_KEY," "));


    }

}
