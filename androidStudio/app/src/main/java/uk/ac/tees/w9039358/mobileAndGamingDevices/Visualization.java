package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Collections;
import java.util.Map;

public class Visualization extends SurfaceView {

    private Canvas Canvas;

    // TODO : Add visibility to the IsMoving boolean, OR delete it if it's not needed anymore which might be the case
    boolean IsMoving = true;

    // TODO : Turn this into a collection of sprites
    // TODO : Remember to add bitmap to sprite
    private Sprite ManRunning;

    private SurfaceHolder SurfaceHolder;
    private Bitmap Bitmap;


    protected Vector2D ScreenSize = new Vector2D(0,0);

    private Map<String, Sprite> Sprites = Collections.emptySortedMap();

    // Collection of drawables


    Visualization(Context context)
    {
        super(context);
        InitializeSprites();
        SurfaceHolder = getHolder();
        Bitmap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.run);
        Bitmap = Bitmap.createScaledBitmap(Bitmap, ManRunning.FrameW* ManRunning.FrameCount, ManRunning.FrameH,false);

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

    public void Draw(Entity entity)
    {
        if (SurfaceHolder.getSurface().isValid())
        {
            Canvas = SurfaceHolder.lockCanvas();
            /* TODO : Can I use one of the resource colour strings for this? Maybe this? Or maybe its a different colour format because this one
                is an android graphics one
            *   canvas.drawColor(getResources().getColor(R.color.ColourWhite)); */
            Canvas.drawColor(Color.WHITE);

            //WhereToDraw.set(XPos, YPos, XPos+ ManRunning.FrameW, YPos+ ManRunning.FrameH);
            entity.WhereToDraw.set(entity.XPos,entity.YPos,entity.XPos+entity.TempSprite.FrameW, entity.YPos + entity.TempSprite.FrameH);


            ManageCurrentFrame();
            Canvas.drawBitmap(Bitmap, ManRunning.FrameToDraw,entity.WhereToDraw,null);
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


    public Vector2D GetScreenSize() {
        // Needs to update screen size to stop it from being 0 at the start
        ScreenSize.SetX(ScreenWidthFromView());
        ScreenSize.SetY(ScreenHeightFromView());
        return ScreenSize;
    }

    // Remember ScreenWidthFromView() and ScreenHeightFromView() return 0 at initialization
    private int ScreenWidthFromView() {
        return getWidth();
    }

    private int ScreenHeightFromView() {
        return getHeight();
    }
}
