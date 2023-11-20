package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;

public abstract class Entity {

//    TODO : Change variable visbility later once refactored
    protected float XPos, YPos, Velocity;

    // TODO: Remove TempSprite later once Vis uses sprite name
    protected Sprite TempSprite = new Sprite(8,115,137,100);

    private String SpriteName = "Player";

    protected boolean IsMoving = true;

    protected boolean IsVisible = true;

    //protected


    // TODO : Move these variables / their functionality into vis later

    public RectF WhereToDraw = new RectF(XPos,YPos,XPos+TempSprite.FrameW,TempSprite.FrameH);

    protected GameController GameControllerReference;


    // TODO : Use frame w and frame h to get a bounding box for this
    // Drawable for this entity, e.g. R.drawable.run

    // Should frame info be contained here? Probably not. Should put it into Visualization

    //
    Entity(GameController gameControllerReference, float xPos, float yPos)
    {
        GameControllerReference = gameControllerReference;
        XPos = xPos;
        YPos = yPos;

    }

    protected abstract void Draw();

    public abstract void Update();

    protected abstract void Move();

}
