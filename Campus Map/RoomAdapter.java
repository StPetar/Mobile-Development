package com.example.madcampusmap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RoomAdapter extends BaseAdapter {

    private List<String> rooms;

    public RoomAdapter(List<String> rooms) { this.rooms = rooms; }

    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public Object getItem(int position) {
        return rooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        String build = rooms.get(position);


        if(convertView == null){
            LayoutInflater lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = lf.inflate(R.layout.room_adapter, null, false);
        }

        TextView building = convertView.findViewById(R.id.building);
        building.setText(build);

        return convertView;
    }


}