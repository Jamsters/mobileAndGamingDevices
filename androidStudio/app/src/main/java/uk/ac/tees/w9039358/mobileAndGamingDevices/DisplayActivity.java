package uk.ac.tees.w9039358.mobileAndGamingDevices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        button = findViewById(R.id.button2);
        textView = findViewById(R.id.textView);

        String name = getIntent().getStringExtra(MainActivity.KEY);

        textView.append(name);

        button.setOnClickListener(this);
    }

    public void onClick(View view) {
        Log.d("DisplayActivity", "Button pressed");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}