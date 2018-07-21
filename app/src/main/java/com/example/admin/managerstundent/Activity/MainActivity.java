package com.example.admin.managerstundent.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.example.admin.managerstundent.Adapter.DBAdapter;
import com.example.admin.managerstundent.Adapter.ListClassAdapter;
import com.example.admin.managerstundent.Adapter.SmartFragmentStatePagerAdapter;
import com.example.admin.managerstundent.DTO.ClassDTO;
import com.example.admin.managerstundent.Entity.Student;
import com.example.admin.managerstundent.Fragments.ListSlotRequestedFragment;
import com.example.admin.managerstundent.Fragments.NotificationFragment;
import com.example.admin.managerstundent.Fragments.StudentProfileFragment;
import com.example.admin.managerstundent.Fragments.TimeTableFragment;
import com.example.admin.managerstundent.R;
import com.example.admin.managerstundent.Ultils.BottomNavigationViewHelper;
import com.example.admin.managerstundent.viewPagers.NoSwipeViewpager;
import com.skyfishjy.library.RippleBackground;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Author: DangNHH
 * 19/05/2018
 * <p>
 * Main Activity Class
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private static final String TAG = MainActivity.class.toString();
    private static final String RECEIVING_FRAGMENT_NAME_KEY = "fragmentName";
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    @BindView(R.id.pager)
    NoSwipeViewpager viewpager;
    private String CHANNEL_ID = "my channel";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        settupUI();
        settupListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: onstart");
        String receivingFragmentName  = getIntent().getStringExtra(RECEIVING_FRAGMENT_NAME_KEY);
        if (receivingFragmentName != null) {
            Log.d(TAG, String.format("onStart: receving fragment name = %s", receivingFragmentName));

            if (receivingFragmentName.equals(NotificationFragment.class.toString())) {
                bottomNavigation.setCurrentItem(2);

            }
        }

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

        //todo use different ids for each noti if dont want them to replace each other
        final int NotificationID = 123123;


        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        //open notification fragment when receive
        intent.putExtra(RECEIVING_FRAGMENT_NAME_KEY, NotificationFragment.class.toString());


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24px)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        //need id for later update or remove
        notificationManager.notify(NotificationID, mBuilder.build());
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
