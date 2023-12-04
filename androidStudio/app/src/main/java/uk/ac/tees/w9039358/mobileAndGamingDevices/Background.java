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
        FindOutOfBoundsEntities();
    }

    @Override
    protected void MoveImplementation() {
        DefaultMoveImplementation();
    }

    @Override
    protected void OnCollisionImplementation(Entity collider) {

    }

    protected void FindOutOfBoundsEntities()
    {
        // The background is the size of the playable space, so we can do collision checks with it to determine what is out of bounds.
        for (Entity other : GameControllerReference.Entities)
        {
            boolean OtherIsSelf = other == this;

            // An entity isn't considered out of bounds if it's still spawning
            //boolean OtherIsSpawning = other.GetIsSpawning();

            // The player has inbuilt ways to stay in bounds
            boolean OtherIsPlayer = other.getClass() == Player.class;

            if (!OtherIsSelf && !OtherIsPlayer)
            {
                CollisionHelper.InsideBackgroundBoundsCheck(this,other);
                // Out of bounds
            }
        }
    }
}
