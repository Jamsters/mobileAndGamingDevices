package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.view.SurfaceHolder;

public class Visualization{

    private Canvas Canvas;

    boolean IsMoving = true;

    // TODO : Turn this into a collection of sprites
    // TODO : Remember to add bitmap to sprite
    private Sprite ManRunning;

    private SurfaceHolder SurfaceHolder;
    private Bitmap Bitmap;

    // Collection of drawables


    Visualization(SurfaceHolder surfaceHolder, Bitmap bitmap)
    {
        InitializeSprites();
        SurfaceHolder = surfaceHolder;
        Bitmap = Bitmap.createScaledBitmap(bitmap, ManRunning.FrameW* ManRunning.FrameCount, ManRunning.FrameH,false);

    }

    private void InitializeSprites()
    {
        ManRunning = new Sprite(8,115,137, 100);
    }

    private void InitializeBitmaps()
    {

    }

    public void Draw(RectF WhereToDraw, float XPos, float YPos)
    {
        if (SurfaceHolder.getSurface().isValid())
        {
            Canvas = SurfaceHolder.lockCanvas();
            /* TODO : Can I use one of the resource colour strings for this? Maybe this? Or maybe its a different colour format because this one
                is an android graphics one
            *   canvas.drawColor(getResources().getColor(R.color.ColourWhite)); */
            Canvas.drawColor(Color.WHITE);
            WhereToDraw.set(XPos, YPos, XPos+ ManRunning.FrameW, YPos+ ManRunning.FrameH);
            ManageCurrentFrame();
            Canvas.drawBitmap(Bitmap, ManRunning.FrameToDraw,WhereToDraw,null);
            SurfaceHolder.unlockCanvasAndPost(Canvas);


        }
    }

    // TODO : Maybe move this into sprite class?
    public void ManageCurrentFrame()
    {
        long Time = System.currentTimeMillis();
        if (IsMoving) {
            if (Time > ManRunning.LastFrameChangeTime + ManRunning.FrameLengthInMS)
            {
                ManRunning.LastFrameChangeTime = Time;
                ManRunning.CurrentFrame++;
                if (ManRunning.CurrentFrame >= ManRunning.FrameCount)
                {
                    ManRunning.CurrentFrame = 0;
                }
            }

        }
        ManRunning.FrameToDraw.left = ManRunning.CurrentFrame * ManRunning.FrameW;
        ManRunning.FrameToDraw.right = ManRunning.FrameToDraw.left + ManRunning.FrameW;
    }
}
