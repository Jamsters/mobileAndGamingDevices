package w9039358.mobileGamingProject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import w9039358.mobileGamingProject.ui.main.MainFragment;
import w9039358.mobileGamingProject.ui.main.OtherFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }

    protected void switchFragmentOther()
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, OtherFragment.newInstance())
                .commitNow();
    }

}