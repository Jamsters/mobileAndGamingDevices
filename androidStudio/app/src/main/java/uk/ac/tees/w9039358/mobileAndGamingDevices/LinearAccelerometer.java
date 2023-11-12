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
import android.icu.math.BigDecimal;
import android.util.Log;

import java.text.Normalizer;

public class LinearAccelerometer implements Utilities{
    private final SensorManager SenManager;
    private final Sensor Sen;
    private final SensorEventListener SenEventListener;
    private float XAxisRunningTotal = 0.0f;
    private boolean isAccuracyPassable = false;
    private int SensorUpdateDelay = SENSOR_DELAY_GAME;
    private int SensorType = Sensor.TYPE_LINEAR_ACCELERATION;
    private boolean isLinAccCreated = false;

    // Rebound Barrier stuff

    private int ReboundBarrierValue = 0;
    private int NormalizationValue = 7;

    public LinearAccelerometer(Context context)
    {
        SenManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sen = SenManager.getDefaultSensor(SensorType);

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

            RegisterListener();
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
            // How many decimal points are we getting
            int TruncateScale = 1;

            int RoundingMethod = BigDecimal.ROUND_DOWN;

            float XAxis = event.values[0];

            // Truncate the float we get by putting it into a big decimal
            BigDecimal BD1 = BigDecimal.valueOf(XAxis).setScale(TruncateScale, RoundingMethod);

            float truncatedXAxis = BD1.floatValue();

            // Greater than this for a positive rebound barrier status
            float PositiveValueThreshold = 0.09f;
            // Less than this for a negative rebound barrier status
            float NegativeValueThreshold = -0.09f;

            // if positive or negative increment opposite direction barrier, if 0 decrement it.
            // because we've already truncated XAxis this should only increment if there's a significant change to acceleration
            int ReboundBarrierValueChange;



            if (truncatedXAxis > PositiveValueThreshold)
            {
                ReboundBarrierValueChange = 1;
            }
            else if (truncatedXAxis < NegativeValueThreshold)
            {
                ReboundBarrierValueChange = -1;
            }
            else
            {
                ReboundBarrierValue = 0;
                ReboundBarrierValueChange = 0;
                if (ReboundBarrierValue > 0)
                {
                    ReboundBarrierValueChange = -1;
                }
                else if (ReboundBarrierValue < 0)
                {
                    ReboundBarrierValueChange = 1;
                }
                else
                {
                    ReboundBarrierValueChange = 0;
                }

            }

            ReboundBarrierValue += ReboundBarrierValueChange;

            if ((ReboundBarrierValue > 0 && ReboundBarrierValueChange == 1) || (ReboundBarrierValue < 0 && ReboundBarrierValueChange == -1))
            {
                XAxisRunningTotal = truncatedXAxis / NormalizationValue;
                // Positive with a positive, negative with a negative
                //XAxisRunningTotal = XAxisRunningTotal + truncatedXAxis;
            }
            else
            {
                // Either zero or a current value that is the opposite of the barrier's positive or negative value.
            }

//            Log.d("LinearAccelerometer.TruncatedXAxis", (String)"TruncatedXAxis: " + Float.toString(truncatedXAxis));
//            Log.d("LinearAccelerometer.XAxisRunningTotal", (String)"X: " + Float.toString(XAxisRunningTotal));
//            Log.d("LinearAccelerometer", (String)"ReboundBarrierValue: " + ReboundBarrierValue);
//            Log.d("LinearAccelerometer", (String)"ReboundBarrierValueChange: " + ReboundBarrierValueChange);

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
    private void RegisterListener()
    {
        SenManager.registerListener(SenEventListener, Sen, SensorUpdateDelay);
    }
    private void UnregisterListener()
    {
        SenManager.unregisterListener(SenEventListener);
    }
    public void Resume(){
        if (isLinAccCreated == true)
        {
            RegisterListener();
            Log.i("LinearAccelerometer", "Sen listener resumed (registered)");
        }
    }
    public void Pause(){
        if (isLinAccCreated == true)
        {
            UnregisterListener();
            Log.i("LinearAccelerometer", "Sen listener paused (unregistered)");
        }
    }

    public float GetXAxisRunningTotal() {
        float x = XAxisRunningTotal;
        ResetXAxisRunningTotal();
        return x;

    }

    public void ResetXAxisRunningTotal () { XAxisRunningTotal = 0; }
}
