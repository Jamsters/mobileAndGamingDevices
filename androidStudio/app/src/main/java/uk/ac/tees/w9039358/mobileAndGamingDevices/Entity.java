package uk.ac.tees.w9039358.mobileAndGamingDevices;

public abstract class Entity {
    Position Position;
    public Vector2D Velocity = new Vector2D(0,0);
    protected String SpriteName;
    public boolean IsAllowedToMove = true;
    private boolean IsAlwaysAnimated = false;
    private boolean IsVisible = true;
    protected boolean IsMoving = false;
    protected boolean IsAlwaysMovingUp = false;
    protected GameController GameControllerReference;

    protected CollisionHelper CollisionHelper = new CollisionHelper();

    Entity(GameController gameControllerReference, Vector2D topLeftPosition, String spriteName)
    {
        GameControllerReference = gameControllerReference;
        SpriteName = spriteName;


        Vector2D BoundingBox = GameControllerReference.Vis.GetBoundingBoxFromSprite(SpriteName);
        Position = new Position(topLeftPosition, BoundingBox);
    }

    public abstract void Update();

    protected abstract void MoveImplementation();

    protected void DefaultMoveImplementation()
    {
        // Set position

        // TODO : Check if delta time is actually needed here or if it messes with movement too much
        float DeltaTime = GameControllerReference.DeltaTime;

        Position.SetXPos(Position.GetXPos() + Velocity.GetX() * DeltaTime);
        Position.SetYPos(Position.GetYPos() + Velocity.GetY() * DeltaTime);

        // Set IsMoving
        if (Velocity.GetX() == 0 && Velocity.GetY() == 0)
        {
            IsMoving = false;
        }
        else
        {
            IsMoving = true;
        }



    }

    protected void Move()
    {
        if (GetIsAllowedToMove())
        {
            MoveImplementation();
        }
        else
        {
            IsMoving = false;
        }
    }

    protected void KeepInBounds()
    {
        KeepInHorizontalBounds();
        KeepInVerticalBounds();
    }

    protected void KeepInHorizontalBounds()
    {
        if (GameControllerReference.IsScreenValid())
        {
            // Left
            if ((Position.TopLeftPosition.GetX()) < 0)
            {
                Position.SetXPos(0);
            }

            // Right
            if ((Position.GetWidthXPosition() > GameControllerReference.Vis.GetScreenSize().GetX()))
            {
                Position.SetXPos((GameControllerReference.Vis.GetScreenSize().GetX() - Position.GetWidthSize()));
            }
        }
    }

    protected void KeepInVerticalBounds()
    {
        if (GameControllerReference.IsScreenValid())
        {
            // Top
            if (Position.TopLeftPosition.GetY() < 0)
            {
                Position.SetYPos(0);
            }

            // Bottom
            if (Position.GetHeightYPosition() > GameControllerReference.Vis.GetScreenSize().GetY())
            {
                Position.SetYPos(GameControllerReference.Vis.GetScreenSize().GetY() - Position.GetHeightSize());
            }
        }
    }
    protected void IsOutOfBounds()
    {
        // TODO : When something is out of bounds fully we need to stop it from moving, think it makes the game lag if it keeps moving
    }

    protected abstract void OnCollision(Entity collider);

    protected boolean GetIsAllowedToMove() {return IsAllowedToMove;}
    protected boolean GetIsAlwaysAnimated() {return IsAlwaysAnimated; }
    protected boolean GetIsVisible() {return IsVisible;}

    protected boolean GetIsMoving() {return IsMoving;}

    protected boolean GetIsAlwaysMovingUp() {
        if (Velocity.GetY() == 0)
        {
            IsAlwaysMovingUp = true;
        }
        else
        {
            IsAlwaysMovingUp = false;
        }
        return IsAlwaysMovingUp;
    }

    public void SetIsVisible(boolean visible) {
        IsVisible = visible;
    }
}
