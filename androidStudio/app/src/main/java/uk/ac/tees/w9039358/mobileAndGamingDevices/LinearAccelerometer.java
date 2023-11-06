package uk.ac.tees.w9039358.mobileAndGamingDevices;

import static android.hardware.SensorManager.SENSOR_DELAY_GAME;
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

import java.text.Normalizer;

public class LinearAccelerometer implements Utilities{
    private final SensorManager SenManager;
    private final Sensor Sen;
    private final SensorEventListener SenEventListener;
    private float XAxis;
    private boolean isAccuracyPassable = false;
    private int SensorUpdateDelay = SENSOR_DELAY_GAME;
    private boolean isLinAccCreated = false;
    public LinearAccelerometer(Context context)
    {
        SenManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sen = SenManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        // Call functions of LinearAccelerometer to manage the sensor event listeners methods
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
            Log.i("LinearAccelerometer", "Linear acceleration sensor found");


            SenManager.registerListener(SenEventListener, Sen, SenManager.SENSOR_DELAY_NORMAL);
            Log.i ("LinearAccelerometer", "Sen listener created and started");

            isLinAccCreated = true;


        }
        else {
            // failure, no sensor matching this type, should probably log and exit GameSurfaceView (game cant be played without it)
            Log.e("LinearAccelerometer", "Linear acceleration sensor not found");
        }
    }
    private void AccuracyChanged(int accuracy) {
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
    private void SensorChanged(SensorEvent event)
    {
        if (isAccuracyPassable == true)
        {
            XAxis = event.values[0];

            float min = 0.1f;
            float max = 0.9f;
            Utilities.clamp(XAxis,min,max);



            String s = "X: " + Float.toString(XAxis);
            Log.d("LinearAccelerometer", s);;
        }
        else
        {
        }
    }
    private void AccuracyPassable()
    {
        isAccuracyPassable = true;
    }
    private void AccuracyBad()
    {
        isAccuracyPassable = false;
    }
    public void Resume(){
        if (isLinAccCreated == true)
        {
            SenManager.registerListener(SenEventListener, Sen, SensorUpdateDelay);
            Log.i("LinearAccelerometer", "Sen listener resumed (registered)");
        }
    }
    public void Pause(){
        if (isLinAccCreated == true)
        {
            SenManager.unregisterListener(SenEventListener);
            Log.i("LinearAccelerometer", "Sen listener paused (unregistered)");
        }
    }

    public float GetXAxis() {
        float x = XAxis;
        ResetXAxis();
        return x;

    }

    public void ResetXAxis () { XAxis = 0; }
}
