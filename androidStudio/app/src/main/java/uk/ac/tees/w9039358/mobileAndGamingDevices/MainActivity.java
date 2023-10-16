package uk.ac.tees.w9039358.mobileAndGamingDevices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);

        /* Trying to change this code so I can have multiple on click listener functions in this class
        button.setOnClickListener(this::onClickButtonName);

        */
        button.setOnClickListener(this);
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
}