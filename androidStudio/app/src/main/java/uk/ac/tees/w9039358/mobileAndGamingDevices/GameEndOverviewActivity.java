package uk.ac.tees.w9039358.mobileAndGamingDevices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class GameEndOverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end_overview);
    }

    public void onClick(View view) {
        Log.d("MainActivity", "Button pressed");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}