package ac.tees.androidstudiopractice.w9039358;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private int mCount = 0;
    private TextView mShowCount;

    public static final String EXTRA_MESSAGE = "Nice name!";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "Log test!");
        mShowCount = (TextView) findViewById(R.id.countTextView);
    }

    public void sendMessage(View view)
    {
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText2);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        Log.d("MainActivity", "Send message test!");
//        startActivity(intent);

    }

    public void testFunction(View view)
    {
        Log.d("MainActivity", "Button test");
    }

    public void showToast(View view) {
        Toast toast = Toast.makeText(this,R.string.toastMessage,Toast.LENGTH_SHORT);
        toast.show();
    }

    public void countUp(View view) {
        mCount++;
        if (mShowCount != null)
        {
            mShowCount.setText(Integer.toString(mCount));
        }


    }
}


