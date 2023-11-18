package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;

public abstract class Entity {

//    TODO : Change variable visbility later once refactored
    protected float XPos;
    public float YPos;

    //protected


    // TODO : Move these variables / their functionality into vis later

    protected Bitmap Bitmap;


    public int FrameCount = 8;
    public int FrameLengthInMS = 100;

    public int FrameW = 115, FrameH = 137;

    public long LastFrameChangeTime = 0;
    public int CurrentFrame = 0;

    public Rect FrameToDraw = new Rect(0,0,FrameW,FrameH);
    public RectF WhereToDraw = new RectF(XPos,YPos,XPos+FrameW,FrameH);


    // TODO : Use frame w and frame h to get a bounding box for this
    // Drawable for this entity, e.g. R.drawable.run

    // Should frame info be contained here? Probably not. Should put it into Visualization

    //
    Entity(float xPos, float yPos)
    {
        XPos = xPos;
        YPos = yPos;

    }

    protected abstract void Draw();

    protected abstract void Update();

}
