package uk.ac.tees.w9039358.mobileAndGamingDevices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowMetrics;

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

        gameController = new GameController(this, SingleTouch, screenSize);
        // I'm not sure if the game is being started on a thread
        // gameController.Resume();
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

    public void ReturnToMainMenu()
    {
        // Implementation, take to MainActivity
    }

    public void ObtainScreenSize()
    {
        //getWindowManager().getDefaultDisplay().getMetrics(display);
    }


}

