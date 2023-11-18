package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.graphics.Rect;

public class Sprite {
    public int FrameW, FrameH;
    public int CurrentFrame = 0;
    public long LastFrameChangeTime = 0;
    public int FrameCount;
    public int FrameLengthInMS;
    public Rect FrameToDraw;

    Sprite(int frameCount, int frameW, int frameH, int frameLengthInMS)
    {
        FrameCount = frameCount;
        FrameW = frameW;
        FrameH = frameH;
        FrameLengthInMS = frameLengthInMS;

        FrameToDraw = new Rect(0,0,FrameW,FrameH);
    }

    public void IncrementCurrentFrame()
    {
        CurrentFrame++;

    }


}
