package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class GameController implements Runnable {

    // This class is similar to a singleton

    private volatile boolean IsPlaying = false;
    private Thread GameThread;
    private long FPS;

    protected LinearAccelerometer LinAcc;

    // 20 ticks a second
    private long TickTime = 1000/50;

    // How many ticks should (?) have happened
    protected float DeltaTime = 0.0f;
    private long LastLogicTime = SystemClock.elapsedRealtime();
    private long LastFPSLogTime = SystemClock.elapsedRealtime();


    ArrayList<Entity> Entities = new ArrayList<>();

    protected Player PlayerReference;

    public Visualization Vis;

    protected SingleTouch SingleTouchReference;

    public boolean SetupFinished = false;

    protected Context Context;

    private GameActivity GameActivityReference;

    private GameSpawner GameSpawn = new GameSpawner(this);





    public GameController(GameActivity gameActivityReference, Context context, SingleTouch singleTouchReference, Vector2D screenSize) {
        GameActivityReference = gameActivityReference;
        Context = context;
        InitializeVisualization(Context, screenSize);

        LinAcc = new LinearAccelerometer(Context);

        // Vis has to be setup before we run this

        EntityInit();


        SingleTouchReference = singleTouchReference;

        SetupFinished = true;


        // TODO : Test toasts, delete later

        // Does the Toast.LENGTH mean the duration or the string length?
        Toast.makeText(Context, "SHORT TEST TOAST WORKING", Toast.LENGTH_SHORT).show();
        Toast.makeText(Context, "LONG TEST TOAST WORKING", Toast.LENGTH_LONG).show();


    }

    private void InitializeVisualization(Context context, Vector2D ScreenSize)
    {

        Vis = new Visualization(context, ScreenSize);
    }

    private void InitializeLogic()
    {

    }

    private void EntityInit()
    {
        AddToEntities(new Background(this, new Vector2D(0,0),"Background",true));

        PlayerReference = new Player(this,new Vector2D(200,200), "Player", true);
        AddToEntities(PlayerReference);




        // Multiple entity init for dynamic spawning
        for (int i = GameSpawner.GetDynamicSpawningInitializationAmount(); i >= 0; i--)
        {
            AddToEntities(new Collectable(this, new Vector2D(0,0),("Coin" + Integer.toString(i)),false));
            AddToEntities(new Enemy(this, new Vector2D(0,0),("HoverDrone" + Integer.toString(i)), false));
            AddToEntities(new ShakeEnemy(this, new Vector2D(0,0),("Laser" + Integer.toString(i)),false));
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
            long TimeSinceLastLogic = SystemClock.elapsedRealtime() - LastLogicTime;

            if (TimeSinceLastLogic >= TickTime)
            {
                Logic();

                LastLogicTime = SystemClock.elapsedRealtime();

                // Should I round this up?
                DeltaTime = TimeSinceLastLogic / TickTime;
            }

            Visualization();

            long TimeSinceLastFPSLogTime = SystemClock.elapsedRealtime() - LastFPSLogTime;
            if (TimeSinceLastFPSLogTime >= 1000)
            {
                Log.d("GameController.FPS", (String)"FPS: " + Long.toString(FPS));
                FPS = 0;
                LastFPSLogTime = SystemClock.elapsedRealtime();
            }



            Log.d("GameController.DeltaTime", (String)"DeltaTime: " + Float.toString(DeltaTime));



        }

    }

    private void Logic() {

        Entities.forEach(Entity::Update);
        GameSpawn.SpawnEntityCall();


    }

    private void Visualization() {
        try {
            Vis.DrawStart();

            // Using a drawable for background now
            //Vis.DrawBackgroundWithColour();

            for (Entity entity : Entities)
            {
                if (entity.GetIsVisible()) {
                    Vis.Draw(entity);
                }
            }

            Vis.DrawEnd();
            FPS++;
        }
        catch(Exception e)
        {
            // TODO : Figure out how the exception here occurs (a random one happens every X restarts), the current try stops it. Think it has to do with accessing
            // an entity that is null but when I changed for it to not draw I still got errors. Maybe it's for the Vis methods as a whole, I was getting problems with
            // DrawStart, DrawEnd and DrawBackground even when non entities were being drawn.
            Log.e("GameController.Visualization","Vis loop exception");
        }
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
        // TODO : Investigate if you need this anymore seen as screen size is now known at Vis launch, in other words we know it won't be 0.
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

    protected boolean IsSameClassEntityInPlay(Entity entity)
    {
        for (Entity other : Entities)
        {
            boolean OtherIsInPlay = other.GetIsAlive();
            boolean OtherIsSameClass = entity.getClass() == other.getClass();
            if (OtherIsSameClass && OtherIsInPlay)
            {
                return true;
            }
        }
        return false;
    }

    protected void GameOver()
    {
        GameActivityReference.SendToGameOverActivity();
    }
}
