package uk.ac.tees.w9039358.mobileAndGamingDevices;

public class Position {
    private Vector2D BoundingBox;
    private Vector2D TopLeftPosition;

    Position(Vector2D TopLeftPosition, Vector2D boundingBox)
    {
        BoundingBox = boundingBox;
    }

    public float GetWidthSize()
    {
        return BoundingBox.GetX();
    }

    public float GetHeightSize()
    {
        return BoundingBox.GetY();
    }

    public Vector2D GetCentreSize()
    {
        return new Vector2D(BoundingBox.GetX()/2, BoundingBox.GetY()/2 );
    }

    public Vector2D GetWidthPosition(Vector2D topLeft)
    {
        return topLeft.AddX(GetWidthSize());
    }

    public Vector2D GetHeightPosition(Vector2D topLeft)
    {
        return topLeft.AddY(GetHeightSize());
    }

    public Vector2D GetCentrePosition(Vector2D topLeft)
    {
        return topLeft.Add(GetCentreSize());
    }

    public Vector2D GetWidthAndHeightPosition (Vector2D topLeft)
    {
        return topLeft.Add(BoundingBox);
    }



}
