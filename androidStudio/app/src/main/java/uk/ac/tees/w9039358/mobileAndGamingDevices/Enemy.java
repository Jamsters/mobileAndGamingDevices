package uk.ac.tees.w9039358.mobileAndGamingDevices;

public class Enemy extends Entity {
    Enemy(GameController gameControllerReference, Vector2D topLeftPosition,String spriteName, boolean spawnsAtStart)
    {
        super(gameControllerReference, topLeftPosition, spriteName, spawnsAtStart);
        MoveSpeed = 10.0f;

        Velocity.SetX(MoveSpeed);
        Velocity.SetY(-MoveSpeed);

        SetSpawnWeight(2);

    }

    @Override
    public void Update() {

        Move();
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
