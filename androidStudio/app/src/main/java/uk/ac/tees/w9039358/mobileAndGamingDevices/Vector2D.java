package uk.ac.tees.w9039358.mobileAndGamingDevices;

public class Vector2D {
    private float X, Y;

    Vector2D(float x, float y)
    {
        X = x;
        Y = y;
    }

    public float GetX() {
        return X;
    }

    public float GetY() {
        return Y;
    }

    public void SetX(float x) {
        X = x;
    }

    public void SetY(float y) {
        Y = y;
    }

    public Vector2D Add(Vector2D other)
    {
        X = X + other.GetX();
        Y = Y + other.GetY();
        return this;
    }

    public Vector2D AddY(float other)
    {
        this.SetY(Y+other);
        return this;
    }

    public Vector2D AddX(float other)
    {
        this.SetX(X+other);
        return this;
    }
}
