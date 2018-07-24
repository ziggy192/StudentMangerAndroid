package com.example.admin.managerstundent.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.managerstundent.DTO.AccountDTO;
import com.example.admin.managerstundent.Entity.Student;
import com.example.admin.managerstundent.HttpServices.HttpHelper;
import com.example.admin.managerstundent.R;
import com.example.admin.managerstundent.Ultils.DummyDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: QuyetNP
 * 25/05/2018
 * <p>
 * A login screen that offers login via user/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.toString();

    @BindView(R.id.tvError)
    TextView tvError;
    @BindView(R.id.edt_username)
    EditText editUser;
    @BindView(R.id.edt_password)
    EditText editPass;


    private int studentIdFromServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    public void checkLogin(View view) {

        //todo uncomment here ( do this last)
        tvError.setVisibility(View.VISIBLE);
        tvError.setText("Loading from server");
//        if(editUser.getText().toString().equals("admin") && editPass.getText().toString().equals("123")) {
        String userName = editUser.getText().toString();
        String password = editPass.getText().toString();
        HttpHelper.getIntance().postLogin(new AccountDTO(userName, password));
        Log.d(TAG, String.format("checkLogin: username=%s,password=%s", userName, password));

//        }
    }


    @Subscribe
    public void onLogin(HttpHelper.PostLoginEvent event) {

        if (event.isSuccess()) {
            Log.d(TAG, "onLogin: Success");

//            tvError.setVisibility(View.INVISIBLE);
            tvError.setText("Login success");
            Student student = event.getStudent();
            DummyDatabase.setStudentProfile(student);
            studentIdFromServer = student.getId();
            //get student profile from start
            HttpHelper.getIntance().startGetStudentProfile(studentIdFromServer);
            //get class details list from start
            HttpHelper.getIntance().getClassDetailsByStudentId(studentIdFromServer);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            //incorrect login


        } else {
            Log.d(TAG, "onLogin: Failure");
            tvError.setVisibility(View.VISIBLE);
            tvError.setText("Incorrect username or password");
            editPass.setText("");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    private boolean checkUserNamePassword(String userName, String password) {
        //todo check username Password here
        //if true, return studentId
        studentIdFromServer = 1;
        return true;
    }
}

