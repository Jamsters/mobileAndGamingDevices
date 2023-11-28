package uk.ac.tees.w9039358.mobileAndGamingDevices;

public class Player extends Entity{

    Player(GameController gameControllerReference, Vector2D topLeftPosition,String spriteName)
    {
        super(gameControllerReference, topLeftPosition, spriteName);
        Velocity = 10;
        YVelocity = 0;

    }

    @Override
    public void Update() {
        Move();
    }
    @Override
    protected void MoveImplementation()
    {
        // Init
        float MoveInput = GameControllerReference.SingleTouchReference.GetXMovement();

        float Difference = MoveInput - Position.GetXPos();

        float Acceleration = GameControllerReference.SingleTouchReference.GetXMovement(); // / FPS;

        float VelocityMultiplier = 1.0f;

        // TODO : Getter for delta time instead of direct
        float elapsedTicks = GameControllerReference.DeltaTime + 1.0f;



        // Don't need to * acceleration by elapsed ticks because it already keeps track of missed acceleration by holding it
        Velocity = (Velocity + Acceleration /* elapsedTicks */) * VelocityMultiplier;
        Position.SetXPos(Position.GetXPos() + Velocity * elapsedTicks);

        Position.SetXPos(Acceleration);

        KeepInBounds();




    }
}
