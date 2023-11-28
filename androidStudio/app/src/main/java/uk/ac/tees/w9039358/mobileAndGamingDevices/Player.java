package uk.ac.tees.w9039358.mobileAndGamingDevices;

public class Player extends Entity{

    Player(GameController gameControllerReference, float xPos, float yPos,String spriteName)
    {
        super(gameControllerReference, xPos,yPos, spriteName);
        Velocity = 10;
        YVelocity = 0;

    }

    @Override
    protected void Draw() {

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

        float Difference = MoveInput - XPos;

        float Acceleration = GameControllerReference.SingleTouchReference.GetXMovement(); // / FPS;

        float VelocityMultiplier = 1.0f;

        // TODO : Getter for delta time instead of direct
        float elapsedTicks = GameControllerReference.DeltaTime + 1.0f;



        // Don't need to * acceleration by elapsed ticks because it already keeps track of missed acceleration by holding it
        Velocity = (Velocity + Acceleration /* elapsedTicks */) * VelocityMultiplier;
        XPos = XPos + Velocity * elapsedTicks;

        XPos = Acceleration;


        // getWidth and getHeight are 0 at launch, it might be possible to change that and avoid this first if statement?
        if (GameControllerReference.Vis.GetScreenSize().GetX() != 0 && GameControllerReference.Vis.GetScreenSize().GetY() != 0)
        {
            // X Bounds Right
            if ((XPos+TempSprite.FrameW > GameControllerReference.Vis.GetScreenSize().GetX()))
            {
                XPos = (GameControllerReference.Vis.GetScreenSize().GetX()-TempSprite.FrameW);
            }

            // X Bounds Left
            if ((XPos) < 0)
            {
                XPos = 0;
            }
        }

    }
}
