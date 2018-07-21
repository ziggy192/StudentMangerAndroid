package com.example.admin.managerstundent.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.managerstundent.Activity.SlotRequestActivity;
import com.example.admin.managerstundent.Entity.ClassDetail;
import com.example.admin.managerstundent.Entity.Subject;
import com.example.admin.managerstundent.R;
import com.example.admin.managerstundent.Ultils.DummyDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SlotRequestClassChooserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SlotRequestClassChooserFragment extends Fragment {
    private static final String TAG = SlotRequestClassChooserFragment.class.toString();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    SlotRequestActivity mActivity;

    @BindView(R.id.tvSubjectName)
    TextView tvSubjectName;
    @BindView(R.id.lvClasses)
    ListView lvClasses;
    public SlotRequestClassChooserFragment() {
        // Required empty public constructor
    }

    public static SlotRequestClassChooserFragment newInstance() {
        SlotRequestClassChooserFragment fragment = new SlotRequestClassChooserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slot_request_class_chooser, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        Subject subject = mActivity.getSubjectChoosed();
        tvSubjectName.setText("Subject: " + subject.getSubjectName());

        List<ClassDetail> classDetailList = DummyDatabase.getClassList(subject);
        final ClassDetailListAdapter adapter = new ClassDetailListAdapter(classDetailList);
        lvClasses.setAdapter(adapter);

        lvClasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClassDetail classDetailChoosed = adapter.getItem(position);
                mActivity.setClassDetailChoosed(classDetailChoosed);
                mActivity.pushFragment(new SlotRequestClassDetailFragment(), SlotRequestClassDetailFragment.class.toString());
                Toast.makeText(
                        SlotRequestClassChooserFragment.this.getActivity()
                        , "Class " + classDetailChoosed.getClassName()
                        , Toast.LENGTH_SHORT).show();
            }
        });
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
    public static class ClassDetailListAdapter extends BaseAdapter {

        List<ClassDetail> classDetails;

        public ClassDetailListAdapter(List<ClassDetail> subjectList) {
            this.classDetails = subjectList;
        }

        @Override
        public int getCount() {
            return classDetails.size();
        }

        @Override
        public ClassDetail getItem(int position) {
            return classDetails.get(position);
        }

        @Override
        public long getItemId(int position) {
            return classDetails.get(position).getClassId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            TextView textView = view.findViewById(android.R.id.text1);
            textView.setText(getItem(position).getClassName());
            return view;
        }
    }

}
