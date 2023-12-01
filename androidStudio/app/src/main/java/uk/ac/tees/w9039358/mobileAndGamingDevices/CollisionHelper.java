package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.util.Log;

public class CollisionHelper {
    CollisionHelper()
    {

    }

    public boolean CollisionCheck(Position pos1, Position pos2)
    {
        // Full collision check

        // Is pos1 top left less than pos2 top right
        boolean Test1 = pos1.TopLeftPosition.GetX() < pos2.GetWidthXPosition();

        // Is pos1 top right greater than pos2 top left
        boolean Test2 = pos1.GetWidthXPosition() > pos2.TopLeftPosition.GetX();

        // Is pos1 top left less than pos2 bottom left
        boolean Test3 = pos1.TopLeftPosition.GetY() < pos2.GetHeightYPosition();

        // Is pos1 top right greater than pos2 top left
        boolean Test4 = pos1.GetHeightYPosition() > pos2.TopLeftPosition.GetY();

        if (Test1 && Test2 && Test3 && Test4)
        {
            Log.i("CollisionHelper.CollisionCheck","Collision detected!");
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean CollisionCheck(Entity entity1, Entity entity2)
    {
        boolean IsCollision = CollisionCheck(entity1.Position,entity2.Position);
        if (IsCollision)
        {
            entity1.OnCollision(entity2);
            entity2.OnCollision(entity1);
        }
        return IsCollision;
    }

}
