package com.syncplay

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView

internal class DeviceListAdapter(private val activity: Activity) : RecyclerView.Adapter<DeviceListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var textName: TextView = itemView.findViewById(R.id.textName) as TextView
        internal var textID: TextView = itemView.findViewById(R.id.textID) as TextView
        internal var attachSwitch: Switch = itemView.findViewById(R.id.attachSwitch) as Switch

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            = ViewHolder(activity.layoutInflater.inflate(R.layout.list_item_view_device, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val device = DeviceManager.getDevice(position)

        holder.textName.text = device.name
        holder.textID.text = device.deviceID
        holder.attachSwitch.isChecked = device.isAttached

        holder.attachSwitch.setOnClickListener { v ->
            if ((v as Switch).isChecked) {
                device.attach()
            } else {
                device.deattach()
            }
        }
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemCount(): Int = DeviceManager.size()
}
