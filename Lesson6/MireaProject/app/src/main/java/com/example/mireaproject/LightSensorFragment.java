package com.example.mireaproject;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LightSensorFragment extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private TextView lightLevelText;
    private TextView logicText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_light_sensor, container, false);

        lightLevelText = view.findViewById(R.id.lightLevelText);
        logicText = view.findViewById(R.id.logicText);

        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor == null) {
            lightLevelText.setText("Датчик освещённости не поддерживается на устройстве.");
            logicText.setText("");
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float lightLevel = event.values[0];
        lightLevelText.setText("Уровень освещённости: " + lightLevel + " lx");

        if (lightLevel < 10) {
            logicText.setText("Вероятно, сейчас ночь или очень темно.");
        } else if (lightLevel < 1000) {
            logicText.setText("День или искусственное освещение.");
        } else {
            logicText.setText("Яркий дневной свет.");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
