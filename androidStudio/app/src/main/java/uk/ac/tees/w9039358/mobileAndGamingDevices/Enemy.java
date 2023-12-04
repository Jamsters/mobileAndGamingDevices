package uk.ac.tees.w9039358.mobileAndGamingDevices;

public class Enemy extends Entity {
    Enemy(GameController gameControllerReference, Vector2D topLeftPosition,String spriteName)
    {
        super(gameControllerReference, topLeftPosition, spriteName);
        MoveSpeed = 10.0f;

        Velocity.SetX(MoveSpeed);
        Velocity.SetY(-MoveSpeed);

        SpawnWeight = 2;

    }

    @Override
    public void Update() {

        Move();
        LogMyPosition();
    }

    @Override
    public void OnCollisionImplementation(Entity collider)
    {
        boolean ColliderIsPlayer = collider.getClass() == Player.class;
        boolean SelfIsAlive = GetIsAlive();
        if (ColliderIsPlayer && SelfIsAlive)
        {
            OnPlayerCollision();
        }
    }

    protected void MoveImplementation(){
        DefaultMoveImplementation();
        KeepInHorizontalBounds();
    }

    public void OnPlayerCollision()
    {
        GameControllerReference.GameOver();
        // Take health away from player or game over?
    }

    @Override
    public void OnHitLeftBounds()
    {
        Velocity.SetX(MoveSpeed);
    }

    @Override
    public void OnHitRightBounds()
    {
        Velocity.SetX(-MoveSpeed);
    }
}
