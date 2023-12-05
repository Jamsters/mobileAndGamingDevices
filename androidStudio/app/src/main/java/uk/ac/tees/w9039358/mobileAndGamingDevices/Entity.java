package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.util.Log;

public abstract class Entity {
    Position Position;
    public Vector2D Velocity = new Vector2D(0,0);
    protected String SpriteName;
    private boolean IsAllowedToMove = true;
    private boolean IsAlwaysAnimated = false;
    private boolean IsVisible = true;
    protected boolean IsMoving = false;
    protected boolean IsAlwaysMovingUp = false;
    protected GameController GameControllerReference;

    protected CollisionHelper CollisionHelper = new CollisionHelper();

    private static int SpawnWeight = 0;

    private boolean Spawning;

    private boolean Alive = false;

    float MoveSpeed = 0.0f;

    Entity(GameController gameControllerReference, Vector2D topLeftPosition, String spriteName, boolean spawnsAtStart)
    {
        Spawning = spawnsAtStart;
        AliveToggle(GetIsSpawning());

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

//        // TODO : Check if delta time is actually needed here or if it messes with movement too much
//        // Answer to above, don't think it is because I only needed to use delta time before for the lin acc adding to a total velocity
//        float DeltaTime = GameControllerReference.DeltaTime;
//
//        Position.SetXPos(Position.GetXPos() + Velocity.GetX() * DeltaTime);
//        Position.SetYPos(Position.GetYPos() + Velocity.GetY() * DeltaTime);

        Position.SetXPos(Position.GetXPos() + Velocity.GetX());
        Position.SetYPos(Position.GetYPos() + Velocity.GetY());

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

    // TODO : Can simplify all of the in bounds methods by passing through booleans, position data and/or screen info data. Maybe overload it for X boolean statements
    protected boolean KeepInAllBounds()
    {
        // Uses a single | to run both statements. || only runs the first one if it returns false.
        return KeepInHorizontalBounds() | KeepInVerticalBounds();

    }
    // Returns true if hit left or right horizontal bounds
    protected boolean KeepInHorizontalBounds()
    {
        // Uses a single | to run both statements. || only runs the first one if it returns false.
        return KeepInLeftBounds() | KeepInRightBounds();
    }

    protected boolean KeepInLeftBounds()
    {
        if (GameControllerReference.IsScreenValid())
        {
            if ((Position.TopLeftPosition.GetX()) < 0)
            {
                Position.SetXPos(0);
                OnHitLeftBounds();
                return true;
            }
        }
        return false;
    }

    protected boolean KeepInRightBounds()
    {
        if (GameControllerReference.IsScreenValid())
        {
            if ((Position.GetWidthXPosition() > GameControllerReference.Vis.GetScreenSize().GetX()))
            {
                Position.SetXPos((GameControllerReference.Vis.GetScreenSize().GetX() - Position.GetWidthSize()));
                OnHitRightBounds();
                return true;
            }
        }
        return false;
    }

    // Returns true if hit top or bottom vertical bounds
    protected boolean KeepInVerticalBounds()
    {
        // Uses a single & to run both statements. && only runs the first one if it returns false.
        return KeepInTopBounds() | KeepInBottomBounds();
    }

    protected boolean KeepInTopBounds()
    {
        if (GameControllerReference.IsScreenValid())
        {
            if (Position.TopLeftPosition.GetY() < 0)
            {
                Position.SetYPos(0);
                return true;
            }
        }
        return false;

    }
    protected boolean KeepInBottomBounds()
    {
        if (GameControllerReference.IsScreenValid()) {
            if (Position.GetHeightYPosition() > GameControllerReference.Vis.GetScreenSize().GetY())
            {
                Position.SetYPos(GameControllerReference.Vis.GetScreenSize().GetY() - Position.GetHeightSize());
                return true;
            }
        }
        return false;
    }

    protected void OnHitLeftBounds()
    {

    }

    protected void OnHitRightBounds()
    {

    }

    protected void OnOutOfBounds()
    {
        Log.d("Entity.OnOutOfBounds",SpriteName + " is out of bounds! " );
        // TODO : When something is out of bounds fully we need to stop it from moving, think it makes the game lag if it keeps moving
        AliveToggle(false);

    }

    protected void OnInsideBounds()
    {
        Log.d("Entity.OnInsideOfBounds",SpriteName + " is inside of bounds! " );
        AliveToggle(true);
        SetSpawning(false);
    }

    protected void AliveToggle(boolean makeAlive)
    {
        SetIsVisible(makeAlive);
        SetAllowedToMove(makeAlive);
        SetAlive(makeAlive);
    }

    protected void OnCollision(Entity collider)
    {
        if (GetIsVisible())
        {
            OnCollisionImplementation(collider);
        }
    }
    protected abstract void OnCollisionImplementation(Entity collider);

    protected boolean IsSpawnConditionMet()
    {
        return true;
    }

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

    public int GetSpawnWeight() { return SetSpawnWeight();
    }

    public void SetIsVisible(boolean visible) {
        IsVisible = visible;
    }

    public boolean GetIsSpawning() {
        return Spawning;
    }

    public void SetSpawning(boolean spawning) {
        Spawning = spawning;
    }

    public void SetAllowedToMove(boolean allowedToMove) {
        IsAllowedToMove = allowedToMove;
    }

    public void LogMyPosition()
    {
        Log.d("Entity.Position", "Name:" + SpriteName + " at X:" + Float.toString(Position.GetXPos()) + " Y:" + Float.toString(Position.GetYPos()));
    }

    public boolean GetIsAlive() {
        return Alive;
    }

    private void SetAlive(boolean alive) {
        Alive = alive;
    }

    public static int SetSpawnWeight() {
        return SpawnWeight;
    }

    public static void SetSpawnWeight(int spawnWeight) {
        SpawnWeight = spawnWeight;
    }
}


