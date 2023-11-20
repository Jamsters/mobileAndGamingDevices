package uk.ac.tees.w9039358.mobileAndGamingDevices;

public class Player extends Entity{

    Player(GameController gameControllerReference, float xPos, float yPos)
    {
        super(gameControllerReference, xPos,yPos);
        Velocity = 0;

    }

    @Override
    protected void Draw() {

    }

    @Override
    public void Update() {
        if (IsMoving == true) {
            Move();
        }
    }
@Override
    protected void Move()
    {


        //BigDecimal BD_FPS = BigDecimal.valueOf(FPS);
        //BD_FPS.setScale(1,BigDecimal.ROUND_HALF_EVEN);


        // Init
        float Acceleration = GameControllerReference.LinAcc.GetXAxisRunningTotal();; // / FPS;

        float VelocityMultiplier = 10.0f;

        // TODO : Getter for delta time instead of direct
        float elapsedTicks = GameControllerReference.DeltaTime + 1.0f;



        // Don't need to * acceleration by elapsed ticks because it already keeps track of missed acceleration by holding it
        Velocity = (Velocity + Acceleration /* elapsedTicks */) * VelocityMultiplier;
        XPos = XPos + Velocity * elapsedTicks;

        Velocity = 0;



        //BigDecimal bd1 = BigDecimal.valueOf(Acceleration);
        //bd1.setScale(1,BigDecimal.ROUND_UP);

        //float move = bd1.floatValue();

        //XPos = XPos + move;

        //Velocity = 0;

        //Velocity = Velocity - move;



        //int DeaccelerationMultiplier = AccelerationMultiplier;

        // Acceleration






        // Change XPos of sprite


        // Deacceleration

        // Deacceleration time calc
        //double accSensorUpdateDelay = 0.02;
        //double deaccTime = TimeThisFrame / accSensorUpdateDelay;
        //BigDecimal BD1 = BigDecimal.valueOf(deaccTime).setScale(2,BigDecimal.ROUND_HALF_UP);
        //Deacceleration = DeaccelerationMultiplier * BD1.floatValue();






        // If velocity is greater than deacceleration value or less than negative deacceleration
        // We know that we can change velocity by deacceleration value without giving acceleration in the opposite direction.
        // Else we will just set velocity to 0
/*            if (Velocity > Deacceleration)
            {
                Velocity = Velocity - Deacceleration;

            }
            else if (Velocity < -Deacceleration)
            {
                Velocity = Velocity + Deacceleration;
            }
            else
            {
                Velocity = 0;
            }

 */



        //XPos = XPos + ((LinAccMultiplier*LinAcc.GetXAxis()));

        // Applying deacceleration to velocity
        //Velocity = Velocity - (DeaccelerationMultiplier*FPS);

        // Logging getWidth()
        //String s = "Width: " + Float.toString(getWidth());
        //Log.d("GameController", s);

        // getWidth and getHeight are 0 at launch, it might be possible to change that and avoid this first if statement?
        if (GameControllerReference.Vis.GetScreenSize().GetX() != 0 && GameControllerReference.Vis.GetScreenSize().GetY() != 0)
        {
            // X Bounds Right
            if ((XPos+TempSprite.FrameW) > GameControllerReference.Vis.GetScreenSize().GetX())
            {
                XPos = (GameControllerReference.Vis.GetScreenSize().GetX()-TempSprite.FrameW);
            }

            // X Bounds Left
            if ((XPos) < 0)
            {
                XPos = 0;
            }
        }


        // This is to stop it from going out of bounds
/*            if (XPos > getWidth())
            {
                YPos += FrameH;
                //XPos = 10;
            }
            if (YPos + FrameH > getHeight())
            {
                YPos = 10;
            }
*/


    }
}
