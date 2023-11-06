package uk.ac.tees.w9039358.mobileAndGamingDevices;

import static android.hardware.SensorManager.SENSOR_STATUS_ACCURACY_HIGH;
import static android.hardware.SensorManager.SENSOR_STATUS_ACCURACY_LOW;
import static android.hardware.SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM;
import static android.hardware.SensorManager.SENSOR_STATUS_NO_CONTACT;
import static android.hardware.SensorManager.SENSOR_STATUS_UNRELIABLE;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class LinAccEventListener implements SensorEventListener {
    private float XAxis;
    private float YAxis;
    private float ZAxis;
    private boolean isAccuracyPassable = false;

    // Wrapper for sensor event listener
    public LinAccEventListener()
    {

    }
    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // I'd use the enhanced switch here to avoid repeating, but this language level of java doesn't support it
        switch (accuracy) {
            case SENSOR_STATUS_ACCURACY_LOW:
                AccuracyPassable();
                break;
            case SENSOR_STATUS_ACCURACY_MEDIUM:
                AccuracyPassable();
                break;
            case SENSOR_STATUS_ACCURACY_HIGH:
                AccuracyPassable();
                break;
            default:
                AccuracyBad();
        }
    }
    @Override
    public final void onSensorChanged(SensorEvent event) {
        XAxis = event.values[0];
        YAxis = event.values[1];
        ZAxis = event.values[2];
        if (isAccuracyPassable = true)
        {
            String s = "X: " + Float.toString(XAxis) + " Y: " + Float.toString(YAxis) + " Z: " + Float.toString(ZAxis);
            Log.d("LinAccEventListener", s);
        }
    }

    protected void Resume(SensorManager sensorManager, Sensor sensor) {
        sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_NORMAL);
        Log.e("LinAccEventListener", "Resume working");
    }
    protected void Pause(SensorManager sensorManager) {
        sensorManager.unregisterListener(this);
        Log.e("LinAccEventListener", "Pause working");
    }

    public void AccuracyPassable()
    {
        isAccuracyPassable = true;
    }

    public void AccuracyBad()
    {
        isAccuracyPassable = false;
    }
}
