package com.example.admin.managerstundent.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.managerstundent.Adapter.TimeSlotModelAdapter;
import com.example.admin.managerstundent.Constant.Constant;
import com.example.admin.managerstundent.Entity.ClassDetail;
import com.example.admin.managerstundent.Entity.SlotRequestedModel;
import com.example.admin.managerstundent.Entity.TimeSlotModel;
import com.example.admin.managerstundent.Fragments.SlotRequestSubjectChooserFragment;
import com.example.admin.managerstundent.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SlotRequestedDetailActivity extends AppCompatActivity {

    SlotRequestedModel model;

    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

    @BindView(R.id.tvSlotRequestState)
    TextView tvSlotRequestState;

    @BindView(R.id.tvSubjectName)
    TextView tvSubjectName;
    @BindView(R.id.tvTeacherName)
    TextView tvTeacherName;

    @BindView(R.id.tvClassName)
    TextView tvClassNameLabel;
    @BindView(R.id.lvTimeSlots)
    ListView lvTimeSlots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_requested_detail);
        ButterKnife.bind(this);
        setSupportActionBar(myToolbar);
        getReferences();
        settupUI();

    }

    private void getReferences() {
        Intent intent = getIntent();
        model = (SlotRequestedModel) intent.getSerializableExtra(Constant.SLOT_REQUESTED_MODEL_KEY);
        if (model == null) {
            model = SlotRequestedModel.getDefaultModel();
        }
    }

    private void settupUI() {
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String state = model.getState();

        switch (state) {
            case SlotRequestedModel.ACCEPTED_STATE:
                tvSlotRequestState.setText("Accepted");
                tvSlotRequestState.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                break;
            case SlotRequestedModel.DENIED_STATE:
                tvSlotRequestState.setText("Denied");

                tvSlotRequestState.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            break;
            case SlotRequestedModel.WAITING_STATE:
                tvSlotRequestState.setText("Waiting for response...");
                tvSlotRequestState.setTextColor(getResources().getColor(R.color.colorTextPrimary));

        }

        ClassDetail classDetailModel = model.getClassDetail();

        tvClassNameLabel.setText(String.format("%s", classDetailModel.getClassName()));
        tvSubjectName.setText(classDetailModel.getSubjectName());
        tvTeacherName.setText(classDetailModel.getTeacherName());

        List<TimeSlotModel> timeSlotModelList = classDetailModel.getTimeSlotModelList();

        TimeSlotModelAdapter adapter = new TimeSlotModelAdapter(timeSlotModelList);
        lvTimeSlots.setAdapter(adapter);



    }

    public void navigateFragement(Fragment fragment, String tag) {
        Fragment mFragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (mFragment == null) {
            mFragment = fragment;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.view_stub, mFragment, tag)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .commit();

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
