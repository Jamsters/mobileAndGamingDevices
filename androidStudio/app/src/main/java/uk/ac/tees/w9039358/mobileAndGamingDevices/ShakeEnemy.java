package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.util.Log;

public class ShakeEnemy extends Enemy{

    int BarrierHealth = 100;
    float LastShakeValue = 0.0f;
    float ShakeSpeedActivationThreshold = 1.0f;
    ShakeEnemy(GameController gameControllerReference, Vector2D topLeftPosition, String spriteName)
    {
        super(gameControllerReference,topLeftPosition,spriteName);
        Velocity.SetX(0);
        Velocity.SetY(-10);

        SpawnWeight = 1;
    }

    @Override
    public void Update() {
        super.Update();

        // Health stuff
        float LinAccInput = GameControllerReference.LinAcc.GetXAxis();
        HandleLinAccShakeInput(LinAccInput);

        BarrierHealthCheck();


    }


    public void HandleLinAccShakeInput(float currentShakeValue)
    {

        float ShakeSpeed = Math.abs(currentShakeValue - LastShakeValue);



        Log.d("ShakeEnemy.HandleLinAccShakeInput", (String)"currentShakeValue : " + Float.toString(currentShakeValue));
        Log.d("ShakeEnemy.HandleLinAccShakeInput", (String)"LastShakeValue : " + Float.toString(LastShakeValue));
        Log.d("ShakeEnemy.HandleLinAccShakeInput", (String)"ShakeSpeed : " + Float.toString(ShakeSpeed));

        if (ShakeSpeed > ShakeSpeedActivationThreshold)
        {

            BarrierHealth--;
            Log.d("ShakeEnemy.HandleLinAccShakeInput", (String)"Barrier health changed! It is now : " + Integer.toString(BarrierHealth));
        }

        LastShakeValue = currentShakeValue;


        //Log.d("ShakeEnemy.HandleLinAccShakeInput", (String)"Barrier health changed! It is now : " + Integer.toString(BarrierHealth));

    }

    public void BarrierHealthCheck()
    {
        if (BarrierHealth <= 0)
        {
            SetIsVisible(false);
        }
    }
}
