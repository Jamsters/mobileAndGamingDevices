package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.util.Log;

public class Collectable extends Entity{

    int Value = 1;

    Collectable(GameController gameControllerReference, Vector2D topLeftPosition,String spriteName, boolean spawnsAtStart)
    {
        super(gameControllerReference, topLeftPosition, spriteName, spawnsAtStart);

        MoveSpeed = 10.0f;
        Velocity.SetY(-MoveSpeed);
        //Velocity.SetX(MoveSpeed);

        SetSpawnWeight(10);
    }

    @Override
    public void Update() {
        Move();
    }

    @Override
    public void OnCollisionImplementation(Entity collider)
    {
        if (collider.getClass() == Player.class)
        {
            OnPlayerCollision();
        }
    }

    protected void MoveImplementation(){
        DefaultMoveImplementation();
        //KeepInTopBounds();
        KeepInHorizontalBounds();
    }

    // TODO : Investigate making this an interface
    protected void OnPlayerCollision()
    {
        Log.i("Collectable.OnCollision", "QUICK TEST");
        if (GetIsVisible())
        {
            GameControllerReference.PlayerReference.AddScore(Value);
            AliveToggle(false);
        }


    }
}
