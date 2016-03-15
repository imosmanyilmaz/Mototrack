package com.example.vehicle.ui;

import android.hardware.Sensor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.vehicle.R;
import com.example.vehicle.ui.sensors.SensorInfoFragment;
import com.example.vehicle.ui.sensors.SensorsFragment;

public class MainActivity extends AppCompatActivity implements SensorsFragment.OnSensorSelectedListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, new SensorsFragment())
                .commit();
    }

    @Override
    public void sensorSelected(Sensor sensor) {
        SensorInfoFragment sensorInfoFragment = SensorInfoFragment.newInstance(sensor);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, sensorInfoFragment)
                .addToBackStack(null)
                .commit();

    }
}
