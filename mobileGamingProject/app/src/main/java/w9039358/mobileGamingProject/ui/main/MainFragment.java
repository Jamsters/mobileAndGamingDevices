package w9039358.mobileGamingProject.ui.main;


import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import w9039358.mobileGamingProject.ui.main.MainFragment;
import w9039358.mobileGamingProject.ui.main.OtherFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import w9039358.mobileGamingProject.R;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private ImageButton startButton;
    View view;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);


        startButton = view.findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (savedInstanceState == null) {
                    Fragment fragment = new OtherFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    //fragmentManager.beginTransaction().addToBackStack(fragment.getString());

                    fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                }
            }
        });

        return view;




    }

}