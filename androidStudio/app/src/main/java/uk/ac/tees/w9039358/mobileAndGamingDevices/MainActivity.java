package uk.ac.tees.w9039358.mobileAndGamingDevices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static String KEY = "MY KEY";
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextText);

        @param next button
        button = findViewById(R.id.button);

        /* Trying to change this code so I can have multiple on click listener functions in this class
        button.setOnClickListener(this::onClickButtonName);

        */
        button.setOnClickListener(this);
    }

    public void onClick(View view) {
        Log.d("MainActivity", "Button pressed");
        Intent intent = new Intent(this, DisplayActivity.class);
        String name = editText.getText().toString();
        /* null and empty text are different. "" doesn't work for some reason though, but using isEmpty does */
        if (name == null || name.isEmpty()) return;
        intent.putExtra(KEY, name);
        startActivity(intent);
    }

    public void onClickStartGame(View view) {
        Log.d("MainActivity", "Game start button pressed");
        Intent intent = new Intent(this, GameActivity.class);

        startActivity(intent);
    }
}