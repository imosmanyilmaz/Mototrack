package com.example.vehicle.ui.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vehicle.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SensorsFragment extends Fragment {

    public static final String TAG = "SensorsFragment";

    private RecyclerView sensorList;
    private SensorListAdapter sensorListAdapter;
    private RecyclerView.LayoutManager sensorListLayoutManager;

    private OnSensorSelectedListener sensorSelectedListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_sensors, container, false);
        sensorList = (RecyclerView) fragment.findViewById(R.id.sensor_list);
        sensorList.setHasFixedSize(true);
        sensorList.setVerticalScrollBarEnabled(false);

        sensorListLayoutManager = new LinearLayoutManager(getActivity());
        sensorList.setLayoutManager(sensorListLayoutManager);
        sensorList.setAdapter(sensorListAdapter);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            sensorSelectedListener = (OnSensorSelectedListener) getActivity();
            sensorListAdapter = new SensorListAdapter(getContext(), sensorSelectedListener);
        } catch (ClassCastException e) {}
    }

    public interface OnSensorSelectedListener {
        void sensorSelected(Sensor sensor);
    }

}
