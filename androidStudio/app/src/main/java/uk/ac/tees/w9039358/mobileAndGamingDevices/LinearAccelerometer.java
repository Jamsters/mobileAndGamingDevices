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

public class LinearAccelerometer {
    private final SensorManager SenManager;
    private Sensor Sen;
    private final SensorEventListener SenEventListener;
    private float XAxisRunningTotal = 0.0f;

    private float XAxis = 0.0f;
    private boolean isAccuracyPassable = false;
    private int SensorUpdateDelay = SENSOR_DELAY_GAME;
    private int SensorType = Sensor.TYPE_LINEAR_ACCELERATION;
    private boolean isLinAccCreated = false;
    private int NormalizationValue = 7;
    private ReboundBarrier RB = new ReboundBarrier();

    public LinearAccelerometer(Context context)
    {
        SenManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        if (SenManager.getDefaultSensor(SensorType) != null)
        {
            Sen = SenManager.getDefaultSensor(SensorType);

            Log.i("LinearAccelerometer", "Linear acceleration sensor found");

            RegisterListener();
            Log.i ("LinearAccelerometer", "Sen listener created and started");

            isLinAccCreated = true;
        }
        else
        {
            // failure
            Log.e("LinearAccelerometer", "Linear acceleration sensor not found");
        }


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
//            // How many decimal points are we getting
//            int TruncateScale = 1;
//
//            int RoundingMethod = BigDecimal.ROUND_DOWN;
//
//            float XAxis = event.values[0];
//
//            // Truncate the float we get by putting it into a big decimal
//            BigDecimal BD1 = BigDecimal.valueOf(XAxis).setScale(TruncateScale, RoundingMethod);
//
//            float TruncatedXAxis = BD1.floatValue();
//
//            // Going to normalize the truncated value, this assumes that all accelerometers use the normalization value as their max acceleration
//            float NormalizedTruncatedXAxis = TruncatedXAxis / NormalizationValue;
//
//            if (RB.DoesReboundBarrierExist(TruncatedXAxis))
//            {
//
//                XAxisRunningTotal += NormalizedTruncatedXAxis;
//            }

            // Implementation for using Lin Acc for shake
            XAxis = event.values[0];


//            Log.d("LinearAccelerometer.TruncatedXAxis", (String)"TruncatedXAxis: " + Float.toString(TruncatedXAxis));
//            Log.d("LinearAccelerometer.TruncatedXAxis", (String)"Normalized TruncatedXAxis: " + Float.toString(NormalizedTruncatedXAxis));
            Log.d("LinearAccelerometer.XAxisRunningTotal", (String)"Normalized XAxisRunningTotal: " + Float.toString(XAxisRunningTotal));
            Log.d("LinearAccelerometer.EndLine", (String)"\n");


        }
        else
        {
            // Accuracy is bad so we don't have to do anything
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
    private boolean RegisterListener()
    {
        return SenManager.registerListener(SenEventListener, Sen, SensorUpdateDelay);
    }
    private void UnregisterListener()
    {
        SenManager.unregisterListener(SenEventListener);
    }
    public void Resume(){
        if (isLinAccCreated == true)
        {
            if (RegisterListener())
            {
                Log.i("LinearAccelerometer", "Sen listener resumed (registered)");
            }
            else
            {
                Log.i("LinearAccelerometer", "Sen listener failed to resume (on registered)");
            }

        }
    }
    public void Pause(){
        if (isLinAccCreated == true)
        {
            UnregisterListener();
            Log.i("LinearAccelerometer", "Sen listener paused (unregistered)");
        }
    }

    public float GetXAxis()
    {
        return XAxis;
    }
    public float GetXAxisRunningTotal() {
        float x = XAxisRunningTotal;
        ResetXAxisRunningTotal();
        return x;

    }

    public void ResetXAxisRunningTotal () {
        XAxisRunningTotal = 0;
        Log.d("LinearAccelerometer.ResetAxisRunningTotal", (String)"AxisRunningTotal has been reset \n");
    }
}
