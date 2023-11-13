package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.math.BigDecimal;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements Runnable {

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
    /* The canvas is what we're drawing onto */
    private Canvas canvas;


    /* Stuff for the run image at the moment. Change later */
    private int FrameW = 115, FrameH = 137;
    private int FrameCount = 8;
    private int FrameLengthInMS = 100;
    private long LastFrameChangeTime = 0;
    private int CurrentFrame = 0;
    private Rect FrameToDraw = new Rect(0,0,FrameW,FrameH);
    private RectF WhereToDraw = new RectF(XPos,YPos,XPos+FrameW,FrameH);
    private SensorManager SensorManager;
    private LinearAccelerometer LinAcc;
    private long TickTime = 1000/50;

    // How many ticks have happened
    private float DeltaTime = 0.0f;
    // When logic was last used
    private long LastLogicTime = System.currentTimeMillis();
    // When vis was last used
    private long LastVisTime = System.currentTimeMillis();


    public GameSurfaceView(Context context) {
        super (context);
        LinAcc = new LinearAccelerometer(context);

        // Tick stuff here

        ImageAccess();


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



            Log.d("GameSurfaceView.DeltaTime", (String)"DeltaTime: " + Float.toString(DeltaTime));
            Log.d("GameSurfaceView.FPS", (String)"FPS: " + Long.toString(FPS));;


        }

    }

    private void Logic() {
        // Going to put stuff for updating everything here
        Update();
    }

    private void Visualization() {
        // Going to put stuff for drawing everything here
        Draw();
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
            Log.e("GameSurfaceView", "Interrupted");
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
            //Log.d("GameSurfaceView", s);

            // getWidth and getHeight are 0 at launch, it might be possible to change that and avoid this first if statement?
            if (getWidth() != 0 && getHeight() != 0)
            {
                // X Bounds Right
                if ((XPos+FrameW) > getWidth())
                {
                    XPos = (getWidth()-FrameW);
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

    public void Draw(){
        if (SurfaceHolder.getSurface().isValid())
        {
            canvas = SurfaceHolder.lockCanvas();
            /* TODO : Can I use one of the resource colour strings for this? Maybe this? Or maybe its a different colour format because this one
                is an android graphics one
            *   canvas.drawColor(getResources().getColor(R.color.ColourWhite)); */
            canvas.drawColor(Color.WHITE);
            WhereToDraw.set(XPos, YPos, XPos+FrameW, YPos+FrameH);
            ManageCurrentFrame();
            canvas.drawBitmap(Bitmap,FrameToDraw,WhereToDraw,null);
            SurfaceHolder.unlockCanvasAndPost(canvas);


        }

    }
    /* This is for the practical work, it's where we access the image in the constructor. I'm putting it in a function
     to make it easier to move around later on*/

    public void ManageCurrentFrame()
    {
        long Time = System.currentTimeMillis();
        if (IsMoving) {
            if (Time > LastFrameChangeTime + FrameLengthInMS)
            {
                LastFrameChangeTime = Time;
                CurrentFrame++;
                if (CurrentFrame >= FrameCount)
                {
                    CurrentFrame = 0;
                }
            }

        }
        FrameToDraw.left = CurrentFrame * FrameW;
        FrameToDraw.right = FrameToDraw.left + FrameW;
    }

    // Stuff for "image access", just a way to separate draw init code from the constructor
    public void ImageAccess(){
        SurfaceHolder = getHolder();
        Bitmap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.run);
        Bitmap = Bitmap.createScaledBitmap(Bitmap,FrameW*FrameCount,FrameH,false);
    }
}
