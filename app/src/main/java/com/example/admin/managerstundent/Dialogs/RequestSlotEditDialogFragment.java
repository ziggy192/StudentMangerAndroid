package com.example.admin.managerstundent.Dialogs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.admin.managerstundent.Activity.SlotRequestActivity;
import com.example.admin.managerstundent.R;
import com.example.admin.managerstundent.Ultils.Common;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnRequestSlotInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RequestSlotEditDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestSlotEditDialogFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters

    private SlotRequestActivity.SlotRequestModel model;
    private OnRequestSlotInteractionListener mListener;


    @BindView(R.id.spinnerDayOfWeek)
    Spinner spinnerDayOfWeek;
    @BindView(R.id.tvStartTime)
    TextView tvStartTime;
    @BindView(R.id.tvEndTime)
    TextView tvEndTime;
    public RequestSlotEditDialogFragment() {
        // Required empty public constructor
    }

    public static RequestSlotEditDialogFragment newInstance(SlotRequestActivity.SlotRequestModel  model) {
        RequestSlotEditDialogFragment fragment = new RequestSlotEditDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            model = (SlotRequestActivity.SlotRequestModel) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request_slot_edit_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, Common.weekdays);

        spinnerDayOfWeek.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRequestSlotInteractionListener) {
            mListener = (OnRequestSlotInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRequestSlotInteractionListener");
        }
    }

    public void clickToDone(View view) {
        int dayOfWeekumber = spinnerDayOfWeek.getSelectedItemPosition();

        String startTime = tvStartTime.getText().toString();
        String endTime = tvEndTime.getText().toString();
        SlotRequestActivity.SlotRequestModel model =
                new SlotRequestActivity.SlotRequestModel(dayOfWeekumber, startTime, endTime);
        mListener.onDone(model);

    }

    public void clickToCancel(View view) {
        mListener.onCancel();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnRequestSlotInteractionListener {
        // TODO: Update argument type and name
        void onDone(SlotRequestActivity.SlotRequestModel model);
        void onCancel();
    }
}
