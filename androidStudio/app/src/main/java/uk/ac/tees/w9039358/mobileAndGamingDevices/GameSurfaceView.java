package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements Runnable {

    /* Most variables will be declared private, will probably communicate through
    intents or broadcast receivers to get stuff from accelerometer or settings */
    private volatile boolean Playing = false;
    private Thread GameThread;
    public GameSurfaceView(Context context) {
        super (context);

    }

    @Override
    public void run() {
        while (Playing)
        {
            long StartFrameTime = System.currentTimeMillis();
            Update();
            Draw();
            /* TODO, this is incomplete, finish it */

        }

    }

    public void Resume() {
        Playing = true;

        GameThread = new Thread(this);
        GameThread.start();
    }

    public void Pause() {
        Playing = false;
        try {
            GameThread.join();
        } catch (InterruptedException e) {
            Log.e("GameView", "Interrupted");
        }
    }

    public void Update() {

    }

    public void Draw(){

    }
    /* This is for the practical work, it's where we access the image in the constructor. I'm putting it in a function
     to make it easier to move around later on*/
    public void ImageAccess(){

    }

}
