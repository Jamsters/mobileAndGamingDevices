package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.HashMap;
import java.util.Map;

public class Visualization extends SurfaceView {
    protected Canvas Canvas;
    protected SurfaceHolder SurfaceHolder;
    protected Vector2D ScreenSize = new Vector2D(0,0);
    private Map<String, Sprite> Sprites = new HashMap<>();
    private Map<String, Bitmap> Bitmaps = new HashMap<>();
    private int BackgroundColour = (Color.WHITE);

    private boolean SetupFinished = false;
    Visualization(Context context) {
        super(context);
        SurfaceHolder = getHolder();
        Initialize();

        SetupFinished = true;
    }
    private void Initialize()
    {
        InitializeSprites();

        for (Sprite sprite : Sprites.values())
        {
            if (sprite.BitmapName == "Background")
            {
                InitializeBitmap(sprite.ResourceID, sprite,false);
            }
            else
            {
                InitializeBitmap(sprite.ResourceID, sprite,true);
            }

        }
    }

    private void InitializeSprites() {
        int Error = R.drawable.error;

        int Run = R.drawable.run;
        int Coin = R.drawable.coin;
        int Background = R.drawable.background;

        AddToSprites("Error", new Sprite("Error",Error,1,200,200, 100));

        AddToSprites("Error", new Sprite("TempEnemy",Error,1,200,200, 100));

        AddToSprites("Player", new Sprite("ManRunning",Run,8,115,137, 100));

        AddToSprites("Background", new Sprite("Background",Background,1,512*2,512*2, 100));

        //AddToSprites("Coin3", new Sprite("Coin",Coin,8,150,150, 100));

//        for (int i = 5; i >= 0; i--)
//        {
//            AddToSprites(("Background" + Integer.toString(i)), new Sprite("Background",Background,1,512*4,512*4,100));
//        }

        for (int i = 50; i >= 0; i--)
        {
            AddToSprites(("Coin" + Integer.toString(i)), new Sprite("Coin",Coin,8,150,150,100));
        }

    }

    private boolean SpritesKeyExists (String key)
    {
        return Sprites.containsKey(key);
    }

    private void AddToSprites(String key, Sprite value) {
        Sprites.put(key,value);
    }

    private Sprite GetSprite(String key) {
        if (SpritesKeyExists(key))
        {
            return Sprites.get(key);
        }
        else
        {
            Log.d("Visualization.GetSprite","Failed to get " + key + " sprite");
            return Sprites.get("Error");
        }
    }

    public Vector2D GetBoundingBoxFromSprite(String spriteName) {
        return GetSprite(spriteName).GetBoundingBox();
    }

    private void InitializeBitmap(int resource, Sprite sprite, boolean createScaled) {
        if (!BitmapKeyExists(sprite.BitmapName))
        {
            Bitmap bitmap;
            bitmap = BitmapFactory.decodeResource(
                    getResources(),
                    resource);
            if (createScaled == true){
                boolean useBilinearFilter = false;
                bitmap = Bitmap.createScaledBitmap(bitmap,sprite.FrameW*sprite.FrameCount,sprite.FrameH, useBilinearFilter);
            }
            AddToBitmaps(sprite.BitmapName,bitmap);
        }
    }

    private boolean BitmapKeyExists (String key)
    {
        return Bitmaps.containsKey(key);
    }

    private void AddToBitmaps(String key, Bitmap value) {
        if (BitmapKeyExists(key))
        {
            // Bitmap exists so don't add it
        }
        else
        {
            Bitmaps.put(key,value);
        }
    }

    private Bitmap GetBitmap(String key) {
        if (BitmapKeyExists(key))
        {
            return Bitmaps.get(key);
        }
        else
        {
            Log.d("Visualization.GetBitmap","Failed to get  " + key + " bitmap");
            return Bitmaps.get("Error");
        }
    }
    public void Draw(Entity entity) {
        if (SurfaceHolder.getSurface().isValid()) {
            Position ePos = entity.Position;
            Sprite Sprite = GetSprite(entity.SpriteName);

            RectF WhereToDraw = new RectF(ePos.GetXPos(), ePos.GetYPos(), ePos.GetWidthXPosition(), ePos.GetHeightYPosition());

            if ((entity.GetIsMoving()||entity.GetIsAlwaysAnimated())) {
                Sprite.ManageCurrentFrame();
            }

            Bitmap Bitmap = GetBitmap(Sprite.BitmapName);

            Canvas.drawBitmap(Bitmap, Sprite.FrameToDraw,WhereToDraw,null);
        }
    }

    public Vector2D GetScreenSize() {
        // Needs to update screen size to stop it from being 0 at the start
        ScreenSize.SetX(ScreenWidthFromView());
        ScreenSize.SetY(ScreenHeightFromView());
        return ScreenSize;
    }
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

    public void DrawStart() {
        if (SurfaceHolder.getSurface().isValid()) {
            Canvas = SurfaceHolder.lockCanvas();
        }
    }

    public void DrawEnd() {
        if (SurfaceHolder.getSurface().isValid()) {
            SurfaceHolder.unlockCanvasAndPost(Canvas);
        }
    }

    public boolean SetupFinished()
    {
        return SetupFinished;
    }

}
