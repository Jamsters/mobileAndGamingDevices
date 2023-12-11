package uk.ac.tees.w9039358.mobileAndGamingDevices;

public class Background extends Entity{
    Background(GameController gameControllerReference, Vector2D topLeftPosition, String spriteName, boolean spawnsAtStart)
    {
        super(gameControllerReference, topLeftPosition, spriteName, spawnsAtStart);
        Velocity.SetY(-5);
        SetSpawning(true);

    }

    @Override
    public void Update() {
        Move();
    }

    @Override
    protected void MoveImplementation() {

        DefaultMoveImplementation();
//        if (Position.GetYPos() <= -200)
//        {
//            Position.SetYPos(0);
//        }
    }

    @Override
    protected void OnCollisionImplementation(Entity collider) {

    }

    @Override
    protected void OnOutOfBounds() {
        super.OnOutOfBounds();
        Position.SetYPos(GameControllerReference.Vis.GetScreenSize().GetY());
        AliveToggle(true);
    }
}

