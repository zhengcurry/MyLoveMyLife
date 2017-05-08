package com.curry.mylovemylife.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by curry on 2017/5/8.
 */

public class ToastUtil {
    public static void showToastShort(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToastLong(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
