package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Visualization extends SurfaceView {

    protected Canvas Canvas;

    // TODO : Turn this into a collection of sprites
    // TODO : Remember to add bitmap to sprite
    private Sprite ManRunning;
    protected SurfaceHolder SurfaceHolder;
    protected Bitmap InitialBitmap;
    protected Bitmap ScaledBitmap;
    protected Vector2D ScreenSize = new Vector2D(0,0);
    private Map<String, Sprite> Sprites = new HashMap<>();

    private Map<String, Bitmap> Bitmaps = new HashMap<>();
    private int BackgroundColour = (Color.WHITE);



    Visualization(Context context)
    {
        super(context);
        InitializeSprites();
        // TODO : Moving init for man running to init bitmap
        ManRunning = new Sprite("ManRunning",8,115,137, 100);
        SurfaceHolder = getHolder();
        InitialBitmap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.run);
        ScaledBitmap = InitialBitmap.createScaledBitmap(InitialBitmap, ManRunning.FrameW* ManRunning.FrameCount, ManRunning.FrameH,false);

    }





    private void IntitializeBitmaps()
    {

    }

    private void AddToBitmaps(String string, Bitmap bitmap)
    {
        Bitmaps.put(string,bitmap);
    }
    private void InitializeSprites()
    {
        AddToSprites("Player", new Sprite("ManRunning",8,115,137, 100));
        AddToSprites("Player2", new Sprite("ManRunning",8,115,137, 10));
        AddToSprites("Error", new Sprite("ManRunning",8,115,137, 1));
    }

    private boolean SpritesKeyExists (String bitmapName)
    {
        return Sprites.containsKey(bitmapName);
    }

    private void AddToSprites(String string, Sprite sprite) {
        Sprites.put(string,sprite);
    }

    private Sprite GetSprite(String spriteName)
    {
        if (SpritesKeyExists(spriteName))
        {
            return Sprites.get(spriteName);
        }
        else
        {
            return Sprites.get("Error");

        }

    }

    public void Draw(Entity entity)
    {
        if (SurfaceHolder.getSurface().isValid())
        {
            Entity Entity = entity;
            Sprite Sprite = GetSprite(entity.SpriteName);


            Entity.WhereToDraw.set(Entity.XPos,Entity.YPos,Entity.XPos + Sprite.FrameW, Entity.YPos + Sprite.FrameH);
            if (Entity.GetIsMoving() == true) {
                Sprite.ManageCurrentFrame();
            }

            Canvas.drawBitmap(ScaledBitmap, Sprite.FrameToDraw,Entity.WhereToDraw,null);
        }
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
