package com.example.admin.managerstundent.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.admin.managerstundent.Constant.Constant;
import com.example.admin.managerstundent.Entity.Student;
import com.example.admin.managerstundent.HttpServices.HttpHelper;
import com.example.admin.managerstundent.R;
import com.example.admin.managerstundent.Ultils.CircleTransform;
import com.example.admin.managerstundent.Ultils.DocumentHelper;
import com.example.admin.managerstundent.Ultils.DummyDatabase;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditStudentActivity extends AppCompatActivity {

    private static final String TAG = EditStudentActivity.class.toString();
    @BindView(R.id.edit_test_name)
    EditText edtName;
    @BindView(R.id.edit_test_phone)
    EditText edtPhone;
    @BindView(R.id.edit_test_birthday)
    EditText edtDayOfBirth;
    @BindView(R.id.edit_text_parents_number)
    EditText edtParentsNumber;
    @BindView(R.id.rbMale)
    RadioButton rbMale;
    @BindView(R.id.rbFemale)
    RadioButton rbFemale;
    private Uri returnUri;
    private String userAvatar = null;
    private Bitmap bitmap, rotateBitmap;
    private ImageView pickImage;
    private ExifInterface exif;
    private File chosenFile;

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        ButterKnife.bind(this);

        Student student = DummyDatabase.getStudentProfile();

        ImageView img = findViewById(R.id.img);
        Random r = new Random();
        String url = "https://picsum.photos/250/250/?image=" + r.nextInt(200);
        Picasso.with(getApplicationContext()).load(url).transform(new CircleTransform()).into(img);
        edtName.setText(student.getName());
        edtPhone.setText(student.getPhoneNumber());
        edtDayOfBirth.setText(student.getDateOfBirth());

        boolean isMale = student.isMale();
        if (isMale) {
            rbMale.setChecked(true);
        } else {
            rbFemale.setChecked(true);
        }

        edtParentsNumber.setText(student.getParentsPhoneNumber());

    }

    @Override
    protected void onStart() {
        super.onStart();
//        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        EventBus.getDefault().unregister(this);
    }



    public void clickToEdit(View view) {
        Student oldStudent = DummyDatabase.getStudentProfile();

        Student editedStudentModel = new Student(
                oldStudent.getId()
                , edtName.getText().toString()
                , edtPhone.getText().toString()
                , edtParentsNumber.getText().toString()
                , edtDayOfBirth.getText().toString()
                , rbMale.isChecked()
                , oldStudent.isPaid()
        );
        //post event
        HttpHelper.getIntance().putStudentProfile(editedStudentModel);
        //save info when http responsed

        finish();
    }

    public void clickToCancel(View view) {
        onBackPressed();
    }

    public void onChooseImage(View view) {
        userAvatar = null;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            returnUri = data.getData();
            getFilePath();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), returnUri);
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

                rotateBitmap = null;
                switch (orientation) {

                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotateBitmap = rotateImage(bitmap, 90);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotateBitmap = rotateImage(bitmap, 180);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotateBitmap = rotateImage(bitmap, 270);
                        break;

                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        rotateBitmap = bitmap;
                }
                pickImage = findViewById(R.id.img);
                pickImage.setImageBitmap(rotateBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getFilePath() {
        String filePath = DocumentHelper.getPath(this, this.returnUri);
        if (filePath == null || filePath.isEmpty()) return;
        chosenFile = new File(filePath);
        try {
            exif = new ExifInterface(String.valueOf(chosenFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeDate(View view) {
        final DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                DateTime dt = new DateTime(year, month, dayOfMonth, 0, 0, 0);
                ((EditText) findViewById(R.id.edit_test_birthday)).setText(dtf.print(dt));
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        dpd.show();
    }
}
