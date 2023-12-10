package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.widget.Toast;

public class ToastHelper {

    // Use a set of toasts to ensure they're unique, even though the showToast method should be enough for now
    private static Toast LastToast;
    private ToastHelper()
    {

    }

    public static void showToast(Toast toast)
    {
        toast.show();
        LastToast = toast;
    }

    public static void cancelLastToast()
    {
        if (LastToast != null)
        {
            LastToast.cancel();
        }
    }

    public static void showThisToastImmediately(Toast toastToShowImmediately)
    {
        cancelLastToast();
        showToast(toastToShowImmediately);
    }
}
