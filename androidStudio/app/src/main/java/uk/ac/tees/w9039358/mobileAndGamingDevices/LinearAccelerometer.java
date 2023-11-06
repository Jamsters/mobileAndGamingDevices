package uk.ac.tees.w9039358.mobileAndGamingDevices;

import static android.hardware.SensorManager.SENSOR_DELAY_NORMAL;
import static android.hardware.SensorManager.SENSOR_STATUS_ACCURACY_HIGH;
import static android.hardware.SensorManager.SENSOR_STATUS_ACCURACY_LOW;
import static android.hardware.SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class LinearAccelerometer {
    private final SensorManager SenManager;
    private final Sensor Sen;
    private final SensorEventListener SenEventListener;
    private float XAxis;
    private float YAxis;
    private float ZAxis;
    private boolean isAccuracyPassable = false;
    private int SensorUpdateDelay = SENSOR_DELAY_NORMAL;
    private boolean isLinAccCreated = false;
    public LinearAccelerometer(Context context)
    {
        SenManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sen = SenManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        SenEventListener = new SensorEventListener() {
            @Override
            public final void onAccuracyChanged(Sensor sensor, int accuracy) {
                AccuracyChanged(accuracy);
            }

            @Override
            public final void onSensorChanged(SensorEvent event) {
                SensorChanged(event);
            }
        };

        if (Sen != null) {
            // success, sensor found, now register listener to check for updates and precision
            Log.d("LinearAccelerometer", "Linear acceleration sensor found");


            SenManager.registerListener(SenEventListener, Sen, SenManager.SENSOR_DELAY_NORMAL);
            Log.d ("LinearAccelerometer", "Sen listener created and started");

            // Call functions of LinearAccelerometer to manage the sensor event listeners methods
        }
        else {
            // failure, no sensor matching this type, should probably log and exit GameSurfaceView (game cant be played without it)
            Log.d("LinearAccelerometer", "Linear acceleration sensor not found");
        }
    }
    public void AccuracyChanged(int accuracy) {
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
    public void SensorChanged(SensorEvent event)
    {
        XAxis = event.values[0];
        YAxis = event.values[1];
        ZAxis = event.values[2];
        if (isAccuracyPassable = true)
        {
            String s = "X: " + Float.toString(XAxis) + " Y: " + Float.toString(YAxis) + " Z: " + Float.toString(ZAxis);
            Log.d("LinearAccelerometer", s);
        }
    }
    public void AccuracyPassable()
    {
        isAccuracyPassable = true;
    }
    public void AccuracyBad()
    {
        isAccuracyPassable = false;
    }
    public void Resume(){
        if (SenManager != null && SenEventListener != null && Sen != null)
        {
            SenManager.registerListener(SenEventListener, Sen, SensorUpdateDelay);
            Log.e("LinearAccelerometer", "Sen listener resumed (registered)");
        }
    }
    public void Pause(){
        if (SenManager != null && SenEventListener != null)
        {
            SenManager.unregisterListener(SenEventListener);
            Log.e("LinearAccelerometer", "Sen listener paused (unregistered)");
        }
    }
}