package uk.ac.tees.w9039358.mobileAndGamingDevices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null)
        {
            if (extras.containsKey("ErrorReason"))
            {
                Toast toast = Toast.makeText(this, extras.getString("ErrorReason"), Toast.LENGTH_LONG);
                ToastHelper.showThisToastImmediately(toast);
                //Snackbar.ma
            }
        }

    }

    public void onClick(View view) {
        Log.d("MainActivity", "Button pressed");
        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);
    }

    public void onClickStartGame(View view) {
        Log.d("MainActivity", "Game start button pressed");
        Intent intent = new Intent(this, GameActivity.class);

        startActivity(intent);
    }

    public void onClickGoToSettings(View view) {
        Log.d("MainActivity", "Settings activity button pressed");
        Intent intent = new Intent(this, SettingsActivity.class);

        startActivity(intent);
    }

    public void onClickGoToHighScores(View view) {
        Log.d("MainActivity", "High scores activity button pressed");
        Intent intent = new Intent(this, HighScoresActivity.class);

        startActivity(intent);
    }
}