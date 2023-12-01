package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.util.Log;

public class Player extends Entity{

    private int Score = 0;
    float MoveTarget = 800.0f;
    float MoveSpeed = 20.0f;

    Player(GameController gameControllerReference, Vector2D topLeftPosition,String spriteName)
    {
        super(gameControllerReference, topLeftPosition, spriteName);
        Velocity.SetX(10);
    }

    @Override
    public void Update() {
        Move();
        CollisionChecks();
    }

    @Override
    public void OnCollision(Entity collider)
    {
        Log.i("Player.OnCollision", "Player has collied with " + collider.SpriteName);
        // TODO : Logging score should be for collectable only
        Log.i("Player.OnCollision", "Score : " + Integer.toString(Score));
    }
    @Override
    protected void MoveImplementation()
    {
        PlayerTouchInput();

        float PlayerCentrePosition = Position.GetCentrePosition().GetX();


        float AbsXMovementToReachMoveTarget = Math.abs(MoveTarget - PlayerCentrePosition);
        float AbsMoveSpeed = Math.abs(MoveSpeed);


        boolean PlayerAtTarget = PlayerCentrePosition == MoveTarget;
        boolean PlayerWillReachTarget = AbsMoveSpeed > AbsXMovementToReachMoveTarget;
        boolean PlayerToTheLeftOfTarget = PlayerCentrePosition < MoveTarget;
        boolean PlayerToTheRightOfTarget = PlayerCentrePosition > MoveTarget;

        Velocity.SetX(0);

        if (PlayerAtTarget)
        {
        }
        else if (PlayerWillReachTarget)
        {
            float NewVelocity = MoveTarget - PlayerCentrePosition;
            Velocity.SetX(NewVelocity);
        }
        else if (PlayerToTheLeftOfTarget)
        {
            Velocity.SetX(MoveSpeed);
        }
        else if (PlayerToTheRightOfTarget)
        {
            Velocity.SetX(-MoveSpeed);
        }

        DefaultMoveImplementation();


        // Stop the player sprite moving at edge of screen

        float PlayerCentreSize = Position.GetCentreSize().GetX();
        float PlayerXPos = Position.GetXPos();

        float ScreenWidth = GameControllerReference.Vis.GetScreenSize().GetX();

        boolean PlayerInLeftBounds = PlayerXPos >= PlayerCentreSize;
        boolean PlayerInRightBounds = PlayerXPos <= (ScreenWidth - PlayerCentreSize*2);

        boolean PlayerWithinBounds = PlayerInLeftBounds && PlayerInRightBounds;

        if (!PlayerWithinBounds)
        {
            IsMoving = false;
        }

        // Keep player in bounds

        KeepInBounds();






    }

    // TODO : Move CollisionChecks() to entity class?
    public void CollisionChecks()
    {
        for (Entity other : GameControllerReference.Entities)
        {
            if (other != this)
            {
                CollisionHelper.CollisionCheck(this,other);
            }
        }
    }

    public void PlayerTouchInput()
    {
        float MoveInput = GameControllerReference.SingleTouchReference.GetXMovement();

        if (MoveInput > 0)
        {
            MoveTarget = MoveInput;
        }
    }

    public int GetScore() {
        return Score;
    }

    public void SetScore(int score) {
        this.Score = score;
    }

    public void AddScore(int score) {
        Score += score;
    }
}
