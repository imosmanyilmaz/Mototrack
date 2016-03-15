package com.example.vehicle.ui.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vehicle.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TTOACARGIL on 3/12/2016.
 */
public class SensorListAdapter extends RecyclerView.Adapter<SensorListAdapter.ViewHolder> implements View.OnClickListener {

    private final List<Sensor> sensorList;
    private final LayoutInflater layoutInflater;
    private SensorsFragment.OnSensorSelectedListener sensorSelectedCallback;

    public SensorListAdapter(Context context, SensorsFragment.OnSensorSelectedListener sensorSelectedCallback) {
        this.sensorSelectedCallback = sensorSelectedCallback;
        SensorManager mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> systemSensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        this.sensorList = new ArrayList<>();
        this.sensorList.addAll(systemSensorList);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public SensorListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = layoutInflater.inflate(R.layout.sensor_list_item, parent, false);
        v.setOnClickListener(this);

        // creating view holder
        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.nameTextView = (TextView) v.findViewById(R.id.sensor_name);
        viewHolder.vendorVersionTextView = (TextView) v.findViewById(R.id.sensor_vendor_version);
        viewHolder.powerConsumptionTextView = (TextView) v.findViewById(R.id.sensor_power_consumption);
        viewHolder.resolutionTextView= (TextView) v.findViewById(R.id.sensor_resolution);
        viewHolder.maximumRangeTextView = (TextView) v.findViewById(R.id.sensor_maximum_range);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SensorListAdapter.ViewHolder holder, int position) {
        Sensor sensor = sensorList.get(position);
        String vendorVersion = "Vendor: " + sensor.getVendor() + " v" + sensor.getVersion();
        String powerConsumption = "Power Consumption: " + sensor.getPower() + " mA";
        String resolution = "Resolution: " + sensor.getResolution();
        String maximumRange = "Maximum Range:" + sensor.getMaximumRange();
        //sensor.isWakeUpSensor()
        //sensor.getReportingMode() API LEVEL 21..

        holder.nameTextView.setText(sensor.getName());
        holder.vendorVersionTextView.setText(vendorVersion);
        holder.powerConsumptionTextView.setText(powerConsumption);
        holder.resolutionTextView.setText(resolution);
        holder.maximumRangeTextView.setText(maximumRange);
        holder.itemView.setTag(sensor);
    }

    @Override
    public int getItemCount() {
        return sensorList.size();
    }

    @Override
    public void onClick(View v) {
        Sensor sensor = (Sensor) v.getTag();
        sensorSelectedCallback.sensorSelected(sensor);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView vendorVersionTextView;
        private TextView powerConsumptionTextView;
        private TextView resolutionTextView;
        private TextView maximumRangeTextView;

        public ViewHolder(View itemView) {
            super(itemView); // this is itemView in super
        }
    }
}
