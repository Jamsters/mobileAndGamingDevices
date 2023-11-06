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
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements Runnable {

    /* Most variables will be declared private, will probably communicate through
    intents or broadcast receivers to get stuff from accelerometer or settings */

    private volatile boolean IsPlaying = false;
    private Thread GameThread;
    private long TimeThisFrame;
    private long FPS;
    private SurfaceHolder SurfaceHolder;
    private Bitmap Bitmap;
    private boolean IsMoving = true;
    private float XPos = 10, YPos = 10;
    private float Velocity = 250;
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


    public GameSurfaceView(Context context) {
        super (context);

        ImageAccess();
        LinAcc = new LinearAccelerometer(context);
        float X = LinAcc.getXAxis();
        float Y = LinAcc.getYAxis();





    }

    @Override
    public void run() {
        while (IsPlaying)
        {
            long StartFrameTime = System.currentTimeMillis();
            Update();
            Draw();
            TimeThisFrame = System.currentTimeMillis() - StartFrameTime;
            if (TimeThisFrame >= 1)
            {
                FPS = 1000 / TimeThisFrame;
            }
        }

    }

    public void Resume() {
        IsPlaying = true;

        GameThread = new Thread(this);
        GameThread.start();
        LinAcc.Resume();
    }

    public void Pause() {
        IsPlaying = false;
        LinAcc.Pause();
        try {
            GameThread.join();
        } catch (InterruptedException e) {
            Log.e("GameView", "Interrupted");
        }
    }

    public void Update() {
        if (IsMoving)
        {
            XPos = XPos + Velocity / FPS;
            if (XPos > getWidth())
            {
                YPos += FrameH;
                XPos = 10;
            }
            if (YPos + FrameH > getHeight())
            {
                YPos = 10;
            }
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

    // Stuff for "image access", just a way to separate draw code from the constructor
    public void ImageAccess(){
        SurfaceHolder = getHolder();
        Bitmap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.run);
        Bitmap = Bitmap.createScaledBitmap(Bitmap,FrameW*FrameCount,FrameH,false);
    }
}
