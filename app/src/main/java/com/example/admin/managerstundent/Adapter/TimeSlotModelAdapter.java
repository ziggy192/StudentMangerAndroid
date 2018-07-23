package com.example.admin.managerstundent.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.managerstundent.Entity.TimeSlotModel;

import java.util.List;

public class TimeSlotModelAdapter extends BaseAdapter {

    private List<TimeSlotModel> timeSlotModelList;

    public TimeSlotModelAdapter(List<TimeSlotModel> timeSlotModelList) {
        this.timeSlotModelList = timeSlotModelList;
    }

    @Override
    public int getCount() {
        return timeSlotModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return timeSlotModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return timeSlotModelList.indexOf(timeSlotModelList.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
        }
        TimeSlotModel model = timeSlotModelList.get(position);

        TextView tvTime = convertView.findViewById(android.R.id.text1);
        TextView tvDayOfWeek = convertView.findViewById(android.R.id.text2);
        tvTime.setText(model.getTime());
        tvDayOfWeek.setText(model.getDayOfWeek());
        return convertView;
    }
}
