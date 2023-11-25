package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SingleTouch implements View.OnTouchListener {
    private StringBuilder sb = new StringBuilder();
    private float XMovement = 0;



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
                OnActionMove(event);
                break;
        }
        sb.append(event.getX() + ", " + event.getY());
        String text = sb.toString();
        Log.i("SingleTouch.onTouch", text);
        return true;
    }

    private void OnActionMove(MotionEvent event)
    {
        sb.append("move, ");
        XMovement += event.getX();
    }

    private float GetXMovement()
    {
        float xMovement = XMovement;
        XMovement = 0;
        return xMovement;
    }

}
