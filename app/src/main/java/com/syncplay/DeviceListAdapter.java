package com.syncplay;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by vishal on 11/5/16.
 */

class DeviceListAdapter extends BaseAdapter {
    private Activity activity;

    DeviceListAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return DeviceManager.size();
    }

    @Override
    public Object getItem(int position) {
        return DeviceManager.getDevice(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder{

        TextView textName;
        TextView textID;
        Switch attachSwitch;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;

        if(convertView == null){

            v = activity.getLayoutInflater().inflate(R.layout.view_device, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.textName = (TextView) v.findViewById(R.id.textName);
            holder.textID = (TextView)v.findViewById(R.id.textID);
            holder.attachSwitch = (Switch)v.findViewById(R.id.attachSwitch);

            /************  Set holder with LayoutInflater ************/
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        if(DeviceManager.size() > 0)
        {
            final PlaybackDevice device = DeviceManager.getDevice(position);

            holder.textName.setText(device.getName());
            holder.textID.setText(device.getDeviceID());
            holder.attachSwitch.setChecked(device.attached());

            /******** Set Item Click Listner for LayoutInflater for each row *******/

            holder.attachSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((Switch)v).isChecked()) {
                        device.attach();
                    } else {
                        device.deattach();
                    }
                }
            });
        }

        return v;
    }
}
