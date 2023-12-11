package uk.ac.tees.w9039358.mobileAndGamingDevices;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Vector;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener {

    GameController gameController;

    private SingleTouch SingleTouch = new SingleTouch();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();

        float ScreenWidth = metrics.widthPixels;
        float ScreenHeight = metrics.heightPixels;

        Log.d("GameActivity.ScreenSize" , "X:" + Float.toString(ScreenWidth) + " Y:" + Float.toString(ScreenHeight));

        Vector2D screenSize = new Vector2D(ScreenWidth,ScreenHeight);

        InitialCheckIfThePhoneCanRunTheGame(screenSize);

        gameController = new GameController(this,this, SingleTouch, screenSize);

        gameController.Vis.setOnTouchListener((View.OnTouchListener)SingleTouch);

        setContentView(gameController.Vis);

    }

    @Override
    protected void onPause() {
        super.onPause();
        gameController.Pause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        gameController.Resume();

    }
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        return SingleTouch.onTouch(v,event);
    }

    public void SendToGameOverActivity()
    {
        // TODO : Check if the game controller even needs to be paused, or if it has to be destroyed. Java garbage collection should take care of it but need to check if there's still a ref to it because this activity with the game in it isn't
        // being destroyed when moving to this game over activity.
        //gameController.Pause();
        Log.d("GameActivity.EndOverview", "Sent user to GameEndOverviewActivity");

        // TODO : Make this go to a new GameOverActivity and not just the main menu
        Intent intent = new Intent(this, GameEndOverviewActivity.class);

        // We shouldn't be able to go back to a finished game
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        startActivity(intent);
        FinishActivityAndStopBack();

    }

    public void SendBackToMainMenuActivity(String reason)
    {
        Log.d("GameActivity.SentBackToMainMenu", "Reason for sending back :" + reason);
        Intent intent = new Intent(this, MainActivity.class);

        // We shouldn't be able to go back to a finished game
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        intent.putExtra("ErrorReason",reason);


        startActivity(intent);
        FinishActivityAndStopBack();

    }

    public void FinishActivityAndStopBack()
    {
        // Close this activity so we can't go back to it, onPause should be called on the way to onDestroy so we don't have to call it
        //gameController = null;

        finish();


    }

    public void InitialCheckIfThePhoneCanRunTheGame(Vector2D screenSize)
    {
        // Checks for phone size and memory. Doesn't check for accelerometer untill the accleerometer starts.

        boolean DEBUG = false;

        // Size, needs to be considered a medium size on width and height to be allowed to play

        // Defined as medium width and height on android studio developer website
        float MIN_SCREEN_WIDTH = 600;
        float MIN_SCREEN_HEIGHT = 900;

        boolean SmallerThanMinWidth = screenSize.GetX() <= MIN_SCREEN_WIDTH;
        boolean SmallerThanMinHeight = screenSize.GetY() <= MIN_SCREEN_HEIGHT;

        if (SmallerThanMinWidth || SmallerThanMinHeight)
        {
            SendBackToMainMenuActivity("Your device's screen size is too small! Minimum width:" + MIN_SCREEN_WIDTH + " Minimum height:" + MIN_SCREEN_HEIGHT);
            return;
        }

        // TODO : Check for memory + other specs
        // Memory
//        ActivityManager activityManager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
//        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
//        activityManager.getMemoryInfo(memoryInfo);
//        long totalMemoryInBytes = memoryInfo.totalMem;
//        long availableMemoryInBytes = memoryInfo.availMem;

//        Log.d("GameActivity.TotalMemory", "Total memory in bytes:" + Long.toString(totalMemoryInBytes));




        // DEBUG
        if (DEBUG)
        {
            SendBackToMainMenuActivity("TEST USER ERROR MESSAGE!");
            return;
        }

    }


}

