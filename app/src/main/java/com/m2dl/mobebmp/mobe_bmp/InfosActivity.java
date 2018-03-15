package com.m2dl.mobebmp.mobe_bmp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class InfosActivity  extends MobeBMPActivity implements SensorEventListener {

    private SensorManager sm = null;
    private TextView luminosityValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensor = event.sensor.getType();
        float [] values = event.values;

        synchronized (this) {
            if (sensor == Sensor.TYPE_LIGHT) {
                float luminosity = values[0];
                luminosityValue = (TextView)findViewById(R.id.luminosity_value);
                luminosityValue.setText(""+luminosity);

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor lightSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        //Sensor audioSenssor = sm.getDefaultSensor(Sensor.);
        sm.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        sm.unregisterListener(this, sm.getDefaultSensor(Sensor.TYPE_LIGHT));
        super.onStop();
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }
}
