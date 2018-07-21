package com.example.admin.managerstundent.Activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.managerstundent.Dialogs.RequestSlotEditDialogFragment;
import com.example.admin.managerstundent.Entity.ClassDetail;
import com.example.admin.managerstundent.Entity.Subject;
import com.example.admin.managerstundent.Fragments.SlotRequestSubjectChooserFragment;
import com.example.admin.managerstundent.R;
import com.example.admin.managerstundent.Ultils.Common;
import com.example.admin.managerstundent.Ultils.DummyDatabase;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SlotRequestActivity extends AppCompatActivity implements RequestSlotEditDialogFragment.OnRequestSlotInteractionListener {

    private static final String TAG = SlotRequestActivity.class.toString();

    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    //    @BindView(R.id.spinnerSubject)
//    Spinner spinnerSubject;
//    @BindView(R.id.lvSlot)
//    ListView lvSlot;
    SlotListAdapter slotListAdapter;

    private Subject subjectChoosed;
    private ClassDetail classDetailChoosed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_request);
        ButterKnife.bind(this);
        setSupportActionBar(myToolbar);

        settupUI();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return true;

    }

    private void settupUI() {
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //todo need aconstant subject list

        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this
//                , android.R.layout.simple_spinner_dropdown_item
//                , DummyDatabase.subjects);
//        //set the spinners adapter to the previously created one.
//        spinnerSubject.setAdapter(adapter);
//

//        List<SlotRequestModel> slotRequestModels = new ArrayList<>();
//        slotRequestModels.add(new SlotRequestModel(2, "12:00", "13:00"));
//        slotListAdapter = new SlotListAdapter(this, slotRequestModels);
//        lvSlot.setAdapter(slotListAdapter);


        navigateFragement(SlotRequestSubjectChooserFragment.newInstance(),SlotRequestSubjectChooserFragment.class.toString());
    }

    public void clickToSubmit(View view) {

    }

    public void clickToAddSlot(View view) {
        Toast.makeText(this, "add slot", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDone(SlotRequestModel model) {
        Toast.makeText(this, "model added weekday = "+Common.weekdays[model.getWeekDayNumber()], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {

    }


    public Subject getSubjectChoosed() {
        return subjectChoosed;
    }

    public void setSubjectChoosed(Subject subjectChoosed) {
        this.subjectChoosed = subjectChoosed;
    }

    public ClassDetail getClassDetailChoosed() {
        return classDetailChoosed;
    }

    public void setClassDetailChoosed(ClassDetail classDetailChoosed) {
        this.classDetailChoosed = classDetailChoosed;
    }

    public void clickToNext(View view) {
        Log.d(TAG, "clickToNext: ");
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
    public static class SlotRequestModel implements Serializable{
        int weekDayNumber;
        String startTime;
        String endTime;

        public SlotRequestModel(int weekDayNumber, String startTime, String endTime) {
            this.weekDayNumber = weekDayNumber;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public int getWeekDayNumber() {
            return weekDayNumber;
        }

        public void setWeekDayNumber(int weekDayNumber) {
            this.weekDayNumber = weekDayNumber;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }

    public static class SlotListAdapter extends ArrayAdapter<SlotRequestModel> {

        private Context mContext;
        private List<SlotRequestModel> slotRequestModelList;

        public SlotListAdapter(@NonNull Context context, List<SlotRequestModel> slotRequestModelList) {
            super(context, 0, slotRequestModelList);
            this.mContext = context;
            this.slotRequestModelList = slotRequestModelList;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItem = convertView;
            //normal item
            if (listItem == null) {
                listItem = LayoutInflater.from(mContext).inflate(R.layout.item_slot_request_list, parent, false);
            }
            final SlotRequestModel model = slotRequestModelList.get(position);

            TextView tvDayOfWeek = listItem.findViewById(R.id.tvDayOfWeek);
            TextView tvStartTime = listItem.findViewById(R.id.tvStartTime);
            TextView tvEndTime = listItem.findViewById(R.id.tvEndTime);

            tvDayOfWeek.setText(Common.getWeekDay(model.getWeekDayNumber()));
            tvStartTime.setText(model.getStartTime());
            tvEndTime.setText(model.getEndTime());

            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "edit this slot position=" + position, Toast.LENGTH_SHORT).show();
                }
            });


            return listItem;
        }


    }
}
