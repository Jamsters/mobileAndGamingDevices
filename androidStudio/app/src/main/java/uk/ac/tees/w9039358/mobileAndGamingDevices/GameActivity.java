package uk.ac.tees.w9039358.mobileAndGamingDevices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class GameActivity extends AppCompatActivity {

    GameSurfaceView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameSurfaceView(this);
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