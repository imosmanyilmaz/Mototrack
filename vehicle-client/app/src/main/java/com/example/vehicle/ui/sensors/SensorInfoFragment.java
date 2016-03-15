package com.example.vehicle.ui.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vehicle.R;


/**
 */
public class SensorInfoFragment extends Fragment implements SensorEventListener {

    public static final String TAG = "SensorEventListener";

    private TextView nameTextView;
    private TextView vendorVersionTextView;
    private TextView powerConsumptionTextView;
    private TextView resolutionTextView;
    private TextView maximumRangeTextView;

    private TextView valueTextView1;
    private TextView valueTextView2;
    private TextView valueTextView3;

    private SensorManager sensorManager;
    private Sensor sensor;
    private float[] gravity;


    public SensorInfoFragment() {
        this.gravity = new float[3];
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public static SensorInfoFragment newInstance(Sensor sensor) {
        SensorInfoFragment fragment = new SensorInfoFragment();
        fragment.setSensor(sensor);
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
        View sensorInfoView = inflater.inflate(R.layout.fragment_sensor_info, container, false);
        nameTextView = (TextView) sensorInfoView.findViewById(R.id.sensor_name);
        vendorVersionTextView = (TextView) sensorInfoView.findViewById(R.id.sensor_vendor_version);
        powerConsumptionTextView = (TextView) sensorInfoView.findViewById(R.id.sensor_power_consumption);
        resolutionTextView = (TextView) sensorInfoView.findViewById(R.id.sensor_resolution);
        maximumRangeTextView = (TextView) sensorInfoView.findViewById(R.id.sensor_maximum_range);

        valueTextView1 = (TextView) sensorInfoView.findViewById(R.id.valueTextView1);
        valueTextView2 = (TextView) sensorInfoView.findViewById(R.id.valueTextView2);
        valueTextView3 = (TextView) sensorInfoView.findViewById(R.id.valueTextView3);

        String vendorVersion = "Vendor: " + sensor.getVendor() + " v" + sensor.getVersion();
        String powerConsumption = "Power Consumption: " + sensor.getPower() + " mA";
        String resolution = "Resolution: " + sensor.getResolution();
        String maximumRange = "Maximum Range:" + sensor.getMaximumRange();
        //sensor.isWakeUpSensor()
        //sensor.getReportingMode() API LEVEL 21..

        nameTextView.setText(sensor.getName());
        vendorVersionTextView.setText(vendorVersion);
        powerConsumptionTextView.setText(powerConsumption);
        resolutionTextView.setText(resolution);
        maximumRangeTextView.setText(maximumRange);

        valueTextView1.setText("Value1: 0");
        valueTextView2.setText("Value2: 0");
        valueTextView3.setText("Value3: 0");

        return sensorInfoView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String sensorName = event.sensor.getName();
        Log.d(TAG, "onSensorChanged|" + sensorName + "|" + event.toString());
        float[] values = event.values;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER && values.length == 3) {
            float alpha = 0.8f;
            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

            float value1 = event.values[0] - gravity[0];
            float value2 = event.values[1] - gravity[1];
            float value3 = event.values[2] - gravity[2];

            valueTextView1.setText("Value1: " + value1);
            valueTextView2.setText("Value2: " + value2);
            valueTextView3.setText("Value3: " + value3);
        }
        else if (values.length == 3) {
            valueTextView1.setText("Value1: " + values[0]);
            valueTextView2.setText("Value2: " + values[1]);
            valueTextView3.setText("Value3: " + values[2]);
        }
        else if (values.length == 1) {
            valueTextView1.setText("Value1: " + values[0]);
            valueTextView2.setVisibility(View.GONE);
            valueTextView3.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        String sensorName = sensor.getName();
        Log.d(TAG, "onAccuracyChanged|" + sensorName + "|" + accuracy);
    }
}
