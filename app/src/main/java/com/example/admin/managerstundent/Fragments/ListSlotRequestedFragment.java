package com.example.admin.managerstundent.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.managerstundent.Activity.SlotDetailActivity;
import com.example.admin.managerstundent.Activity.SlotRequestActivity;
import com.example.admin.managerstundent.Activity.SlotRequestedDetailActivity;
import com.example.admin.managerstundent.Constant.Constant;
import com.example.admin.managerstundent.Entity.SlotRequestedModel;
import com.example.admin.managerstundent.R;
import com.example.admin.managerstundent.Ultils.DummyDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListSlotRequestedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListSlotRequestedFragment extends Fragment {

    private static final String TAG = ListSlotRequestedFragment.class.toString();
    @BindView(R.id.rvSlotRequestedList)
    RecyclerView rvSlotRequestedList;

    public static ListSlotRequestedFragment newInstance() {
        ListSlotRequestedFragment fragment = new ListSlotRequestedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    public ListSlotRequestedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_slot_requested, container, false);
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
    public void onSlotRequestedItemClicked(SlotRequestedClickedEvent event) {
        SlotRequestedModel model = event.getModel();
        //todo show model detail
        Log.d(TAG, String.format("onSlotRequestedItemClicked: model=%s", model.toString()));

        //to slot request detail
        Intent intent = new Intent(getActivity(), SlotRequestedDetailActivity.class);
        intent.putExtra(Constant.SLOT_REQUESTED_MODEL_KEY, model);
        startActivity(intent);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        List<SlotRequestedModel> models = DummyDatabase.getSlotRequestedModels();

        rvSlotRequestedList.setAdapter(new SlotRequestedAdapter(models));
        rvSlotRequestedList.setLayoutManager(new LinearLayoutManager(this.getActivity()));

    }

    @OnClick(R.id.btnAddRequest)
    public void addRequest() {
        Intent intent = new Intent(getActivity(), SlotRequestActivity.class);
        startActivity(intent);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        SlotRequestedModel model;
        @BindView(android.R.id.text1)
        TextView tvClassName;
        @BindView(android.R.id.text2)
        TextView tvState;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setFocusable(true);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new SlotRequestedClickedEvent(model));
                }
            });
        }


        public void bind(SlotRequestedModel model) {
            this.model = model;
            tvClassName.setText(model.getClassDetail().getClassName());
            String state = model.getState();
            switch (state) {
                case SlotRequestedModel.ACCEPTED_STATE:
                    tvState.setText("Accepted");
                    tvState.setTextColor(itemView.getResources().getColor(android.R.color.holo_green_dark));
                    break;
                case SlotRequestedModel.DENIED_STATE:
                    tvState.setText("Denied");
                    tvState.setTextColor(itemView.getResources().getColor(android.R.color.holo_red_dark));

                    break;
                case SlotRequestedModel.WAITING_STATE:
                    tvState.setText("Waiting for response...");
                    tvState.setTextColor(itemView.getResources().getColor(R.color.colorTextPrimary));
                    break;
            }
        }
    }

    public static class SlotRequestedAdapter extends RecyclerView.Adapter<ViewHolder> {

        List<SlotRequestedModel> slotRequestedModels;

        public SlotRequestedAdapter(List<SlotRequestedModel> slotRequestedModels) {
            this.slotRequestedModels = slotRequestedModels;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view  =LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(slotRequestedModels.get(position));

        }

        @Override
        public int getItemCount() {
            return slotRequestedModels.size();
        }
    }

    public static class SlotRequestedClickedEvent{
        SlotRequestedModel model;

        public SlotRequestedClickedEvent(SlotRequestedModel model) {
            this.model = model;
        }

        public SlotRequestedModel getModel() {
            return model;
        }
    }
}
