package uk.ac.tees.w9039358.mobileAndGamingDevices;

public class Vector2D {
    private int X, Y;

    Vector2D(int x, int y)
    {
        X = x;
        Y = y;
    }

    public int GetX() {
        return X;
    }

    public int GetY() {
        return Y;
    }

    public void SetX(int x) {
        X = x;
    }

    public void SetY(int y) {
        Y = y;
    }
}
