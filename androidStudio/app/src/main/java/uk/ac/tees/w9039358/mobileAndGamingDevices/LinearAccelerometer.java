package uk.ac.tees.w9039358.mobileAndGamingDevices;

import static android.hardware.SensorManager.SENSOR_DELAY_NORMAL;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.util.Log;

public class LinearAccelerometer {
    private SensorManager SenManager;
    private Sensor Sen;
    private LinAccEventListener SenEventListener;
    public LinearAccelerometer(Context context)
    {
        SenManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);


        if (SenManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
            // success, sensor found, now need to check for update and precision
            Log.d("GameSurfaceView", "Linear acceleration sensor found");
            //precision

            Sen = SenManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
            CreateNewLinAccEventListener();


            // LinAccSensor.
        }
        else {
            // failure, no sensor matching this type, should probably log and exit GameSurfaceView (game cant be played without it)
            Log.d("GameSurfaceView", "Linear acceleration sensor not found");
        }
    }
    public void CreateNewLinAccEventListener()
    {
        SenEventListener = new LinAccEventListener();
    }
    public void Resume(){
        if (SenEventListener != null)
        {
            SenEventListener.Resume(SenManager,Sen);
        }
    }

    public void Pause(){
        if (SenEventListener != null)
        {
            SenEventListener.Pause(SenManager);
        }
    }
}
