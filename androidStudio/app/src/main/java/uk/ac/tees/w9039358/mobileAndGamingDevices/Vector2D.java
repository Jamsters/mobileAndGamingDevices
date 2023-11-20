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

    public void SetX(int x) {
        X = x;
    }

    public void SetY(int y) {
        Y = y;
    }
}
