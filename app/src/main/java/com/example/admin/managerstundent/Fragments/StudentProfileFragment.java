package com.example.admin.managerstundent.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.managerstundent.Activity.EditStudentActivity;
import com.example.admin.managerstundent.Entity.Student;
import com.example.admin.managerstundent.HttpServices.HttpHelper;
import com.example.admin.managerstundent.R;
import com.example.admin.managerstundent.Ultils.CircleTransform;
import com.example.admin.managerstundent.Ultils.DummyDatabase;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentProfileFragment extends Fragment {


    private static final String TAG = StudentProfileFragment.class.toString();
    @BindView(R.id.txt_fee_status)
    TextView tvFeeStatus;
    @BindView(R.id.txtParentsNumber)
    TextView tvParentsNumber;
    @BindView(R.id.txtPhone)
    TextView tvPhone;
    @BindView(R.id.txtGender)
    TextView tvGender;
    @BindView(R.id.txtUsername)
    TextView tvUsername;
    @BindView(R.id.imageViewUser)
    ImageView imvUser;
    @BindView(R.id.txtBirthday)
    TextView tvDayOfBirth;




    public StudentProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_detail, container, false);
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

    @Subscribe
    public void putStudentRepsonseEvent(HttpHelper.PutStudentProfileResponseEvent event) {
        if (event.isSuccess()) {
            //todo save info here
            Student student = event.getStudent();
            DummyDatabase.setStudentProfile(student);
            setStudentInfo(student);
            Log.d(TAG, String.format("putStudentRepsonseEvent: Success, student=%s", student));
        } else {
            //do notthing
            Log.d(TAG, "putStudentRepsonseEvent: FAilure");

        }
    }

    private void setStudentInfo(Student studentModel) {

        tvUsername.setText(studentModel.getName());
        tvPhone.setText(studentModel.getPhoneNumber());
        tvParentsNumber.setText(studentModel.getParentsPhoneNumber());
        tvDayOfBirth.setText(studentModel.getDateOfBirth());


        boolean isMale = studentModel.isMale();
        if (isMale) {
            tvGender.setText("Male");
        } else {
            tvGender.setText("Female");
        }


        boolean paid = studentModel.isPaid();
        if (paid) {
            tvFeeStatus.setText("Paid");
            tvFeeStatus.setTextColor(getResources().getColor(R.color.main_green_color));
//            imgPaid.setVisibility(View.VISIBLE);
        } else {
            tvFeeStatus.setText("Not paid yet");
            tvFeeStatus.setTextColor(getResources().getColor(R.color.red_btn_bg_color));
//            imgNotPaid.setVisibility(View.VISIBLE);
        }
        Random r = new Random();
        String url = "https://picsum.photos/250/250/?image=" + r.nextInt(200);
        Picasso.with(getActivity()).load(url).transform(new CircleTransform()).into(imvUser);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Student student = DummyDatabase.getStudentProfile();
        setStudentInfo(student);
    }

    @OnClick(R.id.btnEditStudent)
    public void editStudent() {
        Intent intent = new Intent(getActivity(), EditStudentActivity.class);


//        intent.putExtra("name", ((TextView) findViewById(R.id.txtUsername)).getText());
//        intent.putExtra("birthday",((TextView) findViewById(R.id.txtBirthday)).getText());
//        //todo gender need attention
//        intent.putExtra("gender",
//                ((TextView) findViewById(R.id.txtGender)).getText().equals("Male") ? true : false);
//
//        intent.putExtra("class", ((TextView) findViewById(R.id.txtClass)).getText());
//        intent.putExtra(PARENT_PHONE_NUMBER_KEY, tvParentsNumber.getText());
//        intent.putExtra(PHONE_NUMBER_KEY, tvPhone.getText());


        startActivity(intent);
    }

}
