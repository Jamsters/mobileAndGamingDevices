package uk.ac.tees.w9039358.mobileAndGamingDevices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity {

    GameSurfaceView gameView;
    private int ScreenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO : Check to see if setting orientation isn't persisting outside of the app, locking users stuff into portrait only
        // TODO : Also it doesn't look like the orientation fix
        this.setRequestedOrientation(ScreenOrientation);


        gameView = new GameSurfaceView(this);
        // I'm not sure if the game is being started on a thread
        // gameView.Resume();




        setContentView(gameView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.Pause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.Resume();

    }
}