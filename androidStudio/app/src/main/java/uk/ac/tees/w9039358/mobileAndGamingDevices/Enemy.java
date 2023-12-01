package uk.ac.tees.w9039358.mobileAndGamingDevices;

public class Enemy extends Entity {
    Enemy(GameController gameControllerReference, Vector2D topLeftPosition,String spriteName)
    {
        super(gameControllerReference, topLeftPosition, spriteName);

    }

    @Override
    public void Update() {
        Move();
    }

    @Override
    public void OnCollision(Entity collider)
    {
        if (collider.getClass() == Player.class)
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
        // Take health away from player or game over?
    }
}
