package com.nutsdev.deberts.klabor.app.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by n1ck on 25.03.2015.
 */
public class ToastHelper {

    public static void showCenteredToast(Context context, String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

}
