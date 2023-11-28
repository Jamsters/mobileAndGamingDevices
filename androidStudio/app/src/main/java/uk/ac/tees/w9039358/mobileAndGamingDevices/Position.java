package uk.ac.tees.w9039358.mobileAndGamingDevices;

public class Position {
    protected Vector2D BoundingBox;
    protected Vector2D TopLeftPosition;

    Position(Vector2D topLeftPosition, Vector2D boundingBox)
    {
        BoundingBox = boundingBox;
        TopLeftPosition = topLeftPosition;
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

    public float GetWidthXPosition()
    {
        Vector2D topLeftPosition = new Vector2D(TopLeftPosition.GetX(), TopLeftPosition.GetY());
        topLeftPosition.AddX(GetWidthSize());
        return topLeftPosition.GetX();

        //return TopLeftPosition.GetX()+GetWidthSize();
    }

    public float GetHeightYPosition()
    {
        Vector2D topLeftPosition = new Vector2D(TopLeftPosition.GetX(), TopLeftPosition.GetY());
        topLeftPosition.AddY(GetHeightSize());
        return topLeftPosition.GetY();
    }

    public Vector2D GetCentrePosition()
    {
        Vector2D topLeftPosition = new Vector2D(TopLeftPosition.GetX(), TopLeftPosition.GetY());
        topLeftPosition.Add(GetCentrePosition());
        return topLeftPosition;
    }

    public Vector2D GetWidthAndHeightPosition ()
    {
        Vector2D topLeftPosition = new Vector2D(TopLeftPosition.GetX(), TopLeftPosition.GetY());
        topLeftPosition.Add(BoundingBox);
        return topLeftPosition;
    }

    public float GetXPos() {
        return TopLeftPosition.GetX();
    }

    public void SetXPos(float XPos) {
        TopLeftPosition.SetX(XPos);
    }

    public float GetYPos() {
        return TopLeftPosition.GetY();
    }

    public void SetYPos(float YPos) {
        TopLeftPosition.SetY(YPos);
    }



}
