package uk.ac.tees.w9039358.mobileAndGamingDevices;

public class Collectable extends Entity{

    Collectable(GameController gameControllerReference, float xPos, float yPos,String spriteName)
    {
        super(gameControllerReference, xPos,yPos, spriteName);
        Velocity = 0;

    }

    @Override
    protected void Draw() {

    }

    @Override
    public void Update() {
        Move();
    }

    protected void Move(){

    }
}
