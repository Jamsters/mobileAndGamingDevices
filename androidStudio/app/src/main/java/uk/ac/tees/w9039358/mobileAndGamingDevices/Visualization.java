package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Collections;
import java.util.Map;

public class Visualization extends SurfaceView {

    protected Canvas Canvas;

    // TODO : Add visibility to the IsMoving boolean, OR delete it if it's not needed anymore which might be the case
    boolean IsMoving = true;

    // TODO : Turn this into a collection of sprites
    // TODO : Remember to add bitmap to sprite
    private Sprite ManRunning;

    protected SurfaceHolder SurfaceHolder;
    protected Bitmap InitialBitmap;

    protected Bitmap ScaledBitmap;


    protected Vector2D ScreenSize = new Vector2D(0,0);

    private Map<String, Bitmap> Bitmaps = Collections.emptySortedMap();

    private int BackgroundColour = (Color.WHITE);

    // Collection of drawables


    Visualization(Context context)
    {
        super(context);
        //InitializeBitmaps();
        // TODO : Moving init for man running to init bitmap
        ManRunning = new Sprite("ManRunning",8,115,137, 100);
        SurfaceHolder = getHolder();
        InitialBitmap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.run);
        ScaledBitmap = InitialBitmap.createScaledBitmap(InitialBitmap, ManRunning.FrameW* ManRunning.FrameCount, ManRunning.FrameH,false);

    }

    private boolean BitmapKeyExists (String bitmapName)
    {
        return Bitmaps.containsKey(bitmapName);
    }

    private void InitializeBitmaps(String bitmapName)
    {
        if (BitmapKeyExists(bitmapName))
        {
            // We don't use existing bitmaps

        }
        ManRunning = new Sprite("ManRunning",8,115,137, 100);
    }

    private void FindAndCheckBitmap(String bitmapName)
    {
        if (BitmapKeyExists(bitmapName))
        {
            //return GetBitmap(bitmapName);

        }
        else
        {
            // Make a new bitmap
        }
        ManRunning = new Sprite("ManRunning",8,115,137, 100);
    }

    private Bitmap GetBitmap(String bitmapName)
    {
        return Bitmaps.get(bitmapName);
    }

//    public void Draw(RectF WhereToDraw, float XPos, float YPos)
//    {
//        if (SurfaceHolder.getSurface().isValid())
//        {
//            Canvas = SurfaceHolder.lockCanvas();
//            /* TODO : Can I use one of the resource colour strings for this? Maybe this? Or maybe its a different colour format because this one
//                is an android graphics one
//            *   canvas.drawColor(getResources().getColor(R.color.ColourWhite)); */
//            Canvas.drawColor(Color.WHITE);
//            WhereToDraw.set(XPos, YPos, XPos+ ManRunning.FrameW, YPos+ ManRunning.FrameH);
//            ManageCurrentFrame();
//            Canvas.drawBitmap(InitialBitmap, ManRunning.FrameToDraw,WhereToDraw,null);
//            SurfaceHolder.unlockCanvasAndPost(Canvas);
//
//
//        }
//    }

    public void Draw(Entity entity)
    {
        if (SurfaceHolder.getSurface().isValid())
        {
            // Working code
            entity.WhereToDraw.set(entity.XPos,entity.YPos,entity.XPos + entity.TempSprite.FrameW, entity.YPos + entity.TempSprite.FrameH);
            entity.TempSprite.ManageCurrentFrame();

            // New code

//            if (entity.TempSprite.BitmapName == "First")
//            {
//                Canvas.drawBitmap(ScaledBitmap, entity.TempSprite.FrameToDraw,entity.WhereToDraw,null);
//            }
//            else if (entity.TempSprite.BitmapName == "Second")
//            {
//                Canvas.drawBitmap(InitialBitmap, entity.TempSprite.FrameToDraw,entity.WhereToDraw,null);
//            }


            //Working old code
            Canvas.drawBitmap(ScaledBitmap, entity.TempSprite.FrameToDraw,entity.WhereToDraw,null);
        }
    }

    // TODO : Maybe move this into sprite class?
    public void ManageCurrentFrame(Entity entity)
    {
        long Time = System.currentTimeMillis();
        if (IsMoving) {
            if (Time > entity.TempSprite.LastFrameChangeTime + entity.TempSprite.FrameLengthInMS)
            {
                entity.TempSprite.LastFrameChangeTime = Time;
                entity.TempSprite.CurrentFrame++;
                if (entity.TempSprite.CurrentFrame >= entity.TempSprite.FrameCount)
                {
                    entity.TempSprite.CurrentFrame = 0;
                }
            }

        }
        entity.TempSprite.FrameToDraw.left = entity.TempSprite.CurrentFrame * entity.TempSprite.FrameW;
        entity.TempSprite.FrameToDraw.right = entity.TempSprite.FrameToDraw.left + entity.TempSprite.FrameW;
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

    public void DrawBackground() {
        if (SurfaceHolder.getSurface().isValid()) {
            /* TODO : Can I use one of the resource colour strings for this? Maybe this? Or maybe its a different colour format because this one
                is an android graphics one. Example below :
                canvas.drawColor(getResources().getColor(R.color.ColourWhite)); */
            Canvas.drawColor(BackgroundColour);
        }
    }

    public void DrawStart()
    {
        if (SurfaceHolder.getSurface().isValid()) {
            Canvas = SurfaceHolder.lockCanvas();
        }
    }

    public void DrawEnd()
    {
        if (SurfaceHolder.getSurface().isValid()) {
            SurfaceHolder.unlockCanvasAndPost(Canvas);
        }
    }
}
