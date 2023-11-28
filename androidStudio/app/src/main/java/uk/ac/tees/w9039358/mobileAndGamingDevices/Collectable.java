package uk.ac.tees.w9039358.mobileAndGamingDevices;

public class Collectable extends Entity{

    Collectable(GameController gameControllerReference, Vector2D topLeftPosition,String spriteName)
    {
        super(gameControllerReference, topLeftPosition, spriteName);
        Velocity = 0;
        YVelocity = 10;
        IsAlwaysMovingUp = true;

    }

    @Override
    public void Update() {
        Move();
    }

    protected void MoveImplementation(){
        KeepInBounds();
    }
}
