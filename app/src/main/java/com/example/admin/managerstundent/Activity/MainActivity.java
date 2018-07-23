package com.example.admin.managerstundent.Activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.example.admin.managerstundent.Adapter.SmartFragmentStatePagerAdapter;
import com.example.admin.managerstundent.Constant.Constant;
import com.example.admin.managerstundent.Entity.ClassDetail;
import com.example.admin.managerstundent.Fragments.ListSlotRequestedFragment;
import com.example.admin.managerstundent.Fragments.NotificationFragment;
import com.example.admin.managerstundent.Fragments.StudentProfileFragment;
import com.example.admin.managerstundent.Fragments.TimeTableFragment;
import com.example.admin.managerstundent.HttpServices.HttpHelper;
import com.example.admin.managerstundent.R;
import com.example.admin.managerstundent.Ultils.DummyDatabase;
import com.example.admin.managerstundent.Ultils.MyNotificationManager;
import com.example.admin.managerstundent.viewPagers.NoSwipeViewpager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: DangNHH
 * 19/05/2018
 * <p>
 * Main Activity Class
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private static final String TAG = MainActivity.class.toString();
    private static String NOTIFICATION_TOPIC = "notifications";

    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    @BindView(R.id.pager)
    NoSwipeViewpager viewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupNotificationChannel();
        settupUI();
        settupListeners();
        setupFirebaseMessage();


    }

    private void setupFirebaseMessage() {
        FirebaseMessaging.getInstance().subscribeToTopic(NOTIFICATION_TOPIC)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Log.d(TAG, "onSubcribeToTopicComplete:topic=" + NOTIFICATION_TOPIC+", result=ERROR");
                            return;
                        } else {
                            Log.d(TAG, "onSubcribeToTopicComplete:topic=" + NOTIFICATION_TOPIC+", result=SUCCESS");

                        }
                    }
                });
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(this, new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.d(TAG, "onComplete: ERROR");
                    return;
                } else {
                    // Get new Instance ID token
                    String token = task.getResult().getToken();
                    Log.d(TAG, "onGetTokenComplete: token="+token);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }


    private void setupNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(Constant.CHANNEL_ID, Constant.CHANNEL_NAME, importance);
            mChannel.setDescription(Constant.CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }
    @Subscribe
    public void onStudentProfileResponse(HttpHelper.GetStudentProfileResponseEvent event) {
        if (event.isSuccess()) {
            DummyDatabase.setStudentProfile(event.getStudent());
        } else {
            Log.d(TAG, "onStudentProfileResponse: Failure");
        }
    }
    @Subscribe
    public void onPostSlotRequestResponse(HttpHelper.PostSlotRequestResponseEvent event) {
        if (event.isSuccess()) {
            Toast.makeText(this, "Request received", Toast.LENGTH_SHORT).show();
            Log.d(TAG, String.format("onPostSlotRequestResponse: sucess,SlotRequestList=%s", event.getSlotRequestedModelList()));

        } else {
            Log.d(TAG, "onPostSlotRequestResponse: Failure");
        }

    }
    @Subscribe
    public void onClassDetailsListResponse(HttpHelper.GetClassDetailsListResponseEvent event) {
        if (event.isSuccess()) {
            List<ClassDetail> classDetailList = event.getClassDetailList();
            Log.d(TAG, String.format("onClassDetailsListResponse: Sucesses, classDetailList=%s", classDetailList));
            DummyDatabase.setClassDetails(classDetailList);

            //todo update time table fragemnt

        } else {
            Log.d(TAG, "onClassDetailsListResponse: FAiled");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        Log.d(TAG, "onStart: onstart");
        String receivingFragmentName  = getIntent().getStringExtra(Constant.RECEIVING_FRAGMENT_NAME_KEY);
        if (receivingFragmentName != null) {
            Log.d(TAG, String.format("onStart: receving fragment name = %s", receivingFragmentName));

            if (receivingFragmentName.equals(NotificationFragment.class.toString())) {
                bottomNavigation.setCurrentItem(2);

            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void settupListeners() {
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (!wasSelected)
                    viewpager.setCurrentItem(position);
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        sendNotification();

                        break;
                    case 3:
                        break;
                }
                return true;
            }
        });
    }

    private void sendNotification() {
        sendNotification("15 minutes to class","You should hurry up!");
    }

    private void sendNotification(String title, String content) {

        MyNotificationManager.getInstance(this).displayNotification(title, content);
        Log.d(TAG, "sendNotification: triggerd");

    }

    public void settupUI() {


        AHBottomNavigationAdapter navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.navigation_menu);
        navigationAdapter.setupWithBottomNavigation(bottomNavigation);

        bottomNavigation.setColored(true);
        bottomNavigation.setDefaultBackgroundResource(R.color.colorPrimary);
        bottomNavigation.setAccentColor(fetchColor(R.color.white));
//        bottomNavigation.setInactiveColor(Color.parseColor("#FFFF00"));

//        bottomNavigation.setAccentColor(fetchColor(R.color.colorPrimary));
//        bottomNavigation.setInactiveColor(fetchColor(R.color.colorTextSecondary));



        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);


        //todo enable float button on edit student
        // Enable the translation of the FloatingActionButton
//        bottomNavigation.manageFloatingActionButtonBehavior(floatingActionButton);


        bottomNavigation.setCurrentItem(0);
        viewpager.setPagingEnabled(false);
        BottomBarAdapter pagerAdapter = new BottomBarAdapter(getSupportFragmentManager());

        pagerAdapter.addFragments(new ListSlotRequestedFragment());
        pagerAdapter.addFragments(new TimeTableFragment());
        pagerAdapter.addFragments(new NotificationFragment());
        pagerAdapter.addFragments(new StudentProfileFragment());

        viewpager.setAdapter(pagerAdapter);

//        BottomNavigationView bar = findViewById(R.id.bottom_navigation);
//        bar.setOnNavigationItemSelectedListener(this);
//        bar.setSelectedItemId(R.id.nav_request);

//        BottomNavigationViewHelper.disableShiftMode(bar);
//        bar.getMenu().getItem(0).setChecked(true);
    }

    private int fetchColor(@ColorRes int color) {
        return ContextCompat.getColor(this, color);
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

    public void setNotification(int notiNumber) {
        bottomNavigation.setNotification("" + notiNumber, 2);
    }

    public void clearNotification() {
        bottomNavigation.setNotification(new AHNotification(), 2);

    }

    public void addStudent(View view) {
        Intent intent = new Intent(this, ListClassActivity.class);
        startActivity(intent);
    }

    public void viewTimeTable(View view) {
        Intent intent = new Intent(this, TableActivity.class);
        startActivity(intent);
    }


    public void toStudentList(View view) {
        Intent intent = new Intent(this, ListStudentActivity.class);
        startActivity(intent);
    }

    public void toClass(View view) {
//        Intent intent = new Intent(this, ListStudentActivity.class);
//        switch(view.getId()) {
//            case R.id.card1:
//                intent.putExtra("subject", "Math 9");
//                intent.putExtra("time", "5:00 - 6:30 AM");
//                startActivity(intent);
//                break;
//            case R.id.card2:
//                intent.putExtra("subject", "Math 10");
//                intent.putExtra("time", "6:45 - 8:15 AM");
//                startActivity(intent);
//                break;
//            case R.id.card3:
//                intent.putExtra("subject", "Chemistry 10");
//                intent.putExtra("time", "8:30 - 10:00 AM");
//                startActivity(intent);
//                break;
//            case R.id.card4:
//                intent.putExtra("subject", "Physics 11");
//                intent.putExtra("time", "10:15 - 11:45 AM");
//                startActivity(intent);
//                break;
//        }
    }

    public void changeDate(View view) {
//        final DateTimeFormatter dtf = DateTimeFormat.forPattern("dd MMMM, yyyy");
//        Calendar cal = Calendar.getInstance();
//        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                month++;
//                //month in DatePicker count from 0, but month in dt count from 1
//                DateTime dt = new DateTime(year, month, dayOfMonth, 0, 0, 0);
//                date.setText(dtf.print(dt));
//            }
//        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
//        dpd.show();
    }

    public void addClass(View view) {
        Intent intent = new Intent(this, AddClassActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_request:
                navigateFragement(ListSlotRequestedFragment.newInstance(), ListSlotRequestedFragment.class.toString());
                break;
            case R.id.nav_timetable:

                break;
            case R.id.nav_notification:
                break;
            case R.id.nav_profile:
                navigateFragement(new StudentProfileFragment(), StudentProfileFragment.class.toString());
                break;
        }
        return false;
    }

    public static class BottomBarAdapter extends SmartFragmentStatePagerAdapter {
        private final List<Fragment> fragments = new ArrayList<>();

        public BottomBarAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragments(Fragment fragment) {
            fragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
