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
        button = findViewById(R.id.button);

        button.setOnClickListener(this);
    }

    public void onClick(View view) {
        Log.d("MainActibity", "Button pressed");
        Intent intent = new Intent(this, DisplayActivity.class);
        String name = editText.getText().toString();
        if (name == null)
            return;
        intent.putExtra(KEY, name);
        startActivity(intent);
    }
}