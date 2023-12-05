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
    protected Vector2D ScreenSize;
    private Map<String, Sprite> Sprites = new HashMap<>();
    private Map<String, Bitmap> Bitmaps = new HashMap<>();
    private int BackgroundColour = (Color.WHITE);

    private boolean SetupFinished = false;
    Visualization(Context context, Vector2D screenSize) {
        super(context);
        ScreenSize = screenSize;
        SurfaceHolder = getHolder();
        Initialize();

        SetupFinished = true;

        Log.d("Visualization.ScreenSize", "X:" + Float.toString( ScreenSize.GetX()) + "Y: " + Float.toString(ScreenSize.GetY()));
    }
    private void Initialize()
    {
        InitializeSprites();

        for (Sprite sprite : Sprites.values())
        {
            if (sprite.BitmapName == "Example")
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
        int HookDrone = R.drawable.hookdrone;
        int HoverDrone = R.drawable.hoverdrone;
        int Laser = R.drawable.laser;

        // Any frame width or frame height is of the resource's original size. Only multipliers should be used to change it unless it uses screen size

        AddToSprites("Error", new Sprite("Error",Error,1,200,200, 100));

        //AddToSprites("HookDrone", new Sprite("HookDrone",HookDrone,4,93*2,63*2, 100));

        AddToSprites("Player", new Sprite("ManRunning",Run,8,115,137, 100));

        // Background is 256 x 256
        AddToSprites("Background", new Sprite("Background",Background,1, (int) ScreenSize.GetX(),(int) ScreenSize.GetY(), 100));

        for (int i = GameSpawner.GetDynamicSpawningInitializationAmount(); i >= 0; i--)
        {
            AddToSprites(("Coin" + Integer.toString(i)), new Sprite("Coin",Coin,8,150,150,100));
            AddToSprites(("HoverDrone" + Integer.toString(i)), new Sprite("HoverDrone",HoverDrone,4,36*5,21*5, 100));
            AddToSprites(("Laser" + Integer.toString(i)), new Sprite("Laser",Laser,1,(int) ScreenSize.GetX(),64, 100));
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
        //ScreenSize.SetX(ScreenWidthFromView());
        //ScreenSize.SetY(ScreenHeightFromView());
        return ScreenSize;
    }
    private int ScreenWidthFromView() { return getWidth(); }
    private int ScreenHeightFromView() {
        return getHeight();
    }
    public void DrawBackgroundWithColour() {
        if (SurfaceHolder.getSurface().isValid()) {
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
