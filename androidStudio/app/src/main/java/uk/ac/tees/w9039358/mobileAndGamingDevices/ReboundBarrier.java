package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.util.Log;

public class ReboundBarrier {

    private final int FAST_POSITIVE = 5;
    private final int POSITIVE = 1;
    private final int ZERO = 0;
    private final int NEGATIVE = -1;
    private final int FAST_NEGATIVE = -5;

    private final float PositiveValueThreshold = 0.1f;
    private final float NegativeValueThreshold = -0.1f;

    private int DECREASE_BARRIER = ZERO;
    private int FAST_DECREASE_BARRIER = ZERO;
    private int INCREASE_BARRIER = ZERO;
    private int FAST_INCREASE_BARRIER = ZERO;


    private int ReboundBarrierValueChange = ZERO;
    private int ReboundBarrierValue = ZERO;
    private float TruncatedXAxis = 0.0f;
    private boolean TruncatedXIsPositive;
    private boolean TruncatedXIsNegative;
    private boolean TruncatedXIsZero;

    private boolean ReboundBarrierIsPositive;
    private boolean ReboundBarrierIsNegative;
    private boolean ReboundBarrierIsZero;

    private boolean BarrierCanUseFastPositive;
    private boolean BarrierCanUseFastNegative;

    private boolean CanXAxisRunningTotalChange = false;

    ReboundBarrier() {
    }

    public boolean DoesReboundBarrierExist(float truncatedXAxis) {
        // Reset variables
        CanXAxisRunningTotalChange = false;
        ReboundBarrierValueChange = ZERO;
        //
        TruncatedXAxis = truncatedXAxis;
        SetupBooleans();
        Main();
        return CanXAxisRunningTotalChange;
    }

    private void SetupBooleans() {
        TruncatedXIsPositive = (TruncatedXAxis >= PositiveValueThreshold);
        TruncatedXIsNegative = (TruncatedXAxis <= NegativeValueThreshold);
        TruncatedXIsZero = (!TruncatedXIsPositive && !TruncatedXIsNegative);

        if (TruncatedXIsZero == true)
        {
            TruncatedXAxis = 0.0f;
        }

        ReboundBarrierIsPositive = (ReboundBarrierValue > 0);
        ReboundBarrierIsNegative = (ReboundBarrierValue < 0);
        ReboundBarrierIsZero = (!ReboundBarrierIsPositive && !ReboundBarrierIsNegative);

        BarrierCanUseFastPositive = (ReboundBarrierValue >= FAST_POSITIVE);
        BarrierCanUseFastNegative = (ReboundBarrierValue <= FAST_NEGATIVE);
    }

    private void AltMain()
    {
        boolean AllowsPositiveNumbers = false;



    }
    private void Main() {


        if (ReboundBarrierIsPositive) {
            ReboundBarrierIsPositive();
        }
        else if (ReboundBarrierIsNegative) {
            ReboundBarrierIsNegative();
        }
        else if (ReboundBarrierIsZero) {
            ReboundBarrierIsZero();
        }



        ChangeBarrierValueByValueChange();

        Log.d("ReboundBarrier.ReboundBarrierValue", (String) "ReboundBarrierValue: " + ReboundBarrierValue);
        Log.d("ReboundBarrier.ReboundBarrierValueChange", (String) "ReboundBarrierValueChange: " + ReboundBarrierValueChange);

        // Reset barrier increase, decrease
        INCREASE_BARRIER = ZERO;
        FAST_INCREASE_BARRIER = ZERO;

        DECREASE_BARRIER = ZERO;
        FAST_DECREASE_BARRIER = ZERO;
    }

    private void ReboundBarrierIsPositive() {
        INCREASE_BARRIER = FAST_POSITIVE;
        DECREASE_BARRIER = NEGATIVE;
        FAST_DECREASE_BARRIER = FAST_NEGATIVE;

        if (TruncatedXIsPositive) {
            ReboundBarrierValueChange(INCREASE_BARRIER);
            CanXAxisRunningTotalChange = true;
        } else if (TruncatedXIsNegative) {
            ReboundBarrierValueChange(DECREASE_BARRIER);
        } else if (TruncatedXIsZero) {
            // TODO : Clean this up along with the other function for negative if TrunX is zero
            if (ReboundBarrierValue > 5)
            {
                ReboundBarrierValue = 5;
            }
            else
            {
                ReboundBarrierValueChange(DECREASE_BARRIER);
            }

        }
    }

    private void ReboundBarrierIsNegative() {
        INCREASE_BARRIER = FAST_NEGATIVE;
        DECREASE_BARRIER = POSITIVE;
        FAST_DECREASE_BARRIER = FAST_POSITIVE;

        if (TruncatedXIsPositive) {
            ReboundBarrierValueChange(DECREASE_BARRIER);
        } else if (TruncatedXIsNegative) {
            ReboundBarrierValueChange(INCREASE_BARRIER);
            CanXAxisRunningTotalChange = true;
        } else if (TruncatedXIsZero) {
            // TODO : Clean this up along with the other function for positive if TrunX is zero
            if (ReboundBarrierValue < -5)
            {
                ReboundBarrierValue = -5;
            }
            else
            {
                ReboundBarrierValueChange(DECREASE_BARRIER);
            }

        }
    }

    private void ReboundBarrierIsZero() {
        if (TruncatedXIsPositive) {
            INCREASE_BARRIER = POSITIVE;
            ReboundBarrierValueChange(INCREASE_BARRIER);
        } else if (TruncatedXIsNegative) {
            INCREASE_BARRIER = NEGATIVE;
            ReboundBarrierValueChange(INCREASE_BARRIER);
        } else if (TruncatedXIsZero) {
            ReboundBarrierValueChange(ZERO);
        }


        CanXAxisRunningTotalChange = true;
    }

    private void ReboundBarrierValueChange(int sign) {
        switch (sign) {
            case (ZERO): {
                ReboundBarrierValueChange = ZERO;
                break;
            }
            case (POSITIVE): {
                ReboundBarrierValueChange = POSITIVE;
                break;
            }
            case (FAST_POSITIVE): {
                if (BarrierCanUseFastPositive) {
                    ReboundBarrierValueChange = FAST_POSITIVE;
                } else {
                    ReboundBarrierValueChange = POSITIVE;
                }
                break;
            }
            case (NEGATIVE): {
                ReboundBarrierValueChange = NEGATIVE;
                break;
            }
            case (FAST_NEGATIVE): {
                if (BarrierCanUseFastNegative) {
                    ReboundBarrierValueChange = FAST_NEGATIVE;
                } else {
                    ReboundBarrierValueChange = NEGATIVE;
                }
                break;
            }
            default: {
                break;
            }
        }
    }

    private void ChangeBarrierValueByValueChange()
    {
        ReboundBarrierValue += ReboundBarrierValueChange;
    }
}