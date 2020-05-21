package com.niyangup.taobaounion.utils;

import android.widget.Toast;

import com.niyangup.taobaounion.base.BaseApplication;

public class ToastUtil {

    private static Toast toast;

    public static void showToast(String msg) {

        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
