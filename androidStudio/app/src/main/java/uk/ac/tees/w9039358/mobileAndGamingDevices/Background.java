package uk.ac.tees.w9039358.mobileAndGamingDevices;

public class Background extends Entity{
    Background(GameController gameControllerReference, Vector2D topLeftPosition, String spriteName)
    {
        super(gameControllerReference, topLeftPosition, spriteName);
        Velocity.SetY(0);

    }

    @Override
    public void Update() {
        Move();
    }

    @Override
    protected void MoveImplementation() {
        DefaultMoveImplementation();
    }

    @Override
    protected void OnCollision(Entity collider) {

    }
}
