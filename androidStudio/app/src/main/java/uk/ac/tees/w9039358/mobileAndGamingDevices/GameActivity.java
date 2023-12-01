package uk.ac.tees.w9039358.mobileAndGamingDevices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener {

    GameController gameView;

    private SingleTouch SingleTouch = new SingleTouch();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameController(this, SingleTouch);
        // I'm not sure if the game is being started on a thread
        // gameView.Resume();
        gameView.Vis.setOnTouchListener((View.OnTouchListener)SingleTouch);

        setContentView(gameView.Vis);

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
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        return SingleTouch.onTouch(v,event);
    }

    public void ReturnToMainMenu()
    {
        // Implementation, take to MainActivity
    }
}