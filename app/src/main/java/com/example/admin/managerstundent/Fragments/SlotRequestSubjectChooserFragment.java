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
import com.example.admin.managerstundent.Entity.Subject;
import com.example.admin.managerstundent.R;
import com.example.admin.managerstundent.Ultils.DummyDatabase;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SlotRequestSubjectChooserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SlotRequestSubjectChooserFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = SlotRequestSubjectChooserFragment.class.toString();


    // TODO: Rename and change types of parameters
    private List<Subject> subjectList;
    private SubjectListAdapter adapter;

    @BindView(R.id.lvSubjects)
    ListView lvSubjectst;

    SlotRequestActivity mActivity;
    public SlotRequestSubjectChooserFragment() {

        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SlotRequestSubjectChooserFragment newInstance() {

        SlotRequestSubjectChooserFragment fragment = new SlotRequestSubjectChooserFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //get list from database
        subjectList = Arrays.asList(DummyDatabase.subjects);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slot_request_subject_chooser, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        adapter = new SubjectListAdapter(subjectList);
        lvSubjectst.setAdapter(adapter);

        lvSubjectst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Subject subject = adapter.getItem(position);
                mActivity.setSubjectChoosed(subject);
                mActivity.navigateFragement(SlotRequestClassChooserFragment.newInstance(), SlotRequestClassChooserFragment.class.toString());
                Toast.makeText(
                        SlotRequestSubjectChooserFragment.this.getActivity()
                        , "subject "+subject.getSubjectName()
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

    public static class SubjectListAdapter extends BaseAdapter {

        List<Subject> subjectList;

        public SubjectListAdapter(List<Subject> subjectList) {
            this.subjectList = subjectList;
        }

        @Override
        public int getCount() {
            return subjectList.size();
        }

        @Override
        public Subject getItem(int position) {
            return subjectList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return subjectList.get(position).getSubjectId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            TextView textView = view.findViewById(android.R.id.text1);
            textView.setText(getItem(position).getSubjectName());
            return view;
        }
    }
}
