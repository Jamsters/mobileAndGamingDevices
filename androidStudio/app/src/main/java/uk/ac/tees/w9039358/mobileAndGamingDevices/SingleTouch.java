package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SingleTouch implements View.OnTouchListener {
    StringBuilder sb = new StringBuilder();


    public boolean onTouch(View v, MotionEvent event){
        sb.setLength(0);
        switch(event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                sb.append("down, ");
                break;
            case MotionEvent.ACTION_UP:
                sb.append("up, ");
                break;
            case MotionEvent.ACTION_MOVE:
                sb.append("move, ");
                break;
        }
        sb.append(event.getX() + ", " + event.getY());
        String text = sb.toString();
        Log.i("SingleTouch.onTouch", text);
        return true;
    }

}
