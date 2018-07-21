package com.example.admin.managerstundent.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.managerstundent.Activity.SlotRequestActivity;
import com.example.admin.managerstundent.Entity.ClassDetail;
import com.example.admin.managerstundent.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.util.Log.d;

/**
 * A simple {@link Fragment} subclass.
 */
public class SlotRequestClassDetailFragment extends Fragment {


    private static final String TAG = SlotRequestClassChooserFragment.class.toString();
    SlotRequestActivity mActivity;
    ClassDetail classDetailModel;

    @BindView(R.id.tvSubjectName)
    TextView tvSubjectName;
    @BindView(R.id.tvTeacherName)
    TextView tvTeacherName;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvDayOfWeek)
    TextView tvDayOfWeek;
    @BindView(R.id.tvClassName)
    TextView tvClassNameLabel;

    public SlotRequestClassDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slot_request_class_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        classDetailModel = mActivity.getClassDetailChoosed();

        tvClassNameLabel.setText(String.format("Class %s", classDetailModel.getClassName()));
        tvSubjectName.setText(classDetailModel.getSubjectName());
        tvTeacherName.setText(classDetailModel.getTeacherName());
        tvTime.setText(classDetailModel.getTime());
        tvDayOfWeek.setText(classDetailModel.getDaysOfWeekString());

    }
    @OnClick(R.id.btnSubmit)
    public void onSubmit() {
        Log.d(TAG, "onSubmit: pressed "+ classDetailModel.getClassName());
        this.getActivity().finish();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SlotRequestActivity) {
            mActivity = (SlotRequestActivity) context;
        } else {
            Log.e(TAG, "onAttach: not Slot Request Activiyt", null);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

}
