package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.util.Log;

public class Collectable extends Entity{

    int Value = 1;

    Collectable(GameController gameControllerReference, Vector2D topLeftPosition,String spriteName)
    {
        super(gameControllerReference, topLeftPosition, spriteName);
        Velocity.SetY(-10);
    }

    @Override
    public void Update() {
        Move();
    }

    @Override
    public void OnCollision(Entity collider)
    {
        if (collider.getClass() == Player.class)
        {
            OnPlayerCollision();
        }
    }

    protected void MoveImplementation(){
        DefaultMoveImplementation();
        KeepInHorizontalBounds();
        KeepInVerticalBounds();
    }

    // TODO : Investigate making this an interface
    protected void OnPlayerCollision()
    {
        Log.i("Collectable.OnCollision", "QUICK TEST");
        if (GetIsVisible())
        {
            GameControllerReference.PlayerReference.AddScore(Value);
            SetIsVisible(false);
        }


    }
}
