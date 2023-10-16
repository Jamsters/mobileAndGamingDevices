package uk.ac.tees.w9039358.mobileAndGamingDevices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity implements View.OnClickListener {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        button = findViewById(R.id.button2);

        button.setOnClickListener(this);
    }

    public void onClick(View view) {
        Log.d("DisplayActivity", "Button pressed");

        /*
        TODO : I don't think this is the right way to do back either. Android studio docs say that this is a retired method and they use a new way to perform this
         */
        onBackPressed();

        /*
        The old way which I used to do back navigation. Don't use this, let android studio handle back navigation through the backstack.
        If you want to change it then either change this activities manfiest launch attributes or have the carried over intent be set to modify the activities launch.

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

         */
    }
}