package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.util.Log;

public class ShakeEnemy extends Enemy{
    // TODO : Try a lower barrier health but higher shake speed activation threshold
    int BarrierHealth = 100;
    float LastShakeValue = 0.0f;
    float ShakeSpeedActivationThreshold = 0.75f;
    ShakeEnemy(GameController gameControllerReference, Vector2D topLeftPosition, String spriteName, boolean spawnsAtStart)
    {
        super(gameControllerReference,topLeftPosition,spriteName, spawnsAtStart);
        Velocity.SetX(0);
        Velocity.SetY(-10);

        SetSpawnWeight(1);
    }

    @Override
    public void Update() {
        super.Update();

        BarrierHealthCheck();

    }
    @Override
    protected void MoveImplementation() {
        super.MoveImplementation();
        float LinAccInput = GameControllerReference.LinAcc.GetXAxis();
        HandleLinAccShakeInput(LinAccInput);
    }

    @Override
    protected boolean IsSpawnConditionMet()
    {
        // No other shake enemy on screen
        return !GameControllerReference.IsSameClassEntityInPlay(this);
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
            AliveToggle(false);
        }
    }
}
