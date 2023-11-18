package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.ArrayList;

public class GameController implements Runnable {

    /* Most variables will be declared private, will probably communicate through
    intents or broadcast receivers to get stuff from accelerometer or settings */

    private volatile boolean IsPlaying = false;
    private Thread GameThread;
    private long FPS;
    private SurfaceHolder SurfaceHolder;
    private Bitmap Bitmap;
    private boolean IsMoving = true;
    private float XPos = 500, YPos = 10;
    private float Velocity = 5;

    /* Stuff for the run image at the moment. Change later */
    private int FrameW = 115, FrameH = 137;

    private LinearAccelerometer LinAcc;
    private long TickTime = 1000/50;

    // How many ticks have happened
    private float DeltaTime = 0.0f;
    // When logic was last used
    private long LastLogicTime = System.currentTimeMillis();
    // When vis was last used
    private long LastVisTime = System.currentTimeMillis();

    ArrayList<Entity> Entities = new ArrayList<Entity>();

    public Entity Player = new Player(200,200);

    public Visualization Vis;

    private Context VisContext;


    public GameController(Context context) {
        VisContext = context;
        LinAcc = new LinearAccelerometer(context);
        EntityInit();


        // Tick stuff here

        ImageAccess();


    }

    private void EntityInit()
    {
        AddToEntities(new Player(40,40));



    }

    private void AddToEntities(Entity entity)
    {
        Entities.add(entity);
    }

    // Game loop
    @Override
    public void run() {
        while (IsPlaying)
        {
            // TODO : Make an input function and put stuff for the linacc here
            //Input();
            long TimeSinceLastLogic = System.currentTimeMillis() - LastLogicTime;

            if (TimeSinceLastLogic >= TickTime)
            {
                Logic();

                LastLogicTime = System.currentTimeMillis();

                // Should I round this up?
                DeltaTime = TimeSinceLastLogic / TickTime;
            }
            //deltaTime = TimeSinceLastLogic / TickTime;

            Visualization();

            long TimeSinceLastVis = System.currentTimeMillis() - LastVisTime;


            // If larger than 1 millisecond? In other words, pretty much always?
            if (TimeSinceLastVis >= 1)
            {
                FPS = 1000 / TimeSinceLastVis;
                LastVisTime = System.currentTimeMillis();
            }



            Log.d("GameController.DeltaTime", (String)"DeltaTime: " + Float.toString(DeltaTime));
            Log.d("GameController.FPS", (String)"FPS: " + Long.toString(FPS));;


        }

    }

    private void Logic() {
        // Going to put stuff for updating everything here
        Update();
    }

    private void Visualization() {
        // Going to put stuff for drawing everything here
        Vis.Draw(Player.WhereToDraw,Player.XPos,Player.YPos);
    }

    public void Resume() {
        IsPlaying = true;

        GameThread = new Thread(this);
        GameThread.start();

        //TODO: LimAcc.Resume might need reordering, it needs to be the first thing started?
        LinAcc.Resume();
    }

    public void Pause() {
        IsPlaying = false;
        //TODO: LimAcc.Pause might need reordering, it needs to be the last thing stopped?
        LinAcc.Pause();
        try {
            GameThread.join();
        } catch (InterruptedException e) {
            Log.e("GameController", "Interrupted");
        }
    }

    public void Update() {
        if (IsMoving)
        {
            //BigDecimal BD_FPS = BigDecimal.valueOf(FPS);
            //BD_FPS.setScale(1,BigDecimal.ROUND_HALF_EVEN);


            // Init

            float Acceleration = LinAcc.GetXAxisRunningTotal(); // / FPS;

            float VelocityMultiplier = 10.0f;

            float elapsedTicks = DeltaTime + 1.0f;


            // Don't need to * acceleration by elapsed ticks because it already keeps track of missed acceleration by holding it
            Velocity = (Velocity + Acceleration /* elapsedTicks */) * VelocityMultiplier;
            XPos = XPos + Velocity * elapsedTicks;

            Velocity = 0;

            // TODO : Remember that the float we got is n


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
            if (Vis.GetScreenSize().GetX() != 0 && Vis.GetScreenSize().GetY() != 0)
            {
                // X Bounds Right
                if ((XPos+FrameW) > Vis.GetScreenSize().GetY())
                {
                    XPos = (Vis.GetScreenSize().GetY()-FrameW);
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
    /* This is for the practical work, it's where we access the image in the constructor. I'm putting it in a function
     to make it easier to move around later on*/



    // Stuff for "image access", just a way to separate draw init code from the constructor
    public void ImageAccess(){
        Vis = new Visualization(SurfaceHolder,Bitmap,VisContext);
    }


}
