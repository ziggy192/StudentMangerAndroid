package com.example.admin.managerstundent.Activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.managerstundent.Entity.ClassDetail;
import com.example.admin.managerstundent.Entity.Subject;
import com.example.admin.managerstundent.Fragments.SlotRequestSubjectChooserFragment;
import com.example.admin.managerstundent.R;
import com.example.admin.managerstundent.Ultils.Common;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SlotRequestActivity extends AppCompatActivity {

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


        pushFragment(SlotRequestSubjectChooserFragment.newInstance()
                , SlotRequestSubjectChooserFragment.class.toString()
                , false);
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

    public void pushFragment(Fragment fragment, String tag, boolean addToBackStack ) {
        Fragment mFragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (mFragment == null) {
            mFragment = fragment;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.view_stub, mFragment, tag)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }

        transaction.commit();

    }
    public void pushFragment(Fragment fragment, String tag) {
        pushFragment(fragment,tag,true);
    }



    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
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
