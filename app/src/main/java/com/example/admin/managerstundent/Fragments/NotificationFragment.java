package com.example.admin.managerstundent.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.managerstundent.Entity.NotificationModel;
import com.example.admin.managerstundent.R;
import com.example.admin.managerstundent.Ultils.DummyDatabase;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    @BindView(R.id.rvNotifications)
    RecyclerView rvNotification;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        NotificationsAdapter adapter = new NotificationsAdapter(DummyDatabase.getNotificationList());
        rvNotification.setAdapter(adapter);
        rvNotification.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        NotificationModel model;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.imvNotification)
        ImageView imvNotification;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!model.isRead()) {
                        model.setRead(true);
                        DummyDatabase.updateNotification(model);
                        bind(model);
                    }
                }
            });
        }


        public void bind(NotificationModel model) {
            this.model = model;
            tvContent.setText(model.getContent());
            tvTitle.setText(model.getTitle());
            if (model.isRead()) {
                imvNotification.setImageResource(R.drawable.ic_baseline_notifications_24px_disable);
                tvTitle.setTextColor(itemView.getResources().getColor(R.color.colorTextPrimaryDisable));
                tvContent.setTextColor(itemView.getResources().getColor(R.color.colorTextSecondaryDisable));
            } else {
                imvNotification.setImageResource(R.drawable.ic_baseline_notifications_24px_black);
                tvTitle.setTextColor(itemView.getResources().getColor(R.color.colorTextPrimary));
                tvContent.setTextColor(itemView.getResources().getColor(R.color.colorTextSecondary));
            }
        }
    }

    public static class NotificationsAdapter extends RecyclerView.Adapter<ViewHolder> {

        List<NotificationModel> notificationModels;

        public NotificationsAdapter(List<NotificationModel> notificationModels) {
            this.notificationModels = notificationModels;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view  =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(notificationModels.get(position));

        }

        @Override
        public int getItemCount() {
            return notificationModels.size();
        }
    }

//    public static class SlotRequestedClickedEvent{
//        SlotRequestedModel model;
//
//        public SlotRequestedClickedEvent(SlotRequestedModel model) {
//            this.model = model;
//        }
//
//        public SlotRequestedModel getModel() {
//            return model;
//        }
//    }

}
