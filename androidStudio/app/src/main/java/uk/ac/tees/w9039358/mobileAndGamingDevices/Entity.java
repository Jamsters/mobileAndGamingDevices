package uk.ac.tees.w9039358.mobileAndGamingDevices;

public abstract class Entity {
    public float Velocity, YVelocity;
    Position Position;
    protected String SpriteName;
    public boolean IsAllowedToMove = true;
    private boolean IsAlwaysAnimated = false;
    protected boolean IsVisible = true;
    protected boolean IsMoving = false;
    protected boolean IsAlwaysMovingUp = false;
    protected GameController GameControllerReference;

    Entity(GameController gameControllerReference, float xPos, float yPos, String spriteName)
    {
        GameControllerReference = gameControllerReference;
        SpriteName = spriteName;


        Vector2D TopLeftPosition = new Vector2D(xPos,yPos);
        Vector2D BoundingBox = GameControllerReference.Vis.GetBoundingBoxFromSprite(SpriteName);
        Position = new Position(TopLeftPosition, BoundingBox);
    }

    public abstract void Update();

    protected abstract void MoveImplementation();

    protected void MoveUp()
    {
        // Need game tick?
        SetYPos(GetYPos() + YVelocity);
    }

    protected void Move()
    {
        if (GetIsAllowedToMove())
        {
            MoveUp();
            MoveImplementation();
            IsMoving = true;
        }
        else
        {
            IsMoving = false;
        }
    }

    protected boolean GetIsAllowedToMove() {return IsAllowedToMove;}
    protected boolean GetIsAlwaysAnimated() {return IsAlwaysAnimated; }
    protected boolean GetIsVisible() {return IsVisible;}

    protected boolean GetIsMoving() {return IsMoving;}

    protected boolean GetIsAlwaysMovingUp() {
        if (YVelocity == 0)
        {
            IsAlwaysMovingUp = true;
        }
        else
        {
            IsAlwaysMovingUp = false;
        }
        return IsAlwaysMovingUp;
    }

    // TODO : Use Position class methods for getters and setters for X and Y
    public float GetXPos() {
        return Position.TopLeftPosition.GetX();
    }

    public void SetXPos(float XPos) {
        //this.XPos = XPos;
        this.Position.TopLeftPosition.SetX(XPos);
    }

    public float GetYPos() {
        return Position.TopLeftPosition.GetY();
    }

    public void SetYPos(float YPos) {
        this.Position.TopLeftPosition.SetY(YPos);
    }
}
