package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.graphics.Rect;

public class Sprite {
    public int FrameW, FrameH;
    public int CurrentFrame = 0;
    public long LastFrameChangeTime = 0;
    public int FrameCount;
    public int FrameLengthInMS;
    public Rect FrameToDraw;

    protected String BitmapName;
    protected int ResourceID;

    private Vector2D BoundingBox;



    Sprite(String bitmapName, int resourceID, int frameCount, int frameW, int frameH, int frameLengthInMS)
    {
        BitmapName = bitmapName;

        ResourceID = resourceID;
        FrameCount = frameCount;
        FrameW = frameW;
        FrameH = frameH;
        FrameLengthInMS = frameLengthInMS;


        FrameToDraw = new Rect(0,0,frameW,frameH);

        BoundingBox = (new Vector2D(frameW,frameH));
    }

    public void ManageCurrentFrame()
    {
        // TODO : Change for System.currentTimeMillis to the preferred SystemClock.elapsedRealtime()
        long Time = System.currentTimeMillis();
        if (Time > LastFrameChangeTime + FrameLengthInMS)
        {
            LastFrameChangeTime = Time;
            CurrentFrame++;
            if (CurrentFrame >= FrameCount)
            {
                CurrentFrame = 0;
            }
        }
        FrameToDraw.left = CurrentFrame * FrameW;
        FrameToDraw.right = FrameToDraw.left + FrameW;
    }

    public Vector2D GetBoundingBox()
    {
        return BoundingBox;
    }
}
