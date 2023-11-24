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

    /* Stuff for the run image at the moment. Change later */

    protected LinearAccelerometer LinAcc;
    private long TickTime = 1000/50;

    // How many ticks have happened
    protected float DeltaTime = 0.0f;
    // When logic was last used
    private long LastLogicTime = System.currentTimeMillis();
    // When vis was last used
    private long LastVisTime = System.currentTimeMillis();

    ArrayList<Entity> Entities = new ArrayList<>();

    protected Player Player;

    public Visualization Vis;

    public boolean SetupFinished = false;




    public GameController(Context context) {
        InitializeVisualization(context);
        LinAcc = new LinearAccelerometer(context);
        EntityInit();
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
        Player = new Player(this,200,200, "Player");
        AddToEntities(Player);

        AddToEntities(new Player(this,600,600,"Coin1"));
        AddToEntities(new Player(this,600,800,"Coin2"));
        AddToEntities(new Player(this,600,1000,"Coin3"));

        //AddToEntities(new Player(this,200,600,"Error"));

        // Multiple entity init / Overlay test
//        for (int i = 10; i >= 0; i--)
//        {
//            AddToEntities(new Player(this,300,i*100,"Second"));
//        }



    }

    private void AddToEntities(Entity entity)
    {
        Entities.add(entity);
    }

    // Game loop
    @Override
    public void run() {
        while (IsPlaying && SetupFinished)
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
}
