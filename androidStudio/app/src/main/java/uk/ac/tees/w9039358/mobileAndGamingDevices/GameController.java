package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

import java.util.ArrayList;

public class GameController implements Runnable {

    /* Most variables will be declared private, will probably communicate through
    intents or broadcast receivers to get stuff from accelerometer or settings */

    private volatile boolean IsPlaying = false;
    private Thread GameThread;
    private long FPS;

    protected LinearAccelerometer LinAcc;
    private long TickTime = 1000/50;

    // How many ticks should (?) have happened
    protected float DeltaTime = 0.0f;
    private long LastLogicTime = System.currentTimeMillis();
    private long LastVisTime = System.currentTimeMillis();

    ArrayList<Entity> Entities = new ArrayList<>();

    protected Player PlayerReference;

    public Visualization Vis;

    protected SingleTouch SingleTouchReference;

    public boolean SetupFinished = false;





    public GameController(Context context, SingleTouch singleTouchReference) {

        InitializeVisualization(context);

        LinAcc = new LinearAccelerometer(context);

        EntityInit();


        SingleTouchReference = singleTouchReference;

        SetupFinished = true;




    }

    private void InitializeVisualization(Context context)
    {

        Vis = new Visualization(context);
    }

    private void InitializeLogic()
    {

    }

    private void EntityInit()
    {
        AddToEntities(new Background(this, new Vector2D(0,0),"Background"));

        PlayerReference = new Player(this,new Vector2D(200,200), "Player");
        AddToEntities(PlayerReference);


        AddToEntities(new Enemy(this, new Vector2D(500,1500),"TempEnemy"));





        //AddToEntities(new Collectable(this, new Vector2D(600,1400),"Coin2"));
        //AddToEntities(new Collectable(this, new Vector2D(600,1600),"Coin3"));

        //AddToEntities(new Player(this,200,600,"Error"));

        // Multiple entity init / Overlay test
        for (int i = 50; i >= 0; i--)
        {
            AddToEntities(new Collectable(this, new Vector2D(600,500+200*i),("Coin" + Integer.toString(i))));
        }



    }

    private void AddToEntities(Entity entity)
    {
        Entities.add(entity);
    }

    // Game loop
    @Override
    public void run() {
        while (IsPlaying && SetupFinished && Vis.SetupFinished())
        {
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


            // If larger than 1 millisecond? always?
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
        Entities.forEach(Entity::Update);
    }

    private void Visualization() {
        Vis.DrawStart();
        Vis.DrawBackground();

        for (Entity entity : Entities)
        {
            if (entity.GetIsVisible()) {
                Vis.Draw(entity);
            }

        }

        Vis.DrawEnd();
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

    public boolean IsScreenValid ()
    {
        // Screen getWidth and getHeight are 0 at launch, meaning that they're not valid.
        float x = Vis.GetScreenSize().GetX();
        float y = Vis.GetScreenSize().GetY();

        x = (float) Math.floor(x);
        y = (float) Math.floor(y);

        boolean ScreenSizeIsNotZero = (x != 0 && y != 0);
        if (ScreenSizeIsNotZero && IsSetupFinished())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean IsSetupFinished()
    {
        return SetupFinished;
    }

    protected ArrayList<Entity> GetAllOtherEntities(Entity entity)
    {
        ArrayList<Entity> AllOther = new ArrayList<Entity>(Entities);
        AllOther.remove(entity);
        return AllOther;
    }
}
