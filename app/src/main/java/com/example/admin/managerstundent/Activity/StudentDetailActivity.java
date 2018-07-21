package com.example.admin.managerstundent.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.managerstundent.Constant.Constant;
import com.example.admin.managerstundent.R;
import com.example.admin.managerstundent.Ultils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.admin.managerstundent.Constant.Constant.PARENT_PHONE_NUMBER_KEY;
import static com.example.admin.managerstundent.Constant.Constant.PHONE_NUMBER_KEY;

public class StudentDetailActivity extends AppCompatActivity {




    @BindView(R.id.txt_fee_status)
    TextView tvFeeStatus;
    @BindView(R.id.txtParentsNumber)
    TextView tvParentsNumber;
    @BindView(R.id.txtPhone)
    TextView tvPhone;
    @BindView(R.id.txtGender)
    TextView tvGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        ButterKnife.bind(this);
        ((TextView) findViewById(R.id.txtUsername)).setText(getIntent().getStringExtra("name"));
        ImageView img = findViewById(R.id.imageViewUser);
        ImageView imgPaid = findViewById(R.id.imgPaid);
        ImageView imgNotPaid = findViewById(R.id.imgNotPaid);

        tvParentsNumber.setText(getIntent().getStringExtra(PARENT_PHONE_NUMBER_KEY));
        tvPhone.setText(getIntent().getStringExtra(PHONE_NUMBER_KEY));
        boolean isMale = getIntent().getBooleanExtra(Constant.GENDER_KEY, true);
        if (isMale) {
            tvGender.setText("Male");
        } else {
            tvGender.setText("Female");
        }


        boolean paid = getIntent().getBooleanExtra("paid", true);
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
        Picasso.with(getApplicationContext()).load(url).transform(new CircleTransform()).into(img);
    }

    public void editStudent(View view) {
        Intent intent = new Intent(this, EditStudentActivity.class);
        intent.putExtra("name", ((TextView) findViewById(R.id.txtUsername)).getText());
        intent.putExtra("birthday",((TextView) findViewById(R.id.txtBirthday)).getText());
        //todo gender need attention
        intent.putExtra("gender",
                ((TextView) findViewById(R.id.txtGender)).getText().equals("Male") ? true : false);

        intent.putExtra(PARENT_PHONE_NUMBER_KEY, tvParentsNumber.getText());
        intent.putExtra(PHONE_NUMBER_KEY, tvPhone.getText());


        startActivity(intent);
    }
}
